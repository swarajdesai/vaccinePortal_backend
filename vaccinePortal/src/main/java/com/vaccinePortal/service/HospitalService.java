package com.vaccinePortal.service;

import java.util.List;

import com.vaccinePortal.dto.HospitalDTO;
import com.vaccinePortal.dto.VaccineDTO;

public interface HospitalService {

	List<HospitalDTO> getAllHospitals() throws Exception;

	HospitalDTO addHospital(HospitalDTO hospitalDTO);

	HospitalDTO addVaccinesToHospitals(HospitalDTO hospitalDTO, List<VaccineDTO> vaccines) throws Exception;

}
