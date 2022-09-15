package com.vaccinePortal.service;

import java.util.List;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.RegisterVaccineDTO;
import com.vaccinePortal.dto.VaccineDTO;

public interface VaccineService {
	List<VaccineDTO> getAllVaccines() throws Exception;
	
	VaccineDTO addVaccine(VaccineDTO vaccineDTO);

	VaccineDTO getHospitalsForVaccine(Long id);

}
