package com.mastermind.game.controller;

import com.mastermind.game.dto.AttemptRequest;
import com.mastermind.game.dto.RankingDTO;
import com.mastermind.game.entity.Game;
import com.mastermind.game.service.GameService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public Game start(@RequestParam Long userId) {
        return gameService.startGame(userId);
    }

    @PostMapping("/attempt")
    public Map<String, Object> attempt(@RequestBody AttemptRequest request) {
        return gameService.makeAttempt(request);
    }

    @GetMapping("/ranking")
    public List<RankingDTO> ranking() {
        return gameService.ranking();
    }
}