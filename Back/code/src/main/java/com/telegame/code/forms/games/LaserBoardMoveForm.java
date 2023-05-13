package com.telegame.code.forms.games;

import com.telegame.code.forms.ActionForm;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class LaserBoardMoveForm implements ActionForm {
    @Range(min = 0, max = 7)
    int currentPosY;
    @Range(min = 0, max = 9)
    int currentPosX;
    @Range(min = 0, max = 7)
    int newPosY;
    @Range(min = 0, max = 9)
    int newPosX;
    @Pattern(regexp = "^([NSWE])$")
    char rotateTo;
}
