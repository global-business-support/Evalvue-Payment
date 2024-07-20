package com.subscription.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "Subscription")
public class SubscriptionResponseDTO {

    @Id
    @Column(name = "SubscriptionId")
    private Long subscriptionId;

    @Column(name = "RazorPaySubscriptionId")
    private String razorPaySubscriptionId;

    @Column(name = "SubscriptionLink")
    private String subscriptionLink;

    @Column(name = "PlanId")
    private Integer planId;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "NextDueDate")
    private Date nextDueDate;

    @Column(name = "SubscriptionStatusId")
    private Integer subscriptionStatusId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "OrganizationId")
    private Long organizationId;

    // Getters and Setters

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getRazorPaySubscriptionId() {
        return razorPaySubscriptionId;
    }

    public void setRazorPaySubscriptionId(String razorPaySubscriptionId) {
        this.razorPaySubscriptionId = razorPaySubscriptionId;
    }

    public String getSubscriptionLink() {
        return subscriptionLink;
    }

    public void setSubscriptionLink(String subscriptionLink) {
        this.subscriptionLink = subscriptionLink;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Integer getSubscriptionStatusId() {
        return subscriptionStatusId;
    }

    public void setSubscriptionStatusId(Integer subscriptionStatusId) {
        this.subscriptionStatusId = subscriptionStatusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

	@Override
	public String toString() {
		return "SubscriptionResponseDTO [subscriptionId=" + subscriptionId + ", razorPaySubscriptionId="
				+ razorPaySubscriptionId + ", subscriptionLink=" + subscriptionLink + ", planId=" + planId
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", nextDueDate=" + nextDueDate
				+ ", subscriptionStatusId=" + subscriptionStatusId + ", userId=" + userId + ", organizationId="
				+ organizationId + "]";
	}
    
    
}
