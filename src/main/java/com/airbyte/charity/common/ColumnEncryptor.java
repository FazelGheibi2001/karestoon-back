package com.airbyte.charity.common;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.util.Base64;

@Component
public class ColumnEncryptor implements AttributeConverter<String, String> {

    public static final String SPLIT_PLAIN_TEXT = "@#%&";
    public static final String SPLIT_SECRET_KEY = "&%#@";
    public static final String DEFAULT_ALGORITHM = "AES";
    public static final Integer DEFAULT_KEY_SIZE = 256;

    @Override
    public String convertToDatabaseColumn(String plainText) {
        SecretKey key = this.generateKey(DEFAULT_KEY_SIZE);
        String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        String[] dividedPlainText = divideStrings(plainText);

        String cipherText = concatStrings(
                encrypt(DEFAULT_ALGORITHM, dividedPlainText[0], key),
                encrypt(DEFAULT_ALGORITHM, dividedPlainText[1], key),
                SPLIT_PLAIN_TEXT);

        return concatStrings(cipherText, secretKey, SPLIT_SECRET_KEY);
    }

    private String[] divideStrings(String text) {
        int size = text.length();

        if (size >= 2) {
            String[] array = new String[2];
            array[0] = text.substring(0, size / 2);
            array[1] = text.substring(size / 2, size);
            return array;
        }
        throw new IllegalArgumentException("can't divide string");
    }

    private String concatStrings(String text1, String text2, String sign) {
        return text1 + sign + text2;
    }

    @Override
    public String convertToEntityAttribute(String column) {
        String[] ciphertextWithSecretKey = column.split(SPLIT_SECRET_KEY);
        SecretKey key = convertStringToSecretKey(ciphertextWithSecretKey[1]);

        String[] cipherTexts = ciphertextWithSecretKey[0].split(SPLIT_PLAIN_TEXT);
        String section1 = decrypt(DEFAULT_ALGORITHM, cipherTexts[0], key);
        String section2 = decrypt(DEFAULT_ALGORITHM, cipherTexts[1], key);
        return section1 + section2;
    }

    private SecretKey convertStringToSecretKey(String secretKey) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private SecretKey generateKey(int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("generateKey method at ColumnEncryptor Class");
        }
    }

    private String encrypt(String algorithm, String input, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("encrypt method at ColumnEncryptor Class");
        }
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText);
        } catch (Exception e) {
            throw new RuntimeException("encrypt method at ColumnEncryptor Class");
        }
    }

}
