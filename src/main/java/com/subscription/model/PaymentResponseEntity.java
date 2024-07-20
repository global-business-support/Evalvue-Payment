package com.subscription.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
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
	@Column(name = "RazorPaySubscriptionId")
	private String razorPaySubscriptionId;
	@Column(name = "UserId")
	private Long userId;
	@Column(name ="OrganizationId")
	private Long OrganizationId;
	@Column(name = "PaymentStatusId")
	private int PaymentStatusId;
	@Column(name = "Amount")
	private int amount;
	@Column(name = "PaymentMode")
	private String paymentMode;
	@Column(name = "UserEmail")
	private String userEmail;
	@Column(name = "Contact")
	private String contact;
	@Column(name = "CreatedOn")
	private Date createdOn;
	@Column(name = "TransactionId")
	private String transactionId;
	
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
	public String getRazorPaySubscriptionId() {
		return razorPaySubscriptionId;
	}
	public void setRazorPaySubscriptionId(String razorPaySubscriptionId) {
		this.razorPaySubscriptionId = razorPaySubscriptionId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrganizationId() {
		return OrganizationId;
	}
	public void setOrganizationId(Long organizationId) {
		OrganizationId = organizationId;
	}
	public int getPaymentStatusId() {
		return PaymentStatusId;
	}
	public void setPaymentStatusId(int paymentStatusId) {
		PaymentStatusId = paymentStatusId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount / 100;
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "PaymentResponseEntity [paymentId=" + paymentId + ", razorpayPaymentId=" + razorpayPaymentId
				+ ", razorpayOrderId=" + razorpayOrderId + ", razorPaySubscriptionId=" + razorPaySubscriptionId
				+ ", userId=" + userId + ", OrganizationId=" + OrganizationId + ", PaymentStatusId=" + PaymentStatusId
				+ ", amount=" + amount + ", paymentMode=" + paymentMode + ", userEmail=" + userEmail + ", contact="
				+ contact + ", createdOn=" + createdOn + ", transactionId=" + transactionId + "]";
	}
	
	
	

}
