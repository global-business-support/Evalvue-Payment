package com.subscription.service;

import org.json.JSONObject;

import com.razorpay.Payment;

class EnumMappingService {
	private EnumMappingService() {
	}
	
	static String subscriptionStatusInString(int num) {
		if (num == 1) {
			return "created";
		} else if (num == 2) {
			return "active";
		} else if (num == 3) {
			return "completed";
		} else if (num == 4) {
			return "cancelled";
		} else if (num == 5) {
			return "pending";
		} else {
			return "expired";
		}
	}
	
	static Integer subscriptionStatus(String status) {
		if (status.equalsIgnoreCase("created")) {
			return 1;
		} else if (status.equalsIgnoreCase("active")) {
			return 2;
		} else if (status.equalsIgnoreCase("completed")) {
			return 3;
		} else if (status.equalsIgnoreCase("cancelled")) {
			return 4;
		} else if (status.equalsIgnoreCase("pending")) {
			return 5;
		} else {
			return 6;
		}
		
	}

	static int paymentStatus(Boolean captured, String status) {
		if (captured && status.equalsIgnoreCase("captured")) {
			return 1;
		} else if (!captured && status.equalsIgnoreCase("failed")) {
			return 2;
		} else {
			return 3;
		}
	}

	static JSONObject paymentResponse(int num, Payment razorpayPaymentStatus) {
		JSONObject jsonObject = new JSONObject();
		if (num == 1) {
			jsonObject.put("Status", "paid");
			jsonObject.put("Transaction", "Successful");
		} else if (num == 2) {
			String error_description = razorpayPaymentStatus.get("error_description");
			String error_source = razorpayPaymentStatus.get("error_source");
			String error_step = razorpayPaymentStatus.get("error_step");
			String error_reason = razorpayPaymentStatus.get("error_reason");
			jsonObject.put("Status", "failed");
			jsonObject.put("payment_cancelled", error_reason);
			jsonObject.put("error_description", error_description);
			jsonObject.put("error_source", error_source);
			jsonObject.put("error_step", error_step);
		} else {
			jsonObject.put("Status", "refunded");
			jsonObject.put("Transaction", "proceed");
		}
		return jsonObject;
	}
}
