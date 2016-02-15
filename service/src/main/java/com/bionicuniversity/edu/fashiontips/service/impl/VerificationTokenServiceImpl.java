package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import com.bionicuniversity.edu.fashiontips.service.VerificationTokenService;
import com.bionicuniversity.edu.fashiontips.service.util.VerificationTokenUtil;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Alexandr Laktionov
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Inject
    private VerificationTokenDao verificationTokenDao;

    @Inject
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public VerificationToken getByEmail(String email) {
        VerificationToken verificationToken = verificationTokenDao.getByEmail(email);
        if (verificationToken == null) throw new NotFoundException();
        return verificationToken;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VerificationToken> getByToken(String token) {
        return Optional.ofNullable(verificationTokenDao.getByToken(token));
    }

    @Override
    @Transactional
    public VerificationToken save(VerificationToken verificationToken) {
        return verificationTokenDao.save(verificationToken);
    }

    @Override
    @Transactional
    public VerificationToken update(VerificationToken verificationToken) {
        return verificationTokenDao.update(verificationToken);
    }

    @Override
    public void sendEmailRegistrationToken(VerificationToken verificationToken) {
        emailService.sentVerificationToken(verificationToken.getEmail(), verificationToken.getToken());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPresent(VerificationToken verificationToken) {
        Optional<VerificationToken> token = verificationTokenDao.getToken(verificationToken);
        return token.isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VerificationToken> getToken(VerificationToken token) {
        return verificationTokenDao.getToken(token);
    }

    @Override
    public void generateToken(VerificationToken verificationToken) {
        verificationToken.setExpairedTime(LocalDateTime.now().plusSeconds(VerificationToken.EXPAIRED_PERIOD));
        String unhashedToken = verificationToken.getEmail() + LocalDateTime.now();
        String token = VerificationTokenUtil.getHash(unhashedToken, VerificationTokenUtil.SHA_256);
        verificationToken.setToken(token);
    }

    @Override
    @Transactional(timeout = 60)
    public VerificationToken registrateNewToken(VerificationToken verificationToken) {
        generateToken(verificationToken);
        verificationTokenDao.save(verificationToken);
        emailService.sentVerificationToken(verificationToken.getEmail(), verificationToken.getToken());
        return verificationToken;
    }

    @Override
    @Transactional(timeout = 60)
    public VerificationToken resentToken(VerificationToken verificationToken) {
        generateToken(verificationToken);
        VerificationToken updated = verificationTokenDao.update(verificationToken);
        emailService.sentVerificationToken(updated.getEmail(), updated.getToken());
        return verificationToken;
    }
}
