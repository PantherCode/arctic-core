package org.panthercode.arctic.core.security;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * Created by architect on 22.03.17.
 */
public final class PasswordHash {

    private byte[] salt = new byte[0];

    private byte[] hash = new byte[0];

    public PasswordHash() {
        this(null);
    }

    public PasswordHash(byte[] hash) {
        this(hash, null);
    }

    public PasswordHash(byte[] hash, byte[] salt) {
        this.setHash(hash);

        this.setSalt(salt);
    }

    public PasswordHash(byte[] data, int offset) {
        if (data == null) {
            throw new NullPointerException("The value of data is null.");
        }

        ArgumentUtils.checkRange(offset, 0, data.length, "offset");

        this.salt = new byte[offset];

        this.hash = new byte[data.length - offset];

        System.arraycopy(data, 0, this.salt, 0, offset);

        System.arraycopy(data, offset, this.hash, 0, data.length - offset);
    }

    public void setSalt(byte[] salt) {
        this.salt = (salt == null) ? new byte[0] : salt;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public void setHash(byte[] hash) {
        this.hash = (hash == null) ? new byte[0] : hash;
    }

    public byte[] getHash() {
        return this.hash;
    }

    public byte[] toArray() {
        byte[] buffer = new byte[this.salt.length + this.hash.length];

        System.arraycopy(this.salt, 0, buffer, 0, this.salt.length);
        System.arraycopy(this.hash, 0, buffer, this.salt.length, this.hash.length);

        return buffer;
    }

    public void clear() {
        SecurityUtils.clearArray(this.salt);
        SecurityUtils.clearArray(this.hash);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PasswordHash)) {
            return false;
        }

        PasswordHash other = (PasswordHash) obj;

        return SecurityUtils.equals(this.salt, other.getSalt()) &&
                SecurityUtils.equals(this.hash, other.getHash());
    }
}