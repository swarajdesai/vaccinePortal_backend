package com.vaccinePortal.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@ToString(exclude ="hosiptals")
public class Vaccine extends BaseEntity{
	@Column()
	private String name;
	@Column()
	private Integer minAge;
	@Column()
	private Integer maxAge;
	@Column(length=1)
	private String gender;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
            name = "vaccine_hospitals",
            joinColumns = @JoinColumn(
                    name = "vaccine_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "hospital_id", referencedColumnName = "id"
            )
    )
	private Set<Hospital> hosiptals;
}
