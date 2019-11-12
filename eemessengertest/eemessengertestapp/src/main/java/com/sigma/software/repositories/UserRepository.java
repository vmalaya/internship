package com.sigma.software.repositories;

import com.sigma.software.entities.Role;
import com.sigma.software.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class UserRepository {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.sigma.software");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void save(User user) throws NamingException {
        UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        try {
            transaction.begin();
        } catch (NotSupportedException | SystemException e) {
            e.printStackTrace();
        }
        entityManager.persist(user);
        entityManager.merge(new Role("user", user));
        try {
            transaction.commit();
        } catch (RollbackException | HeuristicRollbackException | SystemException | HeuristicMixedException e) {
            e.printStackTrace();
        }
    }

    public List<Long> findAllUsernames() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT u.id from User as u", Long.class);
        List<Long> resultList = query.getResultList();
        return Collections.unmodifiableList(resultList);
    }

    public User findUser(Long userId) {
        return entityManager.getReference(User.class, userId);
    }
}
