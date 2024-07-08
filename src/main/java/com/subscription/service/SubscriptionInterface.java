package com.subscription.service;

import java.text.ParseException;

import org.json.JSONObject;

import com.razorpay.RazorpayException;
import com.subscription.model.Plan;
import com.subscription.request.UserRequest;

public interface SubscriptionInterface {

	public Plan getPlan(int planId);
	public JSONObject createSubscription(String PlanId, int MonthlyCycle, UserRequest userRequest) throws RazorpayException;
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId, Long userId, Long organizationId) throws ParseException;
	public JSONObject getPymentHistory(String razorpaySubscriptionId);
	

}
