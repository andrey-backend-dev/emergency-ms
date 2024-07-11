package org.example.emergency.util.builders;

import org.example.emergency.entity.Caller;
import org.example.emergency.entity.Role;

import java.util.Set;

public class CallerBuilder {
    private final String username;
    private final String password;
    private final String email;
    private String message;
    private String image;
    private Set<Role> roles;

    public CallerBuilder(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public CallerBuilder message(String message) {
        this.message = message;
        return this;
    }

    public CallerBuilder image(String image) {
        this.image = image;
        return this;
    }

    public CallerBuilder roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Caller build() {
        Caller caller = new Caller();
        caller.setUsername(username);
        caller.setPassword(password);
        caller.setEmail(email);
        caller.setMessage(message);
        caller.setImage(image);
        caller.setRoles(roles);
        return caller;
    }
}
