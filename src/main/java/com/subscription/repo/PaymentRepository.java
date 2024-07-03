package com.subscription.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.subscription.response.PaymentResponseEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentResponseEntity,Long> {
 
	    @Modifying
	    @Transactional
	    @Query(value = "INSERT INTO Payment (RazorpayPaymentId, RazorpayOrderId, RazorPaySubscriptionId, UserId, OrganizationId, PaymentStatusId, Amount, PaymentMode, UserEmail, Contact, CreatedOn) "
	    		+ "VALUES (:razorpayPaymentId, :razorpayOrderId, :razorPaySubscriptionId, :userId, :organizationId, :paymentStatusId, :amount, :paymentMode, :userEmail, :contact, :createdOn)", nativeQuery = true)
	    void insertPaymentResponse(
	        @Param("razorpayPaymentId") String razorpayPaymentId,
	        @Param("razorpayOrderId") String razorpayOrderId,
	        @Param("razorPaySubscriptionId") String razorPaySubscriptionId,
	        @Param("userId")Long userId,
	        @Param("organizationId")Long organizationId,
	        @Param("paymentStatusId") int paymentStatusId,
	        @Param("amount") int amount,
	        @Param("paymentMode") String paymentMode,
	        @Param("userEmail") String userEmail,
	        @Param("contact") String contact,
	        @Param("createdOn") Date createdOn
	    );

}
