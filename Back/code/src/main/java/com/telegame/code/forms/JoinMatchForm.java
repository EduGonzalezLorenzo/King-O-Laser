package com.telegame.code.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class JoinMatchForm {
    @Length(min = 1, max = 20)
    String password;
}
