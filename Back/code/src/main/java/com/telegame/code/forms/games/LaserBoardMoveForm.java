package com.telegame.code.forms.games;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
public class LaserBoardMoveForm {
    @Range(min = 0, max = 7)
    int currentPosY;
    @Range(min = 0, max = 9)
    int currentPosX;
    @Range(min = 0, max = 7)
    int newPosY;
    @Range(min = 0, max = 9)
    int newPosX;
    @Pattern(regexp = "^([NSWE])$")
    String rotateTo;
}
