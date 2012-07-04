package com.sillycat.easyopenidgoogle.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sillycat.easyopenidgoogle.model.Company;
import com.sillycat.easyopenidgoogle.model.Person;

@Service("personManager")
public class PersonManagerImpl implements PersonManager {

	private final Log log = LogFactory.getLog(this.getClass());

	public Person get(Integer id) {
		Person p1 = new Person();
		p1.setId(id);
		p1.setPersonName("UserName" + id);
		Company c1 = new Company();
		c1.setCompanyName("CompanyName" + id);
		c1.setId(id);
		p1.setCompany(c1);
		return p1;
	}

	public void delete(Integer id) {
		log.info("service method delete invoke ===============");
	}

	public void save(Person person) {
		log.info("service method save invoke ===============");
		if(person.getId() == null){
			person.setId(13);
		}
	}

	public List<Person> getAll() {
		Integer id = 1;
		Person p1 = new Person();
		p1.setId(id);
		p1.setPersonName("UserName" + id);
		Company c1 = new Company();
		c1.setCompanyName("CompanyName" + id);
		c1.setId(id);
		p1.setCompany(c1);

		id = 2;
		Person p2 = new Person();
		p2.setId(id);
		p2.setPersonName("UserName" + id);
		Company c2 = new Company();
		c2.setCompanyName("CompanyName" + id);
		c2.setId(id);
		p2.setCompany(c2);

		List<Person> list = new ArrayList<Person>();
		list.add(p1);
		list.add(p2);
		return list;
	}

}
