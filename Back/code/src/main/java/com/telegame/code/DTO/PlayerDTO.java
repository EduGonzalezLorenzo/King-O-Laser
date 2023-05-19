package com.telegame.code.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PlayerDTO {
    String playerName;
    String profileImgUrl;
    boolean loggedIn;
}
