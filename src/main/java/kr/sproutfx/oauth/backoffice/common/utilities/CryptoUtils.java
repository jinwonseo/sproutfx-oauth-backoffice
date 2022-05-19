package kr.sproutfx.oauth.backoffice.common.utilities;

import kr.sproutfx.oauth.backoffice.common.exception.DecryptFailedException;
import kr.sproutfx.oauth.backoffice.common.exception.EncryptFailedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyStore;
import java.security.cert.Certificate;

@Component
public class CryptoUtils {
    private static String location;
    private static String alias;
    private static String secret;
    private static String password;

    private static final String CIPHER_TRANSFORMATION = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    @Value("${sproutfx.crypto.key-store.location}")
    public void setLocation(String value) {
        location = value;
    }

    @Value("${sproutfx.crypto.key-store.alias}")
    public void setAlias(String value) {
        alias = value;
    }

    @Value("${sproutfx.crypto.key-store.secret}")
    public void setSecret(String value) {
        secret = value;
    }

    @Value("${sproutfx.crypto.key-store.password}")
    public void setPassword(String value) {
        password = value;
    }

    public static String encrypt(String plainText) {
        try {
            String keyStoreLocation = location.replace("classpath:/", StringUtils.EMPTY);
            ClassPathResource classPathResource = new ClassPathResource(keyStoreLocation);

            char[] keyPassword = password.toCharArray();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(classPathResource.getInputStream(), keyPassword);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            Certificate cert = keyStore.getCertificate(alias);

            cipher.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());

            return Base64Utils.encodeToUrlSafeString(cipher.doFinal(plainText.getBytes()));
        } catch (Exception e) {
            throw new EncryptFailedException();
        }
    }

    public static String decrypt(String base64EncodedEncryptedString) {
        try {
            String keyStoreLocation = location.replace("classpath:/", StringUtils.EMPTY);
            char[] keyPassword = password.toCharArray();

            ClassPathResource classPathResource = new ClassPathResource(keyStoreLocation);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(classPathResource.getInputStream(), keyPassword);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

            cipher.init(Cipher.DECRYPT_MODE, keyStore.getKey(alias, keyPassword));

            return new String(cipher.doFinal(Base64Utils.decodeFromUrlSafeString(base64EncodedEncryptedString)));
        } catch (Exception e) {
            throw new DecryptFailedException();
        }
    }
}
