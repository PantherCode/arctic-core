package org.panthercode.arctic.core.security;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * Created by architect on 04.05.17.
 */
public class Credentials {
    private String username;

    private Password password;

    public Credentials(String username, Password password) {
        this.username = ArgumentUtils.checkNotNull(username, "username");

        this.password = ArgumentUtils.checkNotNull(password, "password");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = ArgumentUtils.checkNotNull(username, "username");
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = ArgumentUtils.checkNotNull(password, "password");
    }
}
