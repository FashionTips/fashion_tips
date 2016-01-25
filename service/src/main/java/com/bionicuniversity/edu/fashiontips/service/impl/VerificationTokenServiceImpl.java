package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.service.VerificationTokenService;
import org.springframework.stereotype.Service;

/**
 * @author Alexandr Laktionov
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
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
