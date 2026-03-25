package com.mastermind.game.service;

import com.mastermind.game.entity.*;
import com.mastermind.game.repository.*;
import com.mastermind.game.dto.AttemptRequest;
import com.mastermind.game.dto.RankingDTO;
import com.mastermind.game.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;

    public GameService(GameRepository gameRepository,
                       UserRepository userRepository,
                       AttemptRepository attemptRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    public Game startGame(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Game game = new Game();
        game.setUser(user);
        game.setSecretCode(generateSecretCode());
        game.setAttemptsUsed(0);
        game.setFinished(false);
        game.setStartTime(LocalDateTime.now());

        return gameRepository.save(game);
    }

    public Map<String, Object> makeAttempt(AttemptRequest request) {
        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));

        if (game.getFinished()) throw new RuntimeException("Jogo já finalizado");

        String secret = game.getSecretCode();
        String guess = request.getGuess();

        int correctPositions = 0;
        int correctColors = 0;

        boolean[] secretUsed = new boolean[4];
        boolean[] guessUsed = new boolean[4];

        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                correctPositions++;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue;
            for (int j = 0; j < 4; j++) {
                if (!secretUsed[j] && guess.charAt(i) == secret.charAt(j)) {
                    correctColors++;
                    secretUsed[j] = true;
                    break;
                }
            }
        }

        Attempt attempt = new Attempt();
        attempt.setGame(game);
        attempt.setGuess(guess);
        attempt.setCorrectPositions(correctPositions);
        attempt.setCorrectColors(correctColors);
        attempt.setAttemptNumber(game.getAttemptsUsed() + 1);

        attemptRepository.save(attempt);

        game.setAttemptsUsed(game.getAttemptsUsed() + 1);

        Map<String, Object> response = new HashMap<>();
        response.put("correctPositions", correctPositions);
        response.put("correctColors", correctColors);

        if (correctPositions == 4) {
            game.setFinished(true);
            game.setEndTime(LocalDateTime.now());
            game.setScore(100 - (game.getAttemptsUsed() * 10));
            response.put("win", true);
            response.put("secret", secret);
        } else if (game.getAttemptsUsed() == 10) {
            game.setFinished(true);
            game.setEndTime(LocalDateTime.now());
            game.setScore(0);
            response.put("lose", true);
            response.put("secret", secret);
        }

        gameRepository.save(game);

        return response;
    }

    public List<RankingDTO> ranking() {
        return gameRepository.findRanking();
    }

    private String generateSecretCode() {
        List<Character> colors = new ArrayList<>(Arrays.asList('A','B','C','D','E','F'));
        Collections.shuffle(colors);
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) code.append(colors.get(i));
        return code.toString();
    }
}