package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
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
    public VerificationToken getByEmail(String email) {
        TypedQuery<VerificationToken> query =
                em.createQuery("SELECT t FROM VerificationToken t WHERE t.email =:email", VerificationToken.class);
        query.setParameter("email", email);
        try {
            VerificationToken verificationToken = query.getSingleResult();
            return verificationToken;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public VerificationToken getByToken(String token) {
        TypedQuery<VerificationToken> query =
                em.createQuery("SELECT t FROM VerificationToken t WHERE t.token = :token", VerificationToken.class);
        query.setParameter("token", token);
        try {
            VerificationToken verificationToken = query.getSingleResult();
            return verificationToken;
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken save(VerificationToken verificationToken) {
//        Objects.requireNonNull(verificationToken);
        if (verificationToken == null && verificationToken.getEmail() == null) {
            throw new IllegalArgumentException("Email could not be empty");
        }
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<VerificationToken> getToken(VerificationToken verificationToken) {
        VerificationToken token = em.find(VerificationToken.class, verificationToken.getEmail());
        return Optional.ofNullable(token);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken update(VerificationToken verificationToken) {
        return em.merge(verificationToken);
    }
}
