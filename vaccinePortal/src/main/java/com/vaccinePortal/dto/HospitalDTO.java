package com.vaccinePortal.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.vaccinePortal.entities.VaccineBooking;
import com.vaccinePortal.entities.VaccineStock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalDTO {
	private Long id;
	@NotBlank(message = " name must be supplied")
	private String name;
	@NotBlank(message = " address must be supplied")
	private String address;
	@NotBlank(message = " phone must be supplied")
	private String phoneNumber;
	@JsonIgnore
	private Set<VaccineStock> vaccineStocks;
	
	@JsonIgnore
	private Set<VaccineBooking> bookings;
}
