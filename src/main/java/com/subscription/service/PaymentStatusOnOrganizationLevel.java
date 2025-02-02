package com.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subscription.model.InsertStatusOfPayment;
import com.subscription.repo.OrgainatizationSetPaidOrNot;

@Service
public class PaymentStatusOnOrganizationLevel {

	@Autowired
	OrgainatizationSetPaidOrNot orgainatizationSetPaidOrNot;
	@Autowired
	InsertStatusOfPayment insertStatusOfPayment;

	public void setPaymentStatusOnOrgainaztion() {
		orgainatizationSetPaidOrNot.insertPaymentStatusOnOrgainaztion(insertStatusOfPayment.getOrganization_id(),
				insertStatusOfPayment.getIsPaid());
	}
}
