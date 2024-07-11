package com.subscription.service;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.subscription.logger.LoggerConfiguration;
import com.subscription.model.Plan;
import com.subscription.repo.PaymentRepository;
import com.subscription.repo.PlanRepository;
import com.subscription.repo.SubscriptionRepository;
import com.subscription.response.PaymentResponseEntity;
import com.subscription.response.SubscriptionResponseDTO;

@Service
public class SubscriptionImpl implements SubscriptionInterface {
	
	Logger log = LoggerConfiguration.getSubscriptionImplLogger();
	@Autowired
	private PlanRepository planRepo;	
	@Autowired
	@Lazy
	private SubscriptionImpl subscriptionImpl;
	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentResponseEntity paymEntity;

	@Autowired
	private RazorpayClient razorpayClient;
	
	@Autowired
	private Plan plan;

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public Plan getPlan(int planId)   {
		    log.info("getPlan() function start.....52");
			List<Object[]> planObj = planRepo.getPlanDetail(planId);
			
			Object[] object = planObj.get(0);
			if (object.equals(null)) {
				System.err.println("object is null");
			}
			plan.setPlanIdRazorPayPlanId((String) object[0]);
			plan.setMonthlyCycle((int) object[1]);
			log.info("paln fetch successfully : {}", plan);
			log.info("getPlan() function complete.....64");
			return plan;
	}

	@Transactional(rollbackFor = { Exception.class })
	private void updateSubcriptionDetailsById(String subscriptionId) throws Exception{
		 log.info("updateSubcriptionDetailsById() function start......68");
			SubscriptionResponseDTO updateSubscriptionDTO = subscriptionService.FecthSubcriptionDetails(subscriptionId);
			
			// Update Data in DB By subscriptionId
			subscriptionRepo.updateSubscriptionDetailsById(
					updateSubscriptionDTO.getRazorPaySubscriptionId(),
					updateSubscriptionDTO.getStartDate(), 
					updateSubscriptionDTO.getNextDueDate(),
					updateSubscriptionDTO.getEndDate(), 
					updateSubscriptionDTO.getSubscriptionStatusId());
			
			log.info("Update endDate, nextDueDate, StartDate in DB By subscriptionId is sucessfully: {}", updateSubscriptionDTO);
			log.info("updateSubcriptionDetailsById() function complete....82");
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public JSONObject createSubscription(String razorPayPlanId, int monthlyCycle) throws Exception {	
		    log.info("createSubscription() function start.....87");
		    JSONObject response = new JSONObject();
			SubscriptionResponseDTO subscriptionDTO = subscriptionService.createSubscriptionAndInsertDB(razorPayPlanId, monthlyCycle);
					
			// SET Entity Data in Subcripiton table in DB
			subscriptionRepo.saveAllDetailsById(subscriptionDTO.getRazorPaySubscriptionId(),
					subscriptionDTO.getSubscriptionLink(), 
					subscriptionDTO.getPlanId(),
					subscriptionDTO.getSubscriptionStatusId(),
					subscriptionDTO.getUserId(),
					subscriptionDTO.getOrganizationId());

		       log.info("Subscription created and insert in DB successfully: {}", subscriptionDTO);

			// Create Response object by JSON (get data in SubscriptionResponseDTO)			   
		        response.put("subscriptionId",subscriptionDTO.getRazorPaySubscriptionId());
		        response.put("subscriptionLink", subscriptionDTO.getSubscriptionLink());
		        response.put("status", ""+EnumMappingService.getSubscriptionStatusById(subscriptionDTO.getSubscriptionStatusId()));	
		    	log.info("createSubscription() function complete.......107");
		        return response;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId, Long userId, Long organizationId) throws Exception {
		 log.info("paymentVerifiction() function start..... 112");
		    updateSubcriptionDetailsById(razorpaySubscriptionId);
			JSONObject response = subscriptionService.VerificationService(razorpaySubscriptionId, paymentId, userId, organizationId);
			
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
			log.info("Pyment information Successfull insert in db: {}",paymEntity);
			
			log.info("paymentVerifiction() function complete.......133");
			return response;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public JSONObject getPymentHistory(String razorpaySubscriptionId) throws Exception {
		    log.info("payment Receipt() function start..... 138");
			List<Object[]> listOfObject = paymentRepository.getPaymentHistoryBySubscriptionId(razorpaySubscriptionId);
			System.out.println(listOfObject.size());
			JSONObject jsonObject = new JSONObject();
			Object[] arrObjects = listOfObject.get(0);
			jsonObject.put("razorpay_order_id", arrObjects[0]);
			jsonObject.put("transaction_id", "" + arrObjects[1]);
			jsonObject.put("payment_status", arrObjects[2]);
			jsonObject.put("amount", arrObjects[3]);
			jsonObject.put("payment_mode", arrObjects[4]);
			jsonObject.put("organization_name", arrObjects[5]);
			String paymentId = (String) arrObjects[6];
			jsonObject.put("date_time", arrObjects[7]);
			subscriptionImpl.findModeAndStatus(paymentId, jsonObject);
            log.info("payment receipt feching Successfully: {}", jsonObject);
			log.info("getPymentReceipt() function complete.......156");
			return jsonObject;
	}

	public void findModeAndStatus(String paymentId, JSONObject jsonObject) throws RazorpayException  {
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