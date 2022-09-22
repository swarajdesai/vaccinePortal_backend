package com.vaccinePortal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaccinePortal.entities.Hospital;
import com.vaccinePortal.entities.UserEntity;
import com.vaccinePortal.entities.VaccineBooking;

public interface VaccineBookingRepository extends JpaRepository<VaccineBooking, Long>{
	
	Optional<VaccineBooking> findByHospital(Hospital hospital);
	
	List<VaccineBooking> findByUser(UserEntity user);
	
	
	
}
