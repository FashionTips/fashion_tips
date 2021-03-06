package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;

import java.util.Optional;

/**
 * @author Alexandr Laktionov
 */
public interface VerificationTokenDao {

    VerificationToken getByEmail(String email, VerificationTokenPK.Type type);

    VerificationToken getByToken(String token);

    VerificationToken save(VerificationToken verificationToken);

    Optional<VerificationToken> getToken(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);
}
