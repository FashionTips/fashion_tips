package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Alexandr Laktionov
 */
@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public VerificationToken getByEmail(String email) {
        return null;
    }

    @Override
    public VerificationToken getByToken(String token) {
        return null;
    }

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        return null;
    }
}
