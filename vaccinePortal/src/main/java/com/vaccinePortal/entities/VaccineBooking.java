package com.vaccinePortal.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class VaccineBooking extends BaseEntity{
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="vaccine")
	private Vaccine vaccine;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="hospital")
	private Hospital hospital;
	
	@Column
	private LocalDate date;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="user")
	private UserEntity user;
	
	
}
