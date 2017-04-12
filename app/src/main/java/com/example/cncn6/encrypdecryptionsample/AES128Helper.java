package com.example.cncn6.encrypdecryptionsample;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by cncn6 on 2017-04-11.
 */

public class AES128Helper {
    private static AES128Helper instance;

    /**
     * 사용될 키 값
     */
    private static final String KEY_VALUE = "openit79787";

    public static AES128Helper getInstance() {
        if (instance == null)
            instance = new AES128Helper();

        return instance;
    }

    /**
     * 지정한 키값으로 보안키를 생성
     */
    private byte[] getKey() throws NoSuchProviderException {
        byte[] seed = KEY_VALUE.getBytes();
        // 1. 128 비트 비밀키 생성
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES"); // AES알고리즘
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        SecureRandom sr; // 암호학적 안전성이 있다고 하는 SecureRandom클래스 사용

        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            // http://stackoverflow.com/questions/23491143/aes128-decryption-javax-crypto-badpaddingexception-pad-block-corrupted

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        sr.setSeed(seed); // 키값을 시드값(난수값)으로 랜덤값을 생성
        kg.init(128, sr); // AES알고리즘은 키로 128비트값을 사용하므로 128비트로 초기화

        SecretKey sk = kg.generateKey(); // 생성
        byte[] key = sk.getEncoded(); // 인코드된 값을 받아 리턴

        return key;
    }

    /**
     * 암호화
     */
    public String encrypt(String clearText) {
        byte[] encodeText = null;
        try {
            SecretKeySpec ks = new SecretKeySpec(getKey(), "AES"); // 키 값의 명세 객체 생성
            Cipher c = Cipher.getInstance("AES"); // AES 암호화 객체 생성하기 위해 Cipher클래스에 필요한 암호화 알고리즘 요청
            c.init(Cipher.ENCRYPT_MODE, ks); // 키 명세 객체를 넘겨주며 암호화 명시
            encodeText = c.doFinal(clearText.getBytes("UTF-8")); // 바이트 배열을 지정한 키 시드 문자열과 AES 알고리즘으로 암호화
            return Base64.encodeToString(encodeText, Base64.DEFAULT); // Base64 인코딩

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 복호화
     */
    public String decrypt(String encryptedText) {
        byte[] decodeText = null;
        try {
            SecretKeySpec ks = new SecretKeySpec(getKey(), "AES"); // 키 값의 명세 객체 생성
            Cipher c = Cipher.getInstance("AES"); // AES 복호화 객체 생성하기 위해 Cipher클래스에 필요한 복호화 알고리즘 요청
            c.init(Cipher.DECRYPT_MODE, ks); // 키 명세 객체를 넘겨주며 복호화 명시
            decodeText = c.doFinal(Base64.decode(encryptedText, Base64.DEFAULT)); // Base 디코딩한 바이트 배열을 지정한 키 시드 문자열과 AES 알고리즘으로 복호화
            return new String(decodeText, "UTF-8");

        } catch (Exception e) {
            return null;
        }
    }
}
