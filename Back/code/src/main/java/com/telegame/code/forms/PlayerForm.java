package com.telegame.code.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerForm {
    @Email
    @Size(min = 6, max = 30)
    String email;

    @Size(min = 8, max = 20)
    String password;

    @Size(min = 3, max = 20)
    String firstName;

    @Size(min = 3, max = 20)
    String lastName;

    @Size(min = 3, max = 10)
    String playerName;
}
