package com.subscription.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.Subscription;
import com.subscription.logger.LoggerConfiguration;
import com.subscription.model.InsertStatusOfPayment;
import com.subscription.request.UserRequest;
import com.subscription.response.PaymentResponseEntity;
import com.subscription.response.SubscriptionResponseDTO;
import com.subscription.unix.time.DateAfterOneMonth;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
	Logger log = LoggerConfiguration.getSubscriptionservicelogger();
	
	@Autowired
	private  RazorpayClient razorpayClient;
	@Autowired
	private  UserRequest userRequest;
	@Autowired
	private  PaymentResponseEntity paymentResponse;
	@Autowired
	private  PaymentStatusOnOrganizationLevel paymentStatusOnOrganizationLevel;
	@Autowired
	private  InsertStatusOfPayment insertStatusOfPayment;
	@Autowired
	private SubscriptionResponseDTO responseDTO;
	
		
	int paymentStatusId;
	private Object status;

	
	public SubscriptionResponseDTO createSubscriptionAndInsertDB(String PlanId, int MonthlyCycle) throws Exception {
		log.info("createSubscriptionAndInsertDB() function start..... 44");
		JSONObject subscriptionRequest = new JSONObject();	
		if(userRequest.getPlan_id() == 1 || userRequest.getPlan_id() == 3) {
		subscriptionRequest.put("plan_id", PlanId);
		subscriptionRequest.put("total_count", 12);
		subscriptionRequest.put("quantity", 1);
		subscriptionRequest.put("customer_notify", 0);
	    long unixTime =  DateAfterOneMonth.unixDateTime();
		subscriptionRequest.put("start_at", unixTime);	
		}
		else {
			if(userRequest.getPlan_id() == 2 || userRequest.getPlan_id() == 4) {
			subscriptionRequest.put("plan_id", PlanId);
			subscriptionRequest.put("total_count", 12);
			subscriptionRequest.put("quantity", 1);
			subscriptionRequest.put("customer_notify", 0); 
			}
		}
		       
//        //Create Subscription
		Subscription subscription = razorpayClient.subscriptions.create(subscriptionRequest);
        log.info("creating Razorpay subscription Id sucessfully....65");
        
//        // Set Data in model class  SubscriptionResponseDTO fetching from  RazorPay Subscription Class
		responseDTO.setRazorPaySubscriptionId(subscription.get("id"));
		status = subscription.get("status");
//		changes
		responseDTO.setSubscriptionStatusId(EnumMappingService.getSubscriptionStatusByString(status.toString()));
		responseDTO.setSubscriptionLink(subscription.get("short_url"));

//        // Get Data In UserRequest and set in SubscriptionResponseDTO
		responseDTO.setUserId(userRequest.getUser_id());
		responseDTO.setPlanId(userRequest.getPlan_id());
		responseDTO.setOrganizationId(userRequest.getOrganization_id());
		log.info("createSubscriptionAndInsertDB() function complete.......80");
		return responseDTO;
	}

	public SubscriptionResponseDTO FecthSubcriptionDetails(String subscriptionId) throws Exception  {
		log.info("FecthSubcriptionDetails() function start..... 83");
		Subscription subscription = razorpayClient.subscriptions.fetch(subscriptionId);
		responseDTO.setRazorPaySubscriptionId(subscriptionId);
		status = subscription.get("status");
		responseDTO.setSubscriptionStatusId(EnumMappingService.getSubscriptionStatusByString(status.toString()));
		
		responseDTO.setStartDate(convertUnixTimeToDate(subscription.get("start_at")));
		responseDTO.setNextDueDate(convertUnixTimeToDate(subscription.get("charge_at")));
		responseDTO.setEndDate(convertUnixTimeToDate(subscription.get("end_at")));
		responseDTO.setSubscriptionLink(subscription.get("short_url"));
		
		log.info("FecthSubcriptionDetails() function complete.......94");
		return responseDTO;

	}

	@Transactional(rollbackOn = { Exception.class })
	public JSONObject VerificationService(String razorpaySuscriptionId, String paymentId, Long userId, Long organizationId) throws Exception {
		log.info("VerificationService() function start..... 100");
//    	feching payment details
		Payment razorPayPayment = razorpayClient.payments.fetch(paymentId);

		// Set Data in PaymentEntity
		paymentResponse.setRazorpayPaymentId(paymentId);
		paymentResponse.setRazorpayOrderId(razorPayPayment.get("order_id"));
		paymentResponse.setRazorPaySubscriptionId(razorpaySuscriptionId);
		paymentResponse.setUserId(userId);
		paymentResponse.setOrganizationId(organizationId);
		// captured and Status fetch
		Boolean captured = razorPayPayment.get("captured");
		String status = razorPayPayment.get("status");
		paymentStatusId = EnumMappingService.getPaymentStatus(captured, status);
		JSONObject jsonResponseObject = EnumMappingService.paymentResponse(paymentStatusId, razorPayPayment);
		paymentResponse.setPaymentStatusId(paymentStatusId);
		paymentResponse.setAmount(razorPayPayment.get("amount"));
		paymentResponse.setPaymentMode(razorPayPayment.get("method"));
		paymentResponse.setUserEmail(razorPayPayment.get("email"));
		paymentResponse.setContact(razorPayPayment.get("contact"));
		paymentResponse.setCreatedOn(razorPayPayment.get("created_at"));
		
		if (captured || status.equalsIgnoreCase("refunded")) {
			insertStatusOfPayment.setIsPaid((byte)1);
			insertStatusOfPayment.setOrganization_id(organizationId);
			//Set DB in IsPaid
			paymentStatusOnOrganizationLevel.setPaymentStatusOnOrgainaztion();
			log.info("IsPaid insert Sucessfully: {}",insertStatusOfPayment);
		}
		
		JSONObject transecationId = razorPayPayment.get("acquirer_data");
		Object rrn = transecationId.get("rrn");
		if (!rrn.equals(null)) {
			paymentResponse.setTransactionId(rrn.toString());
		}
		
		log.info(" VerificationService() function complete.......135");
		return jsonResponseObject;
	}

	private Date convertUnixTimeToDate(Object unixTime) throws ParseException{
		if(unixTime.equals(null)) {
		  return null;
		}
		System.out.println(unixTime);
		long timestamp = Long.parseLong(unixTime.toString()) * 1000L;
		Date date = new Date(timestamp);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		sdf.setTimeZone(TimeZone.getDefault());
		String localDateString = sdf.format(date);
		return sdf.parse(localDateString);

	}

    
    
}