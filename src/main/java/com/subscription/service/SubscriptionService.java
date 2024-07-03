package com.subscription.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Invoice;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Subscription;
import com.subscription.request.UserRequest;
import com.subscription.response.PaymentResponseEntity;
import com.subscription.response.SubscriptionResponseDTO;

@Service
public class SubscriptionService {

   
    //private BitlyService bitlyService;

    private final RazorpayClient razorpayClient;
    private final UserRequest userRequest;
    private final PaymentResponseEntity paymentResponse;
    private int paymentStatusId;
    
    @Autowired
    public SubscriptionService(RazorpayClient razorpayClient, UserRequest userRequest, PaymentResponseEntity paymentResponse) {
        this.razorpayClient = razorpayClient;
		this.userRequest = userRequest;
		this.paymentResponse = paymentResponse;
		
        
    }
    
    
    
    
    public SubscriptionResponseDTO createSubscriptionAndInsertDB(String PlanId, int MonthlyCycle, UserRequest user) throws Exception {
  
        JSONObject subscriptionRequest = new JSONObject();
        subscriptionRequest.put("plan_id", PlanId);
        subscriptionRequest.put("total_count", MonthlyCycle);
        subscriptionRequest.put("quantity", 1);
        subscriptionRequest.put("customer_notify", 0);   
//        
//        //Create Subscription
        Subscription subscription = razorpayClient.subscriptions.create(subscriptionRequest);
//       
//        // Set Data in model class  SubscriptionResponseDTO fetching from  RazorPay Subscription Class
        SubscriptionResponseDTO responseDTO  = new SubscriptionResponseDTO();
        responseDTO.setSubscriptionId(subscription.get("id")); 
        responseDTO.setStatus(subscription.get("status"));
        responseDTO.setSubscriptionLink(subscription.get("short_url"));
        
//        // Get Data In UserRequest and set in SubscriptionResponseDTO
//      
        System.out.println(userRequest);
    	responseDTO.setUserId(userRequest.getUser_id());
    	responseDTO.setPlanId(userRequest.getPlan_id());
    	responseDTO.setOrganizationId(userRequest.getOrganization_id()); 
    	
        return null;    
    }
    
    
    public SubscriptionResponseDTO FecthSubcriptionDetails(String subscriptionId) throws RazorpayException, ParseException {
   SubscriptionResponseDTO responseDTO = new SubscriptionResponseDTO();
    Subscription subscription = razorpayClient.subscriptions.fetch(subscriptionId);
    responseDTO.setSubscriptionId(subscriptionId);
    responseDTO.setStatus(subscription.get("status"));
    responseDTO.setCreateDate(convertUnixTimeToDate(subscription.get("current_start")));
    responseDTO.setNextDueDate(convertUnixTimeToDate(subscription.get("end_at")));
    responseDTO.setEndDate(convertUnixTimeToDate(subscription.get("end_at"))); 
    responseDTO.setSubscriptionLink(subscription.get("short_url"));
    
    UserRequest user = new UserRequest();
    System.out.println(user);
    return  responseDTO ;
   
    }
    
    
    public JSONObject VerificationService(String razorpaySuscriptionId, String paymentId, Long userId, Long organizationId) throws RazorpayException, ParseException {
//    	feching payment details
    	Payment razorPayPayment = razorpayClient.payments.fetch(paymentId);
    	
    	// Set Data in PaymentEntity
    	paymentResponse.setRazorpayPaymentId(paymentId);
    	paymentResponse.setRazorpayOrderId(razorPayPayment.get("order_id"));
    	paymentResponse.setRazorPaySubscriptionId(razorpaySuscriptionId);
    	paymentResponse.setUserId(userId);
    	paymentResponse.setOrganizationId(organizationId);
    	
    	JSONObject jsonObject = new JSONObject();
    	//captured and  Status fetch 
    	Boolean captured = razorPayPayment.get("captured");
    	String status = razorPayPayment.get("status");
    	
    	if(captured && status.equalsIgnoreCase("captured")) {
    		paymentStatusId = 1;
    		jsonObject.put("Status", "paid");
     	    jsonObject.put("Transaction", "Successful");
    	}
    	else if(!captured && status.equalsIgnoreCase("failed")) {
    		paymentStatusId = 2;
    		String error_description = razorPayPayment.get("error_description");
    		String error_source = razorPayPayment.get("error_source");
    		String error_step = razorPayPayment.get("error_step");
    		String error_reason = razorPayPayment.get("error_reason");
    		jsonObject.put("Status", "failed");
    		jsonObject.put("payment_cancelled", error_reason);
     	    jsonObject.put("error_description", error_description);
     	    jsonObject.put("error_source", error_source);
    	    jsonObject.put("error_step", error_step);   	    
    	}
    	else{
    		paymentStatusId = 3;
    		jsonObject.put("Status", "refunded");
     	    jsonObject.put("Transaction", "proceed");
    		
    	}
    	 
    	paymentResponse.setPaymentStatusId(paymentStatusId); 	
    	paymentResponse.setAmount(razorPayPayment.get("amount"));
    	paymentResponse.setPaymentMode(razorPayPayment.get("method"));
    	paymentResponse.setUserEmail(razorPayPayment.get("email"));
    	paymentResponse.setContact(razorPayPayment.get("contact"));
    	paymentResponse.setCreatedOn(razorPayPayment.get("created_at"));
    	        
   
    	System.out.println(paymentResponse);
    	
    	
    	return jsonObject;
    }

    private Date convertUnixTimeToDate(Object unixTime) throws ParseException {
        long timestamp = Long.parseLong(unixTime.toString()) * 1000L;
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());
        String localDateString = sdf.format(date);
        return sdf.parse(localDateString);

    }
}
