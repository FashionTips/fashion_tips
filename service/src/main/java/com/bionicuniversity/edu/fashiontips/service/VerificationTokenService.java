package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;

import java.util.Optional;

/**
 * @author Alexandr Laktionov
 */
public interface VerificationTokenService {

    VerificationToken getByEmail(String email, VerificationTokenPK.Type type);

    Optional<VerificationToken> getByToken(String token);

    VerificationToken save(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void sendEmailRegistrationToken(VerificationToken verificationToken);

    boolean isPresent(VerificationToken verificationToken);

    Optional<VerificationToken> getToken(VerificationToken token);

    void generateToken(VerificationToken verificationToken);

    VerificationToken registrateNewToken(VerificationToken verificationToken);

    VerificationToken resentToken(VerificationToken verificationToken);
}
