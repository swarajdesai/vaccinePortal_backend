package com.vaccinePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaccinePortal.entities.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long>{

}
