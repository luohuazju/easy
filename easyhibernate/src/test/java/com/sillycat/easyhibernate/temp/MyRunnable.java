package com.sillycat.easyhibernate.temp;

import org.hibernate.Session;

import com.sillycat.easyhibernate.model.Student;
import com.sillycat.easyhibernate.util.HibernateUtil;

public class MyRunnable implements Runnable {
	private String id;

	@Override
	public void run() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Student student = (Student) session.load(Student.class, Integer.valueOf(id));
		student.setFirstName("xxxx");
		session.save(student);
		session.getTransaction().commit();
		System.out.println("Done");
	}

	public MyRunnable(String id) {
		this.id = id;
	}

}
