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
    
    @Autowired
    public SubscriptionService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
        
    }
    
    
    
    public SubscriptionResponseDTO createSubscriptionAndInsertDB(String PlanId, int MonthlyCycle, UserRequest userRequest) throws Exception {
    	System.out.println(PlanId);
    	System.out.println(MonthlyCycle);
        JSONObject subscriptionRequest = new JSONObject();
        subscriptionRequest.put("plan_id", PlanId);
        subscriptionRequest.put("total_count", MonthlyCycle);
        subscriptionRequest.put("quantity", 1);
        subscriptionRequest.put("customer_notify", 0);   
        
        //Create Subscription
        Subscription subscription = razorpayClient.subscriptions.create(subscriptionRequest);
       
        // Set Data in model class  SubscriptionResponseDTO fetching from  RazorPay Subscription Class
        SubscriptionResponseDTO responseDTO  = new SubscriptionResponseDTO();
        responseDTO.setSubscriptionId(subscription.get("id")); 
        responseDTO.setStatus(subscription.get("status"));
        responseDTO.setSubscriptionLink(subscription.get("short_url"));
        
        // Get Data In UserRequest and set in SubscriptionResponseDTO
      
        System.out.println(userRequest);
    	responseDTO.setUserId(userRequest.getUser_id());
    	responseDTO.setPlanId(userRequest.getPlan_id());
    	responseDTO.setOrganizationId(userRequest.getOrganization_id()); 
    	
        return responseDTO;    
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
    
    
    public JSONObject VerificationService(String razorpaySuscriptionId, String paymentId) throws RazorpayException, ParseException {
//    	feching payment details
    	Payment razorPayPayment = razorpayClient.payments.fetch(paymentId);
    	
    	// Set Data in PaymentEntity
    	PaymentResponseEntity paymentResponse = new PaymentResponseEntity();
    	paymentResponse.setRazorpayPaymentId(paymentId);
    	paymentResponse.setRazorpayOrderId(razorPayPayment.get("order_id"));
    	paymentResponse.setAmount(razorPayPayment.get("amount"));
    	paymentResponse.setPaymentMode(razorPayPayment.get("method"));
    	paymentResponse.setUserEmail(razorPayPayment.get("email"));
    	paymentResponse.setContact(razorPayPayment.get("contact"));
    	paymentResponse.setCreatedAt(razorPayPayment.get("created_at"));
    	paymentResponse.setStatus(razorPayPayment.get("status"));
    	paymentResponse.setCaptured(razorPayPayment.get("captured"));
//    	paymentResponse.setSubscriptionId(razorpaySuscriptionId);
//    	paymentResponse.setInvoiceId(razorPayPayment.get(""));
//    	
//    	Invoice invoice = razorpayClient.invoices.fetch()
    	
    	
    	System.out.println(paymentResponse);
    	JSONObject jsonObject = razorPayPayment.toJson();
    	
    	
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
