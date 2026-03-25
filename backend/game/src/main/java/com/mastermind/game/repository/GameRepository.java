package com.mastermind.game.repository;

import com.mastermind.game.dto.RankingDTO;
import com.mastermind.game.entity.Game;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
        SELECT new com.mastermind.game.dto.RankingDTO(
            g.id,
            g.user.username,
            g.score,
            g.attemptsUsed,
            g.endTime
        )
        FROM Game g
        WHERE g.finished = true
        AND g.score IS NOT NULL

        AND g.score = (
            SELECT MAX(g2.score)
            FROM Game g2
            WHERE g2.user.id = g.user.id
            AND g2.finished = true
        )

        AND g.attemptsUsed = (
            SELECT MIN(g3.attemptsUsed)
            FROM Game g3
            WHERE g3.user.id = g.user.id
            AND g3.score = g.score
            AND g3.finished = true
        )

        AND g.endTime = (
            SELECT MIN(g4.endTime)
            FROM Game g4
            WHERE g4.user.id = g.user.id
            AND g4.score = g.score
            AND g4.attemptsUsed = g.attemptsUsed
            AND g4.finished = true
        )

        ORDER BY g.score DESC, g.attemptsUsed ASC, g.endTime ASC
    """)
    List<RankingDTO> findRanking();
}