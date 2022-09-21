package com.vaccinePortal.dto;

import java.util.Set;
import com.vaccinePortal.entities.VaccineStock;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VaccineDTO {
	// n de-serialized
	private Long id;
	@NotBlank(message = " name must be supplied")
	private String name;
	@Min(0)
	private Integer minAge;
	@Min(0)
	private Integer maxAge;
	@NotBlank
	@Pattern(regexp = "^M|F$",message = "Please provide valid gender")
	private String gender;
	private boolean isEligible;
	@JsonIgnore
	private Set<VaccineStock> vaccineStock;
}
