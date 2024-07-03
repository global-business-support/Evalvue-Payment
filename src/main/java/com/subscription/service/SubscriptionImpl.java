package com.subscription.service;

import java.text.ParseException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorpay.RazorpayException;
import com.subscription.model.Plan;
import com.subscription.repo.PaymentRepository;
import com.subscription.repo.PlanRepository;
import com.subscription.repo.SubscriptionRepository;
import com.subscription.request.UserRequest;
import com.subscription.response.PaymentResponseEntity;
import com.subscription.response.SubscriptionResponseDTO;
@Service
public class SubscriptionImpl implements SubscriptionInterface {
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private SubscriptionRepository subscriptionRepo;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentResponseEntity paymEntity;

	@Override
	public Plan getPlan(int planId) {	
		System.out.println(planId);
		try {
			List<Object[]> planObj = planRepo.getPlanDetail(planId);
			
		    Object[] object = planObj.get(0);
		    if(object == null){
				System.err.println("object is null");
			}
		    for(Object obj : object) {
		    	 System.out.println(obj);
		    }
		    
		    Plan plan = new Plan();
		    plan.setPlanIdRazorPayPlanId((String)object[0]); 
		    plan.setMonthlyCycle((int)object[1]);
		   	
	    	System.out.println("plan fetching Successsfully");
			//System.out.println(plan.getMonthlyCycle()+ " "+plan.getPlanIdRazorPayPlanId());
	    	return plan;
			
			
		} catch (Exception e) {
			System.err.println("Plan give some error in fetching");
			e.printStackTrace();
		}
		return null;	    
	}

	 @Transactional
	public Boolean FecthSubcriptionDetailsById(String subscriptionId) {
		try {
			System.out.println("1 step");
			SubscriptionResponseDTO updateSubscriptionDTO =  subscriptionService.FecthSubcriptionDetails(subscriptionId);
			
			System.out.println(updateSubscriptionDTO);
			
			System.err.println(updateSubscriptionDTO  == null ? "Fetching Subcription is NUll"  : "Fetch Subscription successfull");
			
			//Update Data in DB By subscriptionId
			subscriptionRepo.updateSubscriptionDetailsById(
					updateSubscriptionDTO.getSubscriptionId(),
	                updateSubscriptionDTO.getCreateDate(),
	                updateSubscriptionDTO.getNextDueDate(),
	                updateSubscriptionDTO.getEndDate(),
	                updateSubscriptionDTO.getStatus()
	            );
			
			System.out.println("data update successful");
			return true;
		} catch (RazorpayException e) {
			System.err.println("Fetching Subcription error");
		} catch (ParseException e) {
			System.err.println("Unix time Converter error in FetchSubscription");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JSONObject createSubscription(String razorPayPlanId, int monthlyCycle, UserRequest userRequest) {
		try {	
			SubscriptionResponseDTO subscriptionDTO = subscriptionService.createSubscriptionAndInsertDB(razorPayPlanId, monthlyCycle, userRequest);
			if(subscriptionDTO == null) {
				System.err.println("SubscriptionResponseDTO is Null in CreateSubcription method");
			}
			System.out.println("SubscriptionResponseDTO Data is present...");
			System.out.println(subscriptionDTO);
			System.out.println();	
			
			// SET Entity Data in Subcripiton table in DB
			subscriptionRepo.saveAllDetailsById(
	                subscriptionDTO.getSubscriptionId(),
	                subscriptionDTO.getSubscriptionLink(),
	                subscriptionDTO.getPlanId(),
	                subscriptionDTO.getStatus(),
	                subscriptionDTO.getUserId(),
	                subscriptionDTO.getOrganizationId()
	            );
			
			System.out.println("Subscription created successfull");
			
			// Create Response object by JSON (get data in SubscriptionResponseDTO)
			    JSONObject response = new JSONObject();
		        response.put("subscriptionId",subscriptionDTO.getSubscriptionId());
		        response.put("subscriptionLink", subscriptionDTO.getSubscriptionLink());
		        response.put("status", subscriptionDTO.getStatus());
		        return response;
			
		} catch (Exception e) {
			System.err.println("Subcription created some Error");
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId, Long userId, Long organizationId) {
		try {
			JSONObject response = subscriptionService.VerificationService(razorpaySubscriptionId, paymentId, userId, organizationId);
			
			
			System.out.println("hii....");
			System.out.println(paymEntity);
			
			// insert data in db
			paymentRepository.insertPaymentResponse(
					paymEntity.getRazorpayPaymentId(),
					paymEntity.getRazorpayOrderId(),
					paymEntity.getRazorPaySubscriptionId(),
					paymEntity.getUserId(),
				    paymEntity.getOrganizationId(),
			        paymEntity.getPaymentStatusId(),
					paymEntity.getAmount(),
					paymEntity.getPaymentMode(),
					paymEntity.getUserEmail(),
					paymEntity.getContact(),
					paymEntity.getCreatedOn()
					);
					
			     
			       
			
			System.out.println("Successfull insert in db");
			return response;
		} catch (RazorpayException e) {
			System.err.println("payment feching some error");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("Unix Time Converter feching some error in Paymen Fetching");
			e.printStackTrace();
		}
		return null;
	}

}
