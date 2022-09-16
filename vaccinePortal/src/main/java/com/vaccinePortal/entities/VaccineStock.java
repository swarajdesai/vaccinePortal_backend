package com.vaccinePortal.entities;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vaccine_stock")
@Getter
@Setter
public class VaccineStock extends BaseEntity {
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="hospital")
	private Hospital hospital;
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="vaccine")
	private Vaccine vaccine;
	@Column
	private Integer qty;
}
