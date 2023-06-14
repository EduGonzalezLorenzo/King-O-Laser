package com.telegame.code.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlayerForm {
    @Size(max = 20)
    String password;

    @Size(max = 20)
    String firstName;

    @Size(max = 20)
    String lastName;

    @Size(max = 10)
    String playerName;
}
