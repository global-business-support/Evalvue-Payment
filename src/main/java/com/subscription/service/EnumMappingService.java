package com.subscription.service;

import com.subscription.enumMapping.PaymentStatus;
import com.subscription.enumMapping.SubscriptionStatus;

public class EnumMappingService {

	private EnumMappingService() {
	}

	static String getSubscriptionStatusById(Integer num) {
		for (SubscriptionStatus s : SubscriptionStatus.values()) {
			if (s.value == num) {
				return s.name().toLowerCase();
			}
		}
		return null;
	}

	static Integer getSubscriptionStatusByString(String status) {
		return SubscriptionStatus.valueOf(status.toUpperCase()).getValue();
	}

	static int getPaymentStatus(Boolean captured, String status) {
		return PaymentStatus.fromCapturedStatus(captured, status).getValue();
	}

}
