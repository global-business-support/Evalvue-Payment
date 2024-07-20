package com.subscription.response.json_obj;

import org.json.JSONObject;

import com.razorpay.Payment;

public class JsonResponse {

	public static JSONObject paymentResponse(int num, Payment razorpayPaymentStatus) {
		JSONObject jsonObject = new JSONObject();
		switch (num) {
		case 1:
			jsonObject.put("Status", "paid");
			jsonObject.put("Transaction", "Successful");
			break;
		case 2:
			String error_description = razorpayPaymentStatus.get("error_description");
			String error_source = razorpayPaymentStatus.get("error_source");
			String error_step = razorpayPaymentStatus.get("error_step");
			String error_reason = razorpayPaymentStatus.get("error_reason");
			jsonObject.put("Status", "failed");
			jsonObject.put("payment_cancelled", error_reason);
			jsonObject.put("error_description", error_description);
			jsonObject.put("error_source", error_source);
			jsonObject.put("error_step", error_step);
			break;
		case 3:
			jsonObject.put("Status", "refunded");
			jsonObject.put("Transaction", "proceed");
			break;
		default:
			throw new IllegalArgumentException("Unknown payment status: " + num);
		}
		
		return jsonObject;
	}
}
