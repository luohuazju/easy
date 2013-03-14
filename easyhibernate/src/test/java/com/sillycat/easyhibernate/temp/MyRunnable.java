package com.sillycat.easyhibernate.temp;

import java.util.Date;

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
		student.setFirstName("xxx" + (new Date()).getTime());
		session.save(student);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		System.out.println("Done " + student.getFirstName());
	}

	public MyRunnable(String id) {
		this.id = id;
	}

}
