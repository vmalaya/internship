package com.sigma.software.dao;

import com.sigma.software.model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class MessageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Message message) throws NamingException {
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        try {
            transaction.begin();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        entityManager.persist(message);
        try {
            transaction.commit();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllMessages() {
        TypedQuery<String> query = entityManager.createQuery("SELECT m.message from Message as m", String.class);
        List<String> resultList = query.getResultList();
        return Collections.unmodifiableList(resultList);
    }
}

