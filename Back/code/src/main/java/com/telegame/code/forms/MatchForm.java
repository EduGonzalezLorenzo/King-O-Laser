package com.telegame.code.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
public class MatchForm {
    @Range(min = 1, max = 20)
    String name;
    @Range(min = 1, max = 20)
    String password;
    Boolean isPublic;
    String boardDisposition;

}
