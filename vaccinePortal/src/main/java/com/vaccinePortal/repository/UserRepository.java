package com.vaccinePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vaccinePortal.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query("select u from UserEntity u join fetch u.userRoles where u.email=?1")
//	@Query("select u from UserEntity u  where u.email=?1")
	Optional<UserEntity> findByEmail(String email);
	
	@Query("select u from UserEntity u where u.phoneNumber=?1")
	Optional<UserEntity> findByPhone(String phoneNumber);
	
	@Query("select u from UserEntity u where u.email=?1")
	Optional<UserEntity> findByOnlyEmail(String email);
	
}
