package com.vaccinePortal.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDTO {
	
	private Long id;
	private VaccineDTO vaccine;
	
	private HospitalDTO hospital;
	
	private LocalDate date;
	
	private UserDTO user;
	
	private BookingStatus status;
}
