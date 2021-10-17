package com.hamrasta.trellis.util.crypto;

import com.hamrasta.trellis.util.string.StringUtil;
import com.hamrasta.trellis.core.log.Logger;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class CryptoUtil {

    public static String sha256(String message) {
        return sha256(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha256(byte[] message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message);
            return StringUtil.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            Logger.error("sha256", e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    public static String md5(String message) {
        return md5(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5(String message, boolean toLowerCase) {
        return md5(message.getBytes(StandardCharsets.UTF_8), toLowerCase);
    }

    public static String md5(byte[] message) {
        return md5(message, true);
    }

    public static String md5(byte[] message, boolean toLowerCase) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(message);
            byte[] digest = m.digest();
            return StringUtil.encodeHexString(digest, toLowerCase);
        } catch (NoSuchAlgorithmException e) {
            Logger.error("md5", e.getMessage());
            return "";
        }
    }

    public static String aesEncrypt(String key, String value) {
        String initVector = CryptoUtil.md5(key).substring(0, 16);
        byte[] encrypted = aesEncrypt(key, initVector.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8), "AES/CBC/PKCS5PADDING");
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static byte[] aesEncrypt(String key, byte[] initVector, byte[] value, String cipherValue) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(cipherValue);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            return cipher.doFinal(value);
        } catch (Exception ex) {
            Logger.error("aes-encryption", ex.getMessage());
            return null;
        }
    }

    public static String aesDecrypt(String key, String value) {
        return aesDecrypt(key, value, true);
    }

    public static String aesDecrypt(String key, String value, boolean toLowercase) {
        String initVector = CryptoUtil.md5(key, toLowercase).substring(0, 16);
        byte[] decrypted = aesDecrypt(key, initVector.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8), "AES/CBC/PKCS5PADDING");
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public static byte[] aesDecrypt(String key, byte[] initVector, byte[] value, String cipherValue) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(cipherValue);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            return cipher.doFinal(Base64.getDecoder().decode(value));
        } catch (Exception ex) {
            Logger.error("aes-encryption", ex.getMessage());
            return null;
        }
    }
}
