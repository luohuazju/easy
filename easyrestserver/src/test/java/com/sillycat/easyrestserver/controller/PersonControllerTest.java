//package com.sillycat.easyrestserver.controller;
//
//import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.server.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletException;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.server.request.MockMvcRequestBuilders;
//import org.springframework.test.web.server.setup.MockMvcBuilders;
//
//import com.sillycat.easyapi.rest.json.Company;
//import com.sillycat.easyapi.rest.json.Person;
//import com.sillycat.easyrestserver.service.PersonService;
//
//public class PersonControllerTest {
//
//	@Mock
//	private PersonService mockPersonService;
//
//	PersonController personController;
//
//	Person item;
//
//	List<Person> items;
//
//	ObjectMapper jsonMapper;
//
//	@Before
//	public void setUp() throws ServletException, IOException {
//		MockitoAnnotations.initMocks(this);
//
//		personController = new PersonController();
//		personController.setPersonService(mockPersonService);
//
//		item = new Person(3, "person3");
//		Person person1 = new Person(1, "person1");
//
//		Company company1 = new Company(1, "company1");
//
//		List<Person> personList = new ArrayList<Person>();
//		personList.add(item);
//		personList.add(person1);
//		company1.setPersons(personList);
//
//		item.setCompany(company1);
//
//		items = personList;
//
//		jsonMapper = new ObjectMapper();
//	}
//
//	@Test
//	public void getAll() throws Exception {
//		Mockito.when(mockPersonService.getAll()).thenReturn(items);
//
//		MockMvcBuilders
//				.standaloneSetup(personController)
//				.build()
//				.perform(
//						MockMvcRequestBuilders.get("/person/persons").accept(
//								MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().mimeType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.[0].personName").value("person3"));
//	}
//
//	@Test
//	public void get() throws Exception {
//
//		Mockito.when(mockPersonService.get(3)).thenReturn(item);
//
//		MockMvcBuilders
//				.standaloneSetup(personController)
//				.build()
//				.perform(
//						MockMvcRequestBuilders.get("/person/3").accept(
//								MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().mimeType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.personName").value("person3"));
//	}
//
//	@Test
//	public void throwJSONError() throws Exception {
//		item.setId(13);
//		Mockito.when(mockPersonService.get(13)).thenReturn(item);
//
//		MockMvcBuilders
//				.standaloneSetup(personController)
//				.build()
//				.perform(
//						MockMvcRequestBuilders.get("/person/13").accept(
//								MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound());
//	}
//
//	@Test
//	public void add() throws Exception {
//		item.setId(null);
//
//		String jsonPerson = jsonMapper.writeValueAsString(item);
//		MockMvcBuilders
//				.standaloneSetup(personController)
//				.build()
//				.perform(
//						MockMvcRequestBuilders.post("/person/person")
//								.contentType(MediaType.APPLICATION_JSON)
//								.accept(MediaType.APPLICATION_JSON)
//								.body(jsonPerson.getBytes()))
//				.andExpect(status().isOk())
//				.andExpect(content().mimeType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.personName").value("person3"));
//	}
//}
