package com.vaccinePortal.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vaccinePortal.dto.BookingStatus;

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
	
	@Column
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="user")
	private UserEntity user;
	
	
}
