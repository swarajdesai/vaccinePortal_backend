package com.vaccinePortal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinePortal.dto.AuthRequest;
import com.vaccinePortal.dto.AuthResp;
import com.vaccinePortal.dto.ResetPasswordDTO;
import com.vaccinePortal.dto.UserDTO;
import com.vaccinePortal.jwt_utils.JwtUtils;
import com.vaccinePortal.service.UserService;

import lombok.extern.slf4j.Slf4j;
import com.vaccinePortal.dto.ApiResponse;
@RestController
@RequestMapping("/auth")
@Slf4j
@CrossOrigin
public class SignInSignUpController {
//dep : JWT utils : for generating JWT
	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;
	//dep : user service for handling users
	@Autowired
	private UserService userService;

	// add a method to authenticate user . In case of success --send back token , o.w
	// send back err mesg
	@PostMapping("/signin")
	public ResponseEntity<?> validateUserCreateToken(@RequestBody @Valid AuthRequest request) {
		// store incoming user details(not yet validated) into Authentication object
		// Authentication i/f ---> implemented by UserNamePasswordAuthToken
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		log.info("auth token " + authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
			log.info("auth token again " + authenticatedDetails.getAuthorities());
			// => auth succcess
			List<String> roles = authenticatedDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList();
			return ResponseEntity.ok(new AuthResp("Auth successful!", utils.generateJwtToken(authenticatedDetails) ,  roles));
		} catch (BadCredentialsException e) { // lab work : replace this by a method in global exc handler
			// send back err resp code
			System.out.println("err "+authToken);
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage()));
			
		}

	}
	//add request handling method for user registration
	@PostMapping("/signup")
	public ResponseEntity<?> userRegistration(@RequestBody @Valid UserDTO user) throws Exception
	{
		System.out.println("in reg user : user "+user+" roles "+user.getRoles());//{....."roles" : [ROLE_USER,...]}
		//invoke service layer method , for saving : user info + associated roles info
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));		
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getProfileInfo(Authentication auth) throws Exception
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.getProfileInfo(auth));		
	}
	
	@PostMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@RequestBody  UserDTO user,Authentication auth) throws Exception
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateProfile(auth, user));		
	}
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ResetPasswordDTO dto,Authentication auth) throws Exception
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(auth, dto));		
	}
	
}
