package com.fernando.oliveira.booking.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.booking.api.dto.PhoneDTO;
import com.fernando.oliveira.booking.api.dto.TravelerDTO;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.PhoneService;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@RestController
@RequestMapping("/api/travelers")
public class TravelerController {

	@Autowired
	TravelerService travelerService;
	
	@Autowired
	PhoneService phoneService;
	
	
	@PostMapping
	public ResponseEntity save(@RequestBody TravelerDTO dto) {
		
		List<Phone> phones = converterDTOToListPhone(dto);
		
		Traveler traveler = convertTravelerDTO(dto, phones);
		
		try {
			Traveler travelerSaved = travelerService.save(traveler);
			return new ResponseEntity(travelerSaved, HttpStatus.CREATED);
			
		}catch(TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private Traveler convertTravelerDTO(TravelerDTO dto, List<Phone> phones) {
		Traveler traveler = Traveler.builder()
								.name(dto.getName())
								.email(dto.getEmail())
								.document(dto.getDocument())
								.phones(phones)
								.build();
		return traveler;
	}

	private List<Phone> converterDTOToListPhone(TravelerDTO dto) {
		List<Phone> phones = new ArrayList<Phone>();
		for(PhoneDTO phoneDTO : dto.getPhones()) {
			Phone phone = phoneService.convertToEntity(phoneDTO);
			phones.add(phone);
		}
		return phones;
	}
	
	@PutMapping("{id}")
	public ResponseEntity update( @PathVariable("id") Long id,  @RequestBody TravelerDTO dto) {
		
		List<Phone> phones = converterDTOToListPhone(dto);
		Traveler traveler = convertTravelerDTO(dto, phones);
		
		try {
		Traveler travelerUpdated = travelerService.update(traveler);
		
			return new ResponseEntity(travelerUpdated, HttpStatus.OK);
		}catch (TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	
	
}