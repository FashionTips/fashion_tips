package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;

import java.time.LocalDateTime;

/**
 * Created by slav9nin on 25.01.2016.
 */
public class VerificationTokenTestData {
    public static final ModelMatcher<VerificationToken, String> COMMENT_MATCHER =
            new ModelMatcher<>(VerificationToken::toString);

    public static final VerificationToken ArusichVerificationTokenAndNotVerified =
            new VerificationToken("arusich2008@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken ArusichVerificationTokenAndNotVerifiedAndNotExpiredTime =
            new VerificationToken("arusich2008@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken ArusichVerificationTokenAndVerified =
            new VerificationToken("arusich2008@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken ArusichVerificationTokenAndVerifiedAndNotExpiredTime =
            new VerificationToken("arusich2008@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken ArusichVerificationTokenWithBadToken =
            new VerificationToken("arusich2008@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000000000");
    public static final VerificationToken NonArusichVerificationTokenWithCompletedTokenAndNotVerified =
            new VerificationToken("arusich777@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken NonArusichVerificationTokenWithCompletedTokenAndVerifies =
            new VerificationToken("arusich777@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
    public static final VerificationToken NonArusichVerificationTokenWithBadToken =
            new VerificationToken("arusich777@ukr.net",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000000000");

    public static final VerificationToken Login1VerificationToken =
            new VerificationToken("email1@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000002222");
    public static final VerificationToken Login1VerificationTokenWithExpiredTime =
            new VerificationToken("email1@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000002222");
    public static final VerificationToken Login1VerificationTokenWithVerifiedFlag =
            new VerificationToken("email1@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000002222");
    public static final VerificationToken Login1VerificationTokenWithVerifiedFlagAndExpiredTime =
            new VerificationToken("email1@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000002222");

    public static final VerificationToken NotPresentedInUserToken =
            new VerificationToken("email4@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000004444");
    public static final VerificationToken NotPresentedInUserTokenAndVerified =
            new VerificationToken("email4@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000004444");
    public static final VerificationToken NotPresentedInUserTokenWithBadToken =
            new VerificationToken("email4@example.com",
                    "b36e992c2cc62c9f5f589e006862b2e5d7fa485b111111111111000000004411");

    public static final VerificationToken NullableVerificationToken =
            new VerificationToken();

    public static final VerificationToken NewVerificationToken =
            new VerificationToken("slav9nin2009@gmail.com");

    public static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();

    static {
        ArusichVerificationTokenAndNotVerified.setExpairedTime(null);
        ArusichVerificationTokenAndVerified.setVerified(true);
        ArusichVerificationTokenAndVerified.setExpairedTime(null);
        NonArusichVerificationTokenWithCompletedTokenAndVerifies.setVerified(true);
        ArusichVerificationTokenAndNotVerifiedAndNotExpiredTime.setExpairedTime(LocalDateTime.now().plusYears(3000L));
        ArusichVerificationTokenAndVerifiedAndNotExpiredTime.setExpairedTime(LocalDateTime.now().plusYears(3000L));

        NotPresentedInUserToken.setExpairedTime(LOCAL_DATE_TIME_NOW);

        Login1VerificationTokenWithVerifiedFlag.setVerified(true);
        Login1VerificationTokenWithExpiredTime.setExpairedTime(LocalDateTime.now());
        Login1VerificationTokenWithVerifiedFlagAndExpiredTime.setExpairedTime(LocalDateTime.now());

        NotPresentedInUserTokenAndVerified.setVerified(true);

        NullableVerificationToken.setExpairedTime(null);
    }
}
