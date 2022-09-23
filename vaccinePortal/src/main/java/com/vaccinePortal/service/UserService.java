package com.vaccinePortal.service;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;

import com.vaccinePortal.dto.ApiResponse;
import com.vaccinePortal.dto.ResetPasswordDTO;
import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.dto.UserRegResponse;

public interface UserService {

	UserRegResponse registerUser(UserDTO user) throws Exception;
	
	UserDTO getProfileInfo(Authentication auth) throws Exception;
	
	UserDTO updateProfile(Authentication auth,UserDTO userDTO) throws Exception;
	
	ApiResponse changePassword(Authentication auth , ResetPasswordDTO resetPasswordDTO) throws Exception;
	

}
