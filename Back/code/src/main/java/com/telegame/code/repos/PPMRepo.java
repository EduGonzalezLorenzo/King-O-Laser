package com.telegame.code.repos;

import com.telegame.code.models.Player_Play_Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PPMRepo extends JpaRepository<Player_Play_Match, Long> {

//    @Transactional
//    @Query("INSERT")
//    void updateGameMatch(long ppm_id, long gameMatch_id);
}
