package com.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.subscription.model.Plan;
import com.subscription.request.UserRequest;
import com.subscription.service.SubscriptionInterface;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/razorpay")
public class SubscriptionController {

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionInterface subscriptionInterface;

	@PostMapping("/create/subscription")
	public ResponseEntity<?> createSubscription(@RequestBody UserRequest request) {
		try {
			logger.info("Received request: {}", request.toString());

			// Call get plan and return
			Plan plan = subscriptionInterface.getPlan(request.getPlan_id());

			// call createSubscription and return in JSON
			JSONObject jsonResponse = subscriptionInterface.createSubscription(plan.getPlanIdRazorPayPlanId(),
					plan.getMonthlyCycle(), request);

			if (jsonResponse == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription ID Not Created");
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			return ResponseEntity.ok().headers(headers).body(jsonResponse.toString());
		} catch (Exception e) {
			logger.error("Error creating subscription");
			return ResponseEntity.status(500).body(false);
		}
	}

	@PostMapping("/fetchDeatils")
	public Boolean fetchExistingSubscriptionId(@RequestParam String subscriptionId) {
		try {
			// call Fetching and return in boolean
			Boolean b = subscriptionInterface.FecthSubcriptionDetailsById(subscriptionId);

			return b;
		} catch (Exception e) {
			logger.error("subscription feching error");
		}
		return false;
	}

	@GetMapping("/verify-payment")
	public ResponseEntity<String> verifyPayment(@RequestParam String razorpay_Subscription_Id,
			@RequestParam String payment_id) {
		JSONObject jsonResponse = subscriptionInterface.paymentVerifiction(razorpay_Subscription_Id, payment_id);

		if (jsonResponse == null) {
			System.out.println("json is null...");
			return ResponseEntity.noContent().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return ResponseEntity.ok().headers(headers).body(jsonResponse.toString());
	}
}
