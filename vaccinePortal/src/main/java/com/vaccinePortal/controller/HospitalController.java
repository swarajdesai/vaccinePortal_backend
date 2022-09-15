package com.vaccinePortal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.RegisterVaccineDTO;
import com.vaccinePortal.dto.VaccineDTO;
import com.vaccinePortal.service.HospitalService;
import com.vaccinePortal.service.VaccineService;

@RestController
@RequestMapping("/hospital")
@CrossOrigin
public class HospitalController {

	@Autowired
	private HospitalService hospServ;
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<HospitalDTO>> getAll() throws Exception{
		return new ResponseEntity<>(hospServ.getAllHospitals(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<HospitalDTO> addVaccine(@RequestBody HospitalDTO hDTO){
		return new ResponseEntity<>(hospServ.addHospital(hDTO),HttpStatus.CREATED);
	}
	
	@PostMapping("/registerVaccine")
	public ResponseEntity<HospitalDTO> registerVaccine(@RequestBody RegisterVaccineDTO rVDTO) throws Exception{
		return new ResponseEntity<>(hospServ.addVaccinesToHospitals(rVDTO.getHospital(), rVDTO.getVaccines()),HttpStatus.OK);
	}
}
