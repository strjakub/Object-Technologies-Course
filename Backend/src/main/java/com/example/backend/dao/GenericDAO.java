package com.example.backend.dao;

import javax.persistence.PersistenceException;
import org.hibernate.Session;
import com.example.backend.service.SessionService;
import org.hibernate.Transaction;

public abstract class GenericDAO<T> {

    public void save(final T object) throws PersistenceException {
        SessionService.openSession();
        final Session session = currentSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
    }

    public Session currentSession() {
        return SessionService.getSession();
    }
}