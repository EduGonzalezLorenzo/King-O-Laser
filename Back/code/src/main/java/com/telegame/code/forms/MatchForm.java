package com.telegame.code.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class MatchForm {
    @Range(min = 1, max = 20)
    String matchName;
    @Range(min = 1, max = 20)
    String password;
    Boolean isPublic;
    String boardDisposition;
    String game;


}
