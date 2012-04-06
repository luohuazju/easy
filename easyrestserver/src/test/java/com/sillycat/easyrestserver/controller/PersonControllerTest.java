package com.sillycat.easyrestserver.controller;

import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.io.IOException;

import javax.servlet.ServletException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.sillycat.easyrestserver.model.Company;
import com.sillycat.easyrestserver.model.Person;
import com.sillycat.easyrestserver.service.PersonService;

public class PersonControllerTest {

	@Mock
	private PersonService mockPersonService;

	PersonController personController;

	Person person;

	ObjectMapper jsonMapper;

	@Before
	public void setUp() throws ServletException, IOException {
		MockitoAnnotations.initMocks(this);

		personController = new PersonController();
		personController.setPersonService(mockPersonService);

		person = new Person();
		person.setCompany(new Company());
		person.setId(3);
		person.setPersonName("person3");

		jsonMapper = new ObjectMapper();
	}

	@Test
	public void get() throws Exception {

		Mockito.when(mockPersonService.get(3)).thenReturn(person);

		MockMvcBuilders
				.standaloneSetup(personController)
				.build()
				.perform(
						MockMvcRequestBuilders.get("/person/3").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().type(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.personName").value("person3"));
	}

	@Test
	public void add() throws Exception {
		person.setId(null);
		String jsonPerson = jsonMapper.writeValueAsString(person);
		MockMvcBuilders
				.standaloneSetup(personController)
				.build()
				.perform(
						MockMvcRequestBuilders.post("/person")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.body(jsonPerson.getBytes()))
				.andExpect(status().isOk())
				.andExpect(content().type(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.personName").value("person3"));
	}
}
