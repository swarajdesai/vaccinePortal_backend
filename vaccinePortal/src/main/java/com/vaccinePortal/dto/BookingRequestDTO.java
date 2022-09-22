package com.vaccinePortal.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingRequestDTO {
	@NotBlank(message="please provide vaccine details")
	private VaccineDTO vaccine;
	@NotBlank(message="Please provide hospital details")
	private HospitalDTO hospital;
	@NotBlank
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	
}
