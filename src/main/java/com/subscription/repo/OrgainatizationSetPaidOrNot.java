package com.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.subscription.model.InsertStatusOfPayment;

@Repository
public interface OrgainatizationSetPaidOrNot extends JpaRepository<InsertStatusOfPayment, Long> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE UserOrganizationMapping SET IsPaid = :isPaid WHERE OrganizationId = :organizationId", nativeQuery = true)
	public void insertPaymentStatusOnOrgainaztion(@Param("organizationId") Long organizationId,
			@Param("isPaid") byte isPaid

	);
}
