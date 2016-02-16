package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.VerificationTokenDao;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.bionicuniversity.edu.fashiontips.entity.VerificationTokenPK;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
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

    @Inject
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public VerificationToken getByEmail(String email, VerificationTokenPK.Type type) {
        VerificationToken verificationToken = verificationTokenDao.getByEmail(email, type);
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
        emailService.sentVerificationToken(verificationToken.getEmail(), verificationToken.getType(), verificationToken.getToken());
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
        verificationToken.setExpiredTime(LocalDateTime.now().plusSeconds(VerificationToken.EXPIRED_PERIOD));
        String unhashedToken = verificationToken.getEmail() + verificationToken.getType() + LocalDateTime.now();
        String token = VerificationTokenUtil.getHash(unhashedToken, VerificationTokenUtil.SHA_256);
        verificationToken.setToken(token);
    }

    @Override
    @Transactional(timeout = 60)
    public VerificationToken registrateNewToken(VerificationToken verificationToken) {
        if (verificationToken.getType() == VerificationTokenPK.Type.PASSWORD_RESET &&
                !userService.findByEmail(verificationToken.getEmail()).isPresent()) {
            throw new NotFoundException(String.format("The user with email %s was not found.", verificationToken.getEmail()));
        }

        generateToken(verificationToken);
        verificationTokenDao.save(verificationToken);
        emailService.sentVerificationToken(verificationToken.getEmail(), verificationToken.getType(), verificationToken.getToken());
        return verificationToken;
    }

    @Override
    @Transactional(timeout = 60)
    public VerificationToken resentToken(VerificationToken verificationToken) {
        generateToken(verificationToken);
        if (verificationToken.getType() == VerificationTokenPK.Type.PASSWORD_RESET && verificationToken.isVerified()) {
            verificationToken.setVerified(false);
        }
        VerificationToken updated = verificationTokenDao.update(verificationToken);
        emailService.sentVerificationToken(updated.getEmail(), verificationToken.getType(), updated.getToken());
        return verificationToken;
    }
}
