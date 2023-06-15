package com.telegame.code.models.games.laserboard;

import com.telegame.code.services.games.LaserBoardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@Service
public class LaserBeamService {

    private LaserBoardService laserBoardService;

}
