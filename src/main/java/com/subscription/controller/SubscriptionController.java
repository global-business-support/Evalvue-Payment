package com.subscription.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.subscription.model.Plan;
import com.subscription.request.PaymentRequest;
import com.subscription.request.UserRequest;
import com.subscription.service.interfaces.SubscriptionInterface;

@RestController
@RequestMapping("/razorpay")
public class SubscriptionController {

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionInterface subscriptionInterface;
	@Autowired
	private UserRequest userRequest;
	
	@PostMapping("/create/subscription/")
	public ResponseEntity<String> createSubscriptionAPI(@RequestBody UserRequest request) throws Exception {
		userRequest.setPlan_id(request.getPlan_id());
		userRequest.setUser_id(request.getUser_id());
		userRequest.setOrganization_id(request.getOrganization_id());
		logger.info("Received create subcription request: {}", userRequest.toString());

		// Call get plan and return
		Plan plan = subscriptionInterface.getPlan(request.getPlan_id());

		// call createSubscription and return in JSON
		JSONObject jsonResponse = subscriptionInterface.createSubscription(plan.getPlanIdRazorPayPlanId(),
				plan.getMonthlyCycle());

		if (jsonResponse.toString().endsWith("{}")) {
			logger.error("Null pointer exception accoured line no : 49", jsonResponse.toString());
			throw new NullPointerException();
		}
		return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
	}

	@PostMapping("/verify/payment/")
	public ResponseEntity<String> verifyPaymentAPI(@RequestBody PaymentRequest paymentRequest) throws Exception {
		logger.info("verify Pyament start : {}", paymentRequest);
		JSONObject jsonResponse = subscriptionInterface.paymentVerifiction(paymentRequest.getSubscription_id(),
				paymentRequest.getPayment_id(), paymentRequest.getUser_id(), paymentRequest.getOrganization_id());

		if (jsonResponse.toString().equals("{}")) {
			logger.error("Null pointer exception accoured line no : 61", jsonResponse.toString());
			throw new NullPointerException();
		}
		return ResponseEntity.ok(jsonResponse.toString());
	}

	@PostMapping("/payment/receipt/")
	public ResponseEntity<?> PaymentReceiptAPI(@RequestParam String subscription_id) throws Exception {
		logger.info("Genrate Pyament receipt start : {}", subscription_id);

		JSONObject json = subscriptionInterface.getPymentHistory(subscription_id);

		if (json.toString().equals("{}")) {
			logger.error("Null pointer exception accoured line no : 75", json.toString());
			throw new NullPointerException();
		}
		return ResponseEntity.ok(json.toString());
	}

	@PostMapping("/cancelled/subscription/")
	public ResponseEntity<String> SubcriptionCancelledAPI(@RequestParam String subscription_id) throws Exception {
		subscriptionInterface.handledCancelledSubcription(subscription_id);
		return ResponseEntity.ok("Subscription cancelled successfully......");
	}
}