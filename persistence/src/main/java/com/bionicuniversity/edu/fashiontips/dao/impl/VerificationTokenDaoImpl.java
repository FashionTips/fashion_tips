package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author Alexandr Laktionov
 */
@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public VerificationToken getByEmail(String email, VerificationTokenPK.Type type) {
        TypedQuery<VerificationToken> query =
                em.createNamedQuery("VerificationToken.getByEmail", VerificationToken.class);
        query.setParameter("email", email).setParameter("type", type);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public VerificationToken getByToken(String token) {
        TypedQuery<VerificationToken> query =
                em.createNamedQuery("VerificationToken.getByToken", VerificationToken.class);
        query.setParameter("token", token);
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken save(VerificationToken verificationToken) {
        if (verificationToken == null && verificationToken.getEmail() == null) {
            throw new IllegalArgumentException("Email could not be empty");
        }
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<VerificationToken> getToken(VerificationToken verificationToken) {
        VerificationToken token = em.find(VerificationToken.class, verificationToken.getId());
        return Optional.ofNullable(token);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken update(VerificationToken verificationToken) {
        return em.merge(verificationToken);
    }
}