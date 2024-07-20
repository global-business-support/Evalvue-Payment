package com.subscription.service;

import org.json.JSONObject;

import com.razorpay.Payment;
import com.subscription.enumMapping.PaymentStatus;
import com.subscription.enumMapping.SubscriptionStatus;

public class EnumMappingService {

	private EnumMappingService() {
	}

	static String getSubscriptionStatusById(int num) {
		return SubscriptionStatus.fromValue(num).name().toLowerCase();
	}

	static Integer getSubscriptionStatusByString(String status) {
		return SubscriptionStatus.fromString(status).getValue();
	}

	static int getPaymentStatus(Boolean captured, String status) {
		return PaymentStatus.fromCapturedStatus(captured, status).getValue();
	}

}
