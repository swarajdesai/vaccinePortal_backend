package com.vaccinePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vaccinePortal.entities.Hospital;
import com.vaccinePortal.entities.Vaccine;
import com.vaccinePortal.entities.VaccineStock;

public interface VaccineStockRepository extends JpaRepository<VaccineStock , Long>{
	
	@Query("select v from VaccineStock v where v.hospital = ?1 and v.vaccine = ?2")
	public Optional<VaccineStock> findByHospitalAndVaccine(Hospital hospital , Vaccine vaccine);
}
