package com.subscription.service;

import java.text.ParseException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
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
	@Lazy
	SubscriptionImpl subscriptionImpl;
	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentResponseEntity paymEntity;

	@Autowired
	RazorpayClient razorpayClient;

	@Override
	public Plan getPlan(int planId) {
		System.out.println(planId);
		try {
			List<Object[]> planObj = planRepo.getPlanDetail(planId);

			Object[] object = planObj.get(0);
			if (object == null) {
				System.err.println("object is null");
			}
			for (Object obj : object) {
				System.out.println(obj);
			}

			Plan plan = new Plan();
			plan.setPlanIdRazorPayPlanId((String) object[0]);
			plan.setMonthlyCycle((int) object[1]);

			System.out.println("plan fetching Successsfully");
			// System.out.println(plan.getMonthlyCycle()+ "
			// "+plan.getPlanIdRazorPayPlanId());
			return plan;

		} catch (Exception e) {
			System.err.println("Plan give some error in fetching");
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	private Boolean updateSubcriptionDetailsById(String subscriptionId) {
		try {
			SubscriptionResponseDTO updateSubscriptionDTO = subscriptionService.FecthSubcriptionDetails(subscriptionId);

			System.out.println(updateSubscriptionDTO);

			System.out.println(
					updateSubscriptionDTO == null ? "Fetching Subcription is NUll" : "Fetch Subscription successfull");

			// Update Data in DB By subscriptionId
			subscriptionRepo.updateSubscriptionDetailsById(
					updateSubscriptionDTO.getRazorPaySubscriptionId(),
					updateSubscriptionDTO.getStartDate(), 
					updateSubscriptionDTO.getNextDueDate(),
					updateSubscriptionDTO.getEndDate(), 
					updateSubscriptionDTO.getSubscriptionStatusId());

			return true;
		} catch (RazorpayException e) {
			System.err.println("Updating Subcription error");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("Unix time Converter error in Update Subscription");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JSONObject createSubscription(String razorPayPlanId, int monthlyCycle, UserRequest userRequest) {

		 JSONObject response = new JSONObject();
		try {	
			SubscriptionResponseDTO subscriptionDTO = subscriptionService.createSubscriptionAndInsertDB(razorPayPlanId, monthlyCycle, userRequest);
			System.out.println(subscriptionDTO);
			if (subscriptionDTO == null) {
				System.err.println("SubscriptionResponseDTO is Null in CreateSubcription method");
			}

			// SET Entity Data in Subcripiton table in DB
			subscriptionRepo.saveAllDetailsById(subscriptionDTO.getRazorPaySubscriptionId(),
					subscriptionDTO.getSubscriptionLink(), 
					subscriptionDTO.getPlanId(),
					subscriptionDTO.getSubscriptionStatusId(),
					subscriptionDTO.getUserId(),
					subscriptionDTO.getOrganizationId());

			System.out.println("Subscription created and insert in DB successfully ....");

			// Create Response object by JSON (get data in SubscriptionResponseDTO)			   
		        response.put("subscriptionId",subscriptionDTO.getRazorPaySubscriptionId());
		        response.put("subscriptionLink", subscriptionDTO.getSubscriptionLink());
		        response.put("status", ""+EnumMappingService.getSubscriptionStatusById(subscriptionDTO.getSubscriptionStatusId()));
		        
		        return response;
			
		}
		catch (Exception e){
			System.err.println("Subcription created some Error");
			e.printStackTrace();
			response.put("payment_error", "Payment server down. Please try again sometime :)");
      return response;
		}
	}

	@Override
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId, Long userId,
			Long organizationId) {
		try {
			System.out.println(paymEntity);
			JSONObject response = subscriptionService.VerificationService(razorpaySubscriptionId, paymentId, userId,
					organizationId);
			Boolean b = updateSubcriptionDetailsById(razorpaySubscriptionId);
			if (b) {
				System.out.println("update subcription table Sucessfully");
			} else {
				System.err.println("subcription table not update");
			}

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
					paymEntity.getCreatedOn(),
					paymEntity.getTransactionId());
			System.out.println("Pyment information Successfull insert in db");
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

	@Override
	public JSONObject getPymentHistory(String razorpaySubscriptionId) {
		try {
			List<Object[]> listOfObject = paymentRepository.getPaymentHistoryBySubscriptionId(razorpaySubscriptionId);
			JSONObject jsonObject = new JSONObject();
			System.out.println(listOfObject.size());
			Object[] arrObjects = listOfObject.get(0);
			System.out.println(arrObjects.length);
			jsonObject.put("razorpay_order_id", arrObjects[0]);
			jsonObject.put("transaction_id", "" + arrObjects[1]);
			jsonObject.put("payment_status", arrObjects[2]);
			jsonObject.put("amount", arrObjects[3]);
			jsonObject.put("payment_mode", arrObjects[4]);
			jsonObject.put("organization_name", arrObjects[5]);
			String paymentId = (String) arrObjects[6];
			jsonObject.put("date_time", arrObjects[7]);
			System.out.println(paymentId);
			subscriptionImpl.findModeAndStatus(paymentId, jsonObject);

			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void findModeAndStatus(String paymentId, JSONObject jsonObject) throws RazorpayException {
		Payment razorPayPayment = razorpayClient.payments.fetch(paymentId);
		//razorPayPayment.get(paymentId);
		String UpiNo = razorPayPayment.get("vpa");
		boolean capture = razorPayPayment.get("captured");
		String card =null;
		if (capture) {
			if (UpiNo != null) {
				jsonObject.put("payment_through_upi", UpiNo);
			}  else {
				card = razorPayPayment.get("card_id");
				jsonObject.put("card", card);
			}
		}
	}

}