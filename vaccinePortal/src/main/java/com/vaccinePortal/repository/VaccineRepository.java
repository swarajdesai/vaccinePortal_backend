package com.vaccinePortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vaccinePortal.entities.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long>{
	@Query("select v from Vaccine v where v.id in ?1")
	List<Vaccine> findVaccinesByIds(List<Long> ids);
}
