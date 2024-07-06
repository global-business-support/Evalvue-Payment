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
    private Boolean captured;
    private String status;
    
    @Autowired
    public SubscriptionService(RazorpayClient razorpayClient, UserRequest userRequest, PaymentResponseEntity paymentResponse) {
        this.razorpayClient = razorpayClient;
		this.userRequest = userRequest;
		this.paymentResponse = paymentResponse;
		
        
    }
    
    
    
    
    public SubscriptionResponseDTO createSubscriptionAndInsertDB(String PlanId, int MonthlyCycle, UserRequest user) throws Exception, RazorpayException {
  
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
        responseDTO.setRazorPaySubscriptionId(subscription.get("id")); 
        status = subscription.get("status");
        responseDTO.setSubscriptionStatusId(EnumMappingService.getSubscriptionStatusByString(status));
        responseDTO.setSubscriptionLink(subscription.get("short_url"));
        
//        // Get Data In UserRequest and set in SubscriptionResponseDTO
//      
        System.out.println(userRequest);
    	responseDTO.setUserId(userRequest.getUser_id());
    	responseDTO.setPlanId(userRequest.getPlan_id());
    	responseDTO.setOrganizationId(userRequest.getOrganization_id()); 
    	
        return responseDTO;    
    }
    
    
    public SubscriptionResponseDTO FecthSubcriptionDetails(String subscriptionId) throws RazorpayException, ParseException {
   SubscriptionResponseDTO responseDTO = new SubscriptionResponseDTO();
    Subscription subscription = razorpayClient.subscriptions.fetch(subscriptionId);
    responseDTO.setRazorPaySubscriptionId(subscriptionId);
    status = subscription.get("status");
    responseDTO.setSubscriptionStatusId(EnumMappingService.getSubscriptionStatusByString(status));
    System.out.println("current_start");
    responseDTO.setStartDate(convertUnixTimeToDate(subscription.get("current_start")));
    System.out.println("charge_at");
    responseDTO.setNextDueDate(convertUnixTimeToDate(subscription.get("charge_at")));
    System.out.println("end_at");
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
    	//captured and  Status fetch 
    	Boolean captured = razorPayPayment.get("captured");
    	String status = razorPayPayment.get("status");
    	paymentStatusId  = EnumMappingService.getPaymentStatus(captured, status);
    	JSONObject jsonResponseObject = EnumMappingService.paymentResponse(paymentStatusId, razorPayPayment);   	 
    	paymentResponse.setPaymentStatusId(paymentStatusId); 	
    	paymentResponse.setAmount(razorPayPayment.get("amount"));
    	paymentResponse.setPaymentMode(razorPayPayment.get("method"));
    	paymentResponse.setUserEmail(razorPayPayment.get("email"));
    	paymentResponse.setContact(razorPayPayment.get("contact"));
    	paymentResponse.setCreatedOn(razorPayPayment.get("created_at"));
        
    	JSONObject transecationId = razorPayPayment.get("acquirer_data");
    	Object rrn = transecationId.get("rrn");
    	System.out.println("rrn = "+rrn);
    	if(!rrn.equals(null)) {
    		paymentResponse.setTransactionId(transecationId.getString("upi_transaction_id"));
    	}
    	System.out.println(paymentResponse);
    	return jsonResponseObject;
    }

    private Date convertUnixTimeToDate(Object unixTime) throws ParseException {
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
