package com.subscription.service.interfaces;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.subscription.model.Plan;

@Component
public interface SubscriptionInterface {

	public Plan getPlan(int planId)throws Exception ;
	public JSONObject createSubscription(String PlanId, int MonthlyCycle) throws Exception ;
	public JSONObject paymentVerifiction(String razorpaySubscriptionId, String paymentId, Long userId, Long organizationId) throws Exception;
	public JSONObject getPymentHistory(String razorpaySubscriptionId)throws Exception;
	public void handledCancelledSubcription(String subscriptionId) throws Exception;
	

}

