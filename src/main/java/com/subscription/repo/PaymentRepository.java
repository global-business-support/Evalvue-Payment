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
	    @Query(value = "INSERT INTO Payment (RazorpayPaymentId, RazorpayOrderId, Amount, PaymentMode, UserEmail, Contact, CreatedAt, Status, Captured) VALUES (:razorpayPaymentId, :razorpayOrderId, :amount, :paymentMode, :userEmail, :contact, :createdAt, :status, :captured)", nativeQuery = true)
	    void insertPaymentResponse(
	        @Param("razorpayPaymentId") String razorpayPaymentId,
	        @Param("razorpayOrderId") String razorpayOrderId,
	        @Param("amount") int amount,
	        @Param("paymentMode") String paymentMode,
	        @Param("userEmail") String userEmail,
	        @Param("contact") String contact,
	        @Param("createdAt") Date createdAt,
	        @Param("status") String status,
	        @Param("captured") Boolean captured
	    );

}
