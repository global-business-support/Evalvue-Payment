package com.subscription.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserOrganizationMapping")
@Component
public class InsertStatusOfPayment {
	@Id
	@Column(name = "OrganizationId")
	private Long organization_id;
	@Column(name = "IsPaid")
	private byte isPaid;

	
	public byte getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(byte isPaid) {
		this.isPaid = isPaid;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

}