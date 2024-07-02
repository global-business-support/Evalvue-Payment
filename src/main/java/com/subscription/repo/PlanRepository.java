package com.subscription.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.subscription.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
	
	@Query(nativeQuery = true, value = "SELECT RazorPayPlanId, MonthlyCycle FROM [Plan] WHERE PlanId = :id")
      public List<Object[]> getPlanDetail(@Param("id") int id);
	
	

}
