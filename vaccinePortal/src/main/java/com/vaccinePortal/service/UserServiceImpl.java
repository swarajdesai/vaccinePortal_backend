package com.vaccinePortal.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	@Override
	public UserRegResponse registerUser(UserDTO user) throws Exception {
		// Objective : 1 rec inserted in users table n insert n recs in link table
		// user_roles
		// 1. Map dto --> entity
		System.out.println("from db "+userRepo.findByEmail(user.getEmail()));
		if(userRepo.findByOnlyEmail(user.getEmail()).isPresent()) throw new Exception("Email Already registered");
		if(userRepo.findByPhone(user.getPhoneNumber()).isPresent()) throw new Exception("Phone Number Already registered");
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

}
