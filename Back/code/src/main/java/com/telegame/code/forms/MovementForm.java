package com.telegame.code.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementForm {
    int currentPosY;
    int currentPosX;
    int newPosY;
    int newPosX;
    String rotateTo;
}
