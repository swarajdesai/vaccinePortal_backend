package com.vaccinePortal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingResp {
	private String message;
	private LocalDate bookingDate;
	private LocalDateTime timeStamp;
	public BookingResp(String message) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
}
