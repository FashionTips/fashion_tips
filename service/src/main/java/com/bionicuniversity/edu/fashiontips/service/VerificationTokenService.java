package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;

/**
 * @author Alexandr Laktionov
 */
public interface VerificationTokenService {

    VerificationToken getByEmail(String email);

    VerificationToken getByToken(String token);

    VerificationToken save(VerificationToken verificationToken);

}
