package com.vaccinePortal.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterVaccineDTO {
	private HospitalDTO hospital;
	
	private List<VaccineDTO> vaccines;

}
