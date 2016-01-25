package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;

import java.util.Optional;

/**
 * @author Alexandr Laktionov
 */
public interface VerificationTokenService {

    VerificationToken getByEmail(String email);

    Optional<VerificationToken> getByToken(String token);

    VerificationToken save(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void sendEmailRegistrationToken(VerificationToken verificationToken);

    boolean isPresent(VerificationToken verificationToken);

    Optional<VerificationToken> getToken(VerificationToken token);

    VerificationToken generateToken(String email);

    VerificationToken createNewToken(String email);

}
