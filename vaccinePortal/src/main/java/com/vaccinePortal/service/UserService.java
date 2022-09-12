package com.vaccinePortal.service;

import javax.validation.Valid;

import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.dto.UserRegResponse;

public interface UserService {

	UserRegResponse registerUser(UserDTO user);

}
