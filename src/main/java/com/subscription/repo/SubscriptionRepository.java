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
	 @Query(nativeQuery = true, value = "INSERT INTO Subscription(RazorPaySubscriptionId, SubcriptionLink, PlanId, Status, UserId, OrganizationId) VALUES (:razorPaySubscriptionId, :subcriptionLink, :planId, :status, :userId, :organizationId)")
	    public void saveAllDetailsById(
	                            @Param("razorPaySubscriptionId") String razorPaySubscriptionId, 
	                            @Param("subcriptionLink") String subcriptionLink,
	                            @Param("planId") int planId,
	                            @Param("status") String status,
	                            @Param("userId") Long userId,
	                            @Param("organizationId") Long organizationId);
	 
	 
		@Transactional
		@Modifying
		@Query(nativeQuery = true, value = "UPDATE Subscription SET StartDate = :startDate, NextDueDate = :nextDueDate, EndDate = :endDate, Status = :status WHERE RazorPaySubscriptionId = :razorPaySubscriptionId ")
		public void updateSubscriptionDetailsById(
				@Param("razorPaySubscriptionId") String razorPaySubscriptionId,
				@Param("startDate") Date startDate,
				@Param("nextDueDate") Date nextDueDate,
				@Param("endDate") Date endDate,
				@Param("status") String status
				);
		         

}
