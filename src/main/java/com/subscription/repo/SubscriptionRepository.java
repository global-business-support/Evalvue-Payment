package com.subscription.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.subscription.response.SubscriptionResponseDTO;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionResponseDTO, Long> {
	
	  @Modifying
	    @Transactional
	    @Query(nativeQuery = true, value = "INSERT INTO Subscription (RazorPaySubscriptionId, SubscriptionLink, PlanId, SubscriptionStatusId, UserId, OrganizationId) VALUES (:razorPaySubscriptionId, :subscriptionLink, :planId, :subscriptionStatusId, :userId, :organizationId)")
	    void saveAllDetailsById(
	            @Param("razorPaySubscriptionId") String razorPaySubscriptionId,
	            @Param("subscriptionLink") String subscriptionLink,
	            @Param("planId") int planId,
	            @Param("subscriptionStatusId") int subscriptionStatusId,
	            @Param("userId") Long userId,
	            @Param("organizationId") Long organizationId);
	
	 
	  @Modifying
	    @Transactional
	    @Query(nativeQuery = true, value = "UPDATE Subscription SET StartDate = :startDate, NextDueDate = :nextDueDate, EndDate = :endDate, SubscriptionStatusId = :subscriptionStatusId WHERE RazorPaySubscriptionId = :razorPaySubscriptionId")
	    void updateSubscriptionDetailsById(
	            @Param("razorPaySubscriptionId") String razorPaySubscriptionId,
	            @Param("startDate") Date startDate,
	            @Param("nextDueDate") Date nextDueDate,
	            @Param("endDate") Date endDate,
	            @Param("subscriptionStatusId") int subscriptionStatusId
	    );
		         

}
