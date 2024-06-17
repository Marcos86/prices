package com.mdelamo.prices.util;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DbUtils {

    @Autowired
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cleanDb() {
        entityManager.flush();
        entityManager.clear();
        entityManager.createNativeQuery(" delete from price ").executeUpdate();
    }
}
