package com.vaccinePortal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinePortal.dto.BookingDTO;
import com.vaccinePortal.dto.BookingRequestDTO;
import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.dto.VaccineHospResponse;
import com.vaccinePortal.service.VaccineService;


@RestController
@RequestMapping("/vaccine")
@CrossOrigin
public class VaccineController {
	
	@Autowired
	private VaccineService vaccineServ;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<VaccineDTO>> getAllVaccines() throws Exception{
		return new ResponseEntity<>(vaccineServ.getAllVaccines(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<VaccineDTO> addVaccine(@RequestBody VaccineDTO vDTO) throws Exception{
		return new ResponseEntity<>(vaccineServ.addVaccine(vDTO),HttpStatus.CREATED);
	}
	
	@GetMapping("/getInfo/{id}")
	public ResponseEntity<VaccineHospResponse> getuser(@PathVariable long id) throws Exception {
		return new ResponseEntity<>(vaccineServ.getHospitalsForVaccine(id),HttpStatus.OK);
	}
	
	@GetMapping("/eligibleVaccines")
	public ResponseEntity<List<VaccineDTO>> getEligibleVaccines(Authentication auth) throws Exception{
		return new ResponseEntity<>(vaccineServ.getMyEligibleVaccines(auth),HttpStatus.OK);
	}
	
	@PostMapping("/book")
	public ResponseEntity<BookingDTO> bookVaccine(Authentication auth ,@RequestBody BookingRequestDTO booking) throws Exception{
		return new ResponseEntity<>(vaccineServ.bookVaccine(auth, booking.getHospital(), booking.getVaccine(), booking.getDate()),HttpStatus.CREATED);
	}
	
//	@GetMapping("/getBookings/hospital/{id}")
//	public ResponseEntity<List<BookingDTO>> getVaccinesByHospital(Authen)
}
