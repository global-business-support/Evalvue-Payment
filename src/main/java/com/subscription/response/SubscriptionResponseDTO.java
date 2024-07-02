package com.subscription.response;

import java.util.Date;

import com.subscription.request.UserRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class SubscriptionResponseDTO {
	@Id
	@Column(name = "SubscriptionId")
	private Long id;
	@Column(name="RazorPaySubscriptionId")
    private String subscriptionId;
	@Column(name="SubcriptionLink")
    private String subscriptionLink;
	@Column(name="PlanId")
    private int planId;
	@Column(name="StartDate")
    private Date createDate;
	@Column(name="NextDueDate")
    private Date nextDueDate;
	@Column(name="EndDate")
    private Date endDate;
	@Column(name="Status")
    private String status;
	@Column(name="UserId")
	private Long userId;
	@Column(name="OrganizationId")
	private Long organizationId;
	
    public SubscriptionResponseDTO(Long id, String subscriptionLink, String status) {
		super();
		this.id = id;
		this.subscriptionLink = subscriptionLink;
		this.status = status;
	}

	// Getters and setters

    public SubscriptionResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscriptionLink() {
        return subscriptionLink;
    }

    public void setSubscriptionLink(String subscriptionLink) {
        this.subscriptionLink = subscriptionLink;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
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
		return "SubscriptionResponseDTO [id=" + id + ", subscriptionId=" + subscriptionId + ", subscriptionLink="
				+ subscriptionLink + ", planId=" + planId + ", createDate=" + createDate + ", nextDueDate="
				+ nextDueDate + ", endDate=" + endDate + ", status=" + status + ", userId=" + userId
				+ ", organizationId=" + organizationId + "]";
	}

	
    
    
    
    
}
