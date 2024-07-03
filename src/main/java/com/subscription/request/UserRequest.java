package com.subscription.request;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user")
public class UserRequest {


	private long user_id;
	private long organization_id;
	private int plan_id;

	
	public long getUser_id() {
		return user_id;
	}

	
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getOrganization_id() {
		return organization_id;
	}

	
	public void setOrganization_id(long organization_id) {
		this.organization_id = organization_id;
	}

	public int getPlan_id() {
		return plan_id;
	}

	
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}

		@Override
	public String toString() {
		return "UserRequest [user_Id=" + user_id + ", organization_Id=" + organization_id + ", plan_Id=" + plan_id + "]";
	}

}
