package com.telegame.code.forms;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MatchForm {
    @Length(min = 1, max = 20)
    @NotNull
    String matchName;

    @NotNull
    Boolean isPublic;

    @Length(min = 1, max = 20)
    String password;

    @NotNull
    String game;

    String metadata;
}
