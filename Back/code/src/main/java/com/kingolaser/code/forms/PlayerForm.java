package com.kingolaser.code.forms;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Range;

public class PlayerForm {
    @Email
    String email;

    @Range(min = 8, max = 20)
    String password;

    @Range(min = 3, max = 10)
    String name;

    @Range(min = 3, max = 10)
    String playerName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
