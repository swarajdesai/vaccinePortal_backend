package com.vaccinePortal.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineHospResponse {
	private VaccineDTO vaccineDTO;
	
	private List<HospitalDTO> hospitals;
	
	private Map<String , Integer> quantities;
}
