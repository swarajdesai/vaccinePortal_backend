package com.vaccinePortal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaccinePortal.dto.ApiResponse;
import com.vaccinePortal.dto.AuthResp;
import com.vaccinePortal.dto.ResetPasswordDTO;
import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.dto.UserRegResponse;
import com.vaccinePortal.entities.UserEntity;
import com.vaccinePortal.repository.RoleRepository;
import com.vaccinePortal.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	// dep : user repo n role repo
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	// mapper
	@Autowired
	private ModelMapper mapper;
	// password enc
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager manager;

	@Override
	public UserRegResponse registerUser(UserDTO user) throws Exception {
		// Objective : 1 rec inserted in users table n insert n recs in link table
		// user_roles
		// 1. Map dto --> entity
		System.out.println("from db "+userRepo.findByEmail(user.getEmail()));
		if(userRepo.findByOnlyEmail(user.getEmail()).isPresent()) throw new Exception("Email Already registered");
		if(userRepo.findByPhone(user.getPhoneNumber()).isPresent()) throw new Exception("Phone Number Already registered");
		if(user.getBirthDate().isAfter(LocalDate.now())) throw new Exception("Birth Date should not be future date");
		UserEntity userEntity = mapper.map(user, UserEntity.class);
		// 2. Map Set<UserRole : enum> ---> Set<Role :entity> n assign it to the
		// transient user entity
		userEntity.setUserRoles(roleRepo.findByRoleNameIn(user.getRoles()));
		// 3. encode pwd
		userEntity.setPassword(encoder.encode(user.getPassword()));
		
//		userEntity.setPhoneNumber(user.getPhoneNumber());
		// 4 : Save user details
		System.out.println("saving noww");
		UserEntity persistentUser = userRepo.save(userEntity);
		return new UserRegResponse("User registered successfully ");
	}

	@Override
	public UserDTO getProfileInfo(Authentication auth) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO updateProfile(Authentication auth, UserDTO userDTO) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
//		if(!user.getEmail().equalsIgnoreCase(userDTO.getEmail()) && userRepo.findByOnlyEmail(userDTO.getEmail()).isPresent()) {
//			throw new Exception("Email Already registered");
//		}
		if(!user.getPhoneNumber().equalsIgnoreCase(userDTO.getPhoneNumber()) && userRepo.findByPhone(userDTO.getPhoneNumber()).isPresent()){
			throw new Exception("Phone Number Already registered");
		}
		if(userDTO.getBirthDate().isAfter(LocalDate.now())) throw new Exception("Birth Date should not be future date");
		
		user.setBirthDate(userDTO.getBirthDate());
		user.setEmail(userDTO.getEmail());
		user.setGender(userDTO.getGender());
		user.setName(userDTO.getName());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		return mapper.map(userRepo.save(user),UserDTO.class);
	}

	@Override
	public ApiResponse changePassword(Authentication auth, ResetPasswordDTO resetPasswordDTO) throws Exception {
		UserEntity user = userRepo.findByOnlyEmail(auth.getName()).orElseThrow(()-> new Exception("No User Found"));
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(),
				resetPasswordDTO.getPrevPassword());
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
			// => auth succcess
			user.setPassword(encoder.encode(resetPasswordDTO.getNewPassword()));
			userRepo.save(user);
			return new ApiResponse("Password updated succesfully");
		} catch (BadCredentialsException e) { // lab work : replace this by a method in global exc handler
			throw new Exception("Password you entered is incorrect");
		}
		
	}

}
