package com.vaccinePortal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;

import com.vaccinePortal.dto.BookingDTO;
import com.vaccinePortal.dto.BookingResp;
import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.RegisterVaccineDTO;
import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.dto.VaccineHospResponse;

public interface VaccineService {
	
	
	VaccineDTO addVaccine(VaccineDTO vaccineDTO) throws Exception;

	VaccineHospResponse getHospitalsForVaccine(Long id) throws Exception;
	
	List<VaccineDTO> getMyEligibleVaccines(Authentication auth) throws Exception;
	
	BookingResp bookVaccine(Authentication auth ,HospitalDTO hospital ,  VaccineDTO vaccine , LocalDate date) throws Exception;

	List<VaccineDTO> getAllVaccines(Authentication auth) throws Exception;
	
	BookingResp cancelBooking(Authentication auth ,BookingDTO bookingDTO) throws Exception;
	
	List<BookingDTO> getMyBookings(Authentication auth) throws Exception;

}
