package com.vaccinePortal.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	private String name;
	@Column(unique = true)
	private String email;
	@Column(length = 350)
	private String password;
	@Column(length = 10)
	private String phoneNumber;
	@Column
	private LocalDate birthDate;
	@Column(length=1) 
	private String gender;
	// many-to-many , User *--->* Role
	@ManyToMany
	@JoinTable(name = "user_roles", 
	joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> userRoles = new HashSet<>();
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private Set<VaccineBooking> bookings = new HashSet<>();
	
	public Integer getAge() {
		Period diff=  Period.between(this.getBirthDate(), LocalDate.now());
		return diff.getYears()*12+diff.getMonths();
	}

}
