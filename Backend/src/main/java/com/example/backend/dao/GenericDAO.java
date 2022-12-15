package com.example.backend.dao;

import javax.persistence.PersistenceException;
import org.hibernate.Session;
import com.example.backend.service.SessionService;
import org.hibernate.Transaction;

public abstract class GenericDAO<T> {

    public void save(final T object) throws PersistenceException {
        final Session session = currentSession();
        final Transaction transaction = session.beginTransaction();
        session.save(object);
        session.merge(object);
        transaction.commit();
    }

    public void update(final T object) throws PersistenceException {
        final Session session = currentSession();
        final Transaction transaction = session.beginTransaction();
        session.update(object);
        session.merge(object);
        transaction.commit();
    }

    public Session currentSession() {
        return SessionService.getSession();
    }
}