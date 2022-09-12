package com.vaccinePortal.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaccinePortal.dto.UserRole;
import com.vaccinePortal.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
 //find by role : enum
	Optional<Role> findByRoleName(UserRole role);
	Set<Role> findByRoleNameIn(Set<UserRole> roles);
}
