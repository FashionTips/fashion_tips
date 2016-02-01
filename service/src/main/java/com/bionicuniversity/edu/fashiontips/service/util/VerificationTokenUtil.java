package com.bionicuniversity.edu.fashiontips.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by slav9nin on 25.01.2016.
 */
public class VerificationTokenUtil {

    public static final String MD5 = "MD5";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_512 = "SHA-512";

    public static String getHash(String message, String algorithm) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            try {
                messageDigest = MessageDigest.getInstance(SHA_256);
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
        }
        messageDigest.update(message.getBytes());
        byte[] mdbytes = messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
