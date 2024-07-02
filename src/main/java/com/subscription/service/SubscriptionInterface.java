package com.subscription.service;

import org.json.JSONObject;

import com.subscription.model.Plan;
import com.subscription.request.UserRequest;

public interface SubscriptionInterface {

	public Plan getPlan(int planId);
	public Boolean FecthSubcriptionDetailsById(String subscriptionId);
	public JSONObject createSubscription(String PlanId, int MonthlyCycle, UserRequest userRequest);
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId);

}
