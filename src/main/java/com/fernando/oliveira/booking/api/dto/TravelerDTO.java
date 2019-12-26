package com.fernando.oliveira.booking.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelerDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String document;
	
	private List<PhoneDTO> phones;
	
//	private Address adress;
//	
//	private List<Booking> bookings;

}