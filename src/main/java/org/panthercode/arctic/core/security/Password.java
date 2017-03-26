package org.panthercode.arctic.core.security;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by architect on 22.03.17.
 */
public final class Password {

    /**
     *
     */
    private final static String ALGORITHM = "PBKDF2WithHmacSHA512";

    /**
     *
     */
    private final static int ITERATIONS = 32;

    /**
     *
     */
    private final static int KEY_LENGTH = 256;

    /**
     *
     */
    private byte[] salt = new byte[0];

    /**
     *
     */
    private char[] password = new char[0];

    /**
     *
     */
    public Password() {
        this(null);
    }

    /**
     *
     * @param password
     */
    public Password(char[] password) {
        this(password, null);
    }

    /**
     *
     * @param password
     * @param salt
     */
    public Password(char[] password, byte[] salt) {
        this.setPassword(password);

        this.setSalt(salt);
    }

    /**
     *
     * @param password
     */
    public void setPassword(char[] password) {
        this.password = password == null ? new char[0] : password;
    }

    /**
     *
     * @return
     */
    public char[] getPassword() {
        return this.password;
    }

    /**
     *
     * @param salt
     */
    public void setSalt(byte[] salt) {
        this.salt = salt == null ? new byte[0] : salt;
    }

    /**
     *
     * @return
     */
    public byte[] getSalt() {
        return this.salt;
    }

    /**
     *
     */
    public void clear() {
        SecurityUtils.clearArray(this.salt);
        SecurityUtils.clearArray(this.password);
    }

    /**
     *
     * @return
     */
    public PasswordHash hash() {
        return this.hash(ALGORITHM, ITERATIONS, KEY_LENGTH);
    }

    /**
     *
     * @param algorithm
     * @param iterations
     * @param keyLength
     * @return
     */
    public PasswordHash hash(String algorithm, int iterations, int keyLength) {
        ArgumentUtils.checkNotNull(algorithm, "algorithm");
        ArgumentUtils.checkGreaterZero(iterations, "iterations");
        ArgumentUtils.checkGreaterZero(keyLength, "key length");

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            PBEKeySpec spec = new PBEKeySpec(this.password, this.salt, iterations, keyLength);
            SecretKey key = secretKeyFactory.generateSecret(spec);

            return new PasswordHash(key.getEncoded(), this.salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Password)) {
            return false;
        }

        Password other = (Password) obj;

        return SecurityUtils.equals(this.password, other.getPassword()) &&
                SecurityUtils.equals(this.salt, other.getSalt());
    }

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        char[] password = "Password".toCharArray();

        byte[] salt = "SALTSALT".getBytes();

        Password p = new Password(password, salt);

        System.out.println(new String(p.hash().getHash()));
        System.out.println(new String(p.hash().getSalt()));
    }
}
