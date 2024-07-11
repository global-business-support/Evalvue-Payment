package com.subscription.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Component
@Entity
public class Plan {
	@Id
	@Column(name = "PlanId")
	private int planId;
	@Column(name = "MonthlyCycle")
	private Integer monthlyCycle;
	@Column(name = "RazorPayPlanId")
	private String planIdRazorPayPlanId;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getMonthlyCycle() {
		return monthlyCycle;
	}

	public void setMonthlyCycle(int monthlyCycle) {
		this.monthlyCycle = monthlyCycle;
	}

	public String getPlanIdRazorPayPlanId() {
		return planIdRazorPayPlanId;
	}

	public void setPlanIdRazorPayPlanId(String planIdRazorPayPlanId) {
		this.planIdRazorPayPlanId = planIdRazorPayPlanId;
	}

	@Override
	public String toString() {
		return "Plan [planId=" + planId + ", monthlyCycle=" + monthlyCycle + ", planIdRazorPayPlanId="
				+ planIdRazorPayPlanId + "]";
	}

}
