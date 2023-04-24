package com.telegame.code.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @Email
    @Size(min = 6, max = 30)
    String email;

    @Size(min = 8, max = 15)
    @NotNull
    String password;

    @Size(min = 3, max = 10)
    String playerName;
}
