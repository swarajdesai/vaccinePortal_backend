package com.vaccinePortal.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "roles")
public class UserDTO {
	@JsonProperty(access = Access.READ_ONLY) // user id will be serialized n sent to clnt BUT it won't be read from clnt
												// n de-serialized
	private Long userId;
	@NotBlank(message = " name must be supplied")
	private String name;
	
	@NotBlank(message = "email must be supplied")
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message = "password must be supplied")
	private String password;
	@NotBlank(message="Phone Number must be supplied")
	private String phoneNumber;
	@Min(1)
	@Max(99)
	private Integer age;
	@NotBlank
	@Pattern(regexp = "^M|F$",message = "Please provide valid gender")
	private String gender;
	// many-to-many , User *--->* Role
	@NotEmpty(message = "at least 1 role should be chosen")
	private Set<UserRole> roles = new HashSet<>();

}
