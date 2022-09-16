package com.vaccinePortal.entities;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@ToString(exclude ="vaccineStocks")
public class Hospital extends BaseEntity{
	@Column()
	private String name;
	@Column()
	private String address;
	@Column()
	private String phoneNumber;
	@OneToMany(mappedBy="hospital",fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private Set<VaccineStock> vaccineStocks;
	
}
