package com.allets.backend.dashboard.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


public class EncryptUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(EncryptUtil.class);

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean validateEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static String encryptMd5String(String str) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            LOG.warn("encryptMd5String method", e);
            md5 = null;
        }
        return md5;
    }

    public static String encryptSha512String(String str) {
        MessageDigest md;
        String sha512 = "";

        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(str.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                sha512 += s;
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.warn("encryptSha512String method", e);
            return null;
        }
        return sha512;
    }

}
