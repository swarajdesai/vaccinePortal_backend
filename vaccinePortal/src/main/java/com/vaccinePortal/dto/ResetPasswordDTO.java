package com.vaccinePortal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
	private String prevPassword;
	private String newPassword;
}
