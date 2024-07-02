package com.subscription.request;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Component
public class CreateSubscriptionRequest {

	@Column(name = "RazorPayPlanId")
	private String razorPayId;
	@Column(name = "MonthlyCycle")
	private int montlyCycle;

	public String getRazorPayId() {
		return razorPayId;
	}

	public void setRazorPayId(String razorPayId) {
		this.razorPayId = razorPayId;
	}

	public int getMontlyCycle() {
		return montlyCycle;
	}

	public void setMontlyCycle(int montlyCycle) {
		this.montlyCycle = montlyCycle;
	}

}
