package com.subscription.response;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payment")
public class PaymentResponseEntity {

	@Id
	@Column(name = "PaymentId")
	private Long paymentId;
	@Column(name = "RazorpayPaymentId")
	private String razorpayPaymentId;
	@Column(name = "RazorpayOrderId")
	private String razorpayOrderId;
	@Column(name = "Amount")
	private int amount;
	@Column(name = "PaymentMode")
	private String paymentMode;
	@Column(name = "UserEmail")
	private String userEmail;
	@Column(name = "Contact")
	private String contact;
	@Column(name = "CreatedAt")
	private Date createdAt;
	@Column(name = "Status")
	private String status;
	@Column(name = "Captured")
	private Boolean captured;
	@Column(name = "InvoiceId")
	private String invoiceId;
	@Column(name="InvoiceLink")
	private String InvoiceLink;
	@Column(name = "SubscriptionId")
	private String subscriptionId;
	

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount/100;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceLink() {
		return InvoiceLink;
	}

	public void setInvoiceLink(String invoiceLink) {
		InvoiceLink = invoiceLink;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getCaptured() {
		return captured;
	}

	public void setCaptured(Boolean captured) {
		this.captured = captured;
	}

	@Override
	public String toString() {
		return "PaymentResponseEntity [paymentId=" + paymentId + ", razorpayPaymentId=" + razorpayPaymentId
				+ ", razorpayOrderId=" + razorpayOrderId + ", amount=" + amount + ", paymentMode=" + paymentMode
				+ ", userEmail=" + userEmail + ", contact=" + contact + ", createdAt=" + createdAt + ", status="
				+ status + ", captured=" + captured + ", invoiceId=" + invoiceId + ", InvoiceLink=" + InvoiceLink
				+ ", subscriptionId=" + subscriptionId + "]";
	}

	
	

}
