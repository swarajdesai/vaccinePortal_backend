package com.vaccinePortal.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude ="userRoles")
public class UserEntity extends BaseEntity{
	
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column(unique = true)
	private String email;
	@Column(length = 350)
	private String password;
	@Column(length = 10)
	private String phoneNumber;
	// many-to-many , User *--->* Role
	@ManyToMany
	@JoinTable(name = "user_roles", 
	joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> userRoles = new HashSet<>();

}
