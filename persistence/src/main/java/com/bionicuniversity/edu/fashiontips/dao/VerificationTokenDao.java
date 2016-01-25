package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;

/**
 * @author Alexandr Laktionov
 */
public interface VerificationTokenDao {

    VerificationToken getByEmail(String email);

    VerificationToken getByToken(String token);

    VerificationToken save(VerificationToken verificationToken);

}
