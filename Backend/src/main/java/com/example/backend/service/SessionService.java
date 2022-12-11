package com.example.backend.service;

import com.example.backend.utils.HibernateUtil;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionService {

	private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static Session session;

	@SneakyThrows
	public static void openSession(){
		session = sessionFactory.openSession();
	}
	
	public static Session getSession() {
		return session;
	}

	@SneakyThrows
	public static void closeSession() {
		session.close();
	}

}
