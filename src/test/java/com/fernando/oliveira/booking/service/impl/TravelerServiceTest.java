package com.fernando.oliveira.booking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelerServiceTest {

	@Autowired
	private TravelerService travelerService;

	@Test(expected = Test.None.class)
	public void travelerMustHavePhone() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("1111111111").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 01").email("traveler01@gmail.com").document("11111111111")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getPhones());
		Assert.assertNotNull(result.getPhones().get(0).getTraveler());

	}

	@Test
	public void travelerMustHaveNameAndValidEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("222222222").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 02").email("traveler02@gmail.com").document("22222222222")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result.getName());
		Assert.assertNotNull(result.getEmail());

	}

	@Test
	public void travelerMustHaveUniqueName() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("333333333").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 03").email("traveler03@gmail.com").document("33333333333")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByName(result.getName());

		Assert.assertNotNull(result.getName());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getName(), t.getName());

	}

	@Test
	public void travelerMustHaveUniqueEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("444444444").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 04").email("traveler04@gmail.com").document("44444444444")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByEmail(result.getEmail());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getEmail(), t.getEmail());

	}

	@Test
	public void travelerMustHaveUniqueDocument() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("555555555").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 05").email("traveler05@gmail.com").document("55555555555")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByDocument(result.getDocument());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getDocument(), t.getDocument());

	}

	@Test
	public void mustReturnIdTraveler() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("666666666").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 06").email("traveler06@gmail.com").document("66666666666")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result.getId());

	}

	@Test
	@Ignore
	public void shoudReturnExceptionUniqueEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 09").email("traveler09@gmail.com").document("9999999999")
				.phones(phones).build();

		travelerService.save(traveler);
		
		
		Phone phone2 = Phone.builder().prefix(new Integer(11)).number("888888888").build();
		List<Phone> phones2 = new ArrayList<Phone>();
		phones.add(phone2);
		Traveler traveler2 = Traveler.builder().name("Traveler 10").email("traveler09@gmail.com").document("10101010")
				.phones(phones2).build();
		
		
		Throwable exception  = Assertions.catchThrowable(() -> travelerService.save(traveler2));
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o email informado");
		
				
		
	}

	@Test
	public void shoudReturnExceptionUniqueName() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 07").email("traveler07@gmail.com").document("777777")
				.phones(phones).build();
		Traveler traveler2 = Traveler.builder().name("Traveler 07").email("traveler08@gmail.com").document("88")
				.phones(phones).build();

		travelerService.save(traveler);
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler2));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o nome informado");

	}

	@Test
	public void shoudReturnExceptionUniqueDocument() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 11").email("traveler11@gmail.com").document("88888888888")
				.phones(phones).build();
		
		Traveler traveler2 = Traveler.builder().name("Traveler 12").email("traveler12@gmail.com").document("88888888888")
				.phones(phones).build();

		travelerService.save(traveler);
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler2));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o documento informado");


	}

	@Test
	public void shoudReturnExceptionInvalidEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 07").email("traveler07@gmail").document("77777777777")
				.phones(phones).build();
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Email inválido");
		
	}
	
	@Test
	public void shouldUpdateTravelerName() {
		//cenario
		Optional<Traveler> result = travelerService.findById(1L);
		
		Traveler traveler = result.get();
		traveler.setName("traveler updated");
		
		Traveler travelerUpdate = travelerService.update(traveler);
		
		Assert.assertEquals("traveler updated", travelerUpdate.getName());
		
		
	}

	@Test
	public void shouldUpdateTravelerPhone() {
		//cenario
		Optional<Traveler> result = travelerService.findById(1L);
		Phone phone = result.get().getPhones().get(0);
		phone.setNumber("99999-8888");
		
		//acao
		Traveler updateTraveler = travelerService.update(result.get());
		
		//verificacao
		Assert.assertEquals("99999-8888", updateTraveler.getPhones().get(0).getNumber());
		
		
		
		
	}
	
	
	
	
}
