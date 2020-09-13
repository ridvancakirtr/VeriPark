package com.example.veripark.CryptoHandler;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class CryptoHandler {
    private static CryptoHandler instance = null;

    public static CryptoHandler getInstance() {

        if (instance == null) {
            instance = new CryptoHandler();
        }
        return instance;
    }

    public String encrypt(String message,String SecretKey,String IV) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {

        byte[] srcBuff = message.getBytes("UTF8");
        //here using substring because AES takes only 16 or 24 or 32 byte of key
        SecretKeySpec skeySpec = new
                SecretKeySpec(Base64.decode(SecretKey, Base64.DEFAULT), "AES");
        IvParameterSpec ivSpec = new
                IvParameterSpec(Base64.decode(IV, Base64.DEFAULT));
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] dstBuff = ecipher.doFinal(srcBuff);
        String base64 = Base64.encodeToString(dstBuff, Base64.DEFAULT);
        return base64;
    }

    public String decrypt(String encrypted,String SecretKey,String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException {

        SecretKeySpec skeySpec = new
                SecretKeySpec(Base64.decode(SecretKey, Base64.DEFAULT), "AES");
        IvParameterSpec ivSpec = new
                IvParameterSpec(Base64.decode(IV, Base64.DEFAULT));
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] raw = Base64.decode(encrypted, Base64.DEFAULT);
        byte[] originalBytes = ecipher.doFinal(raw);
        String original = new String(originalBytes, "UTF8");
        return original;
    }
}