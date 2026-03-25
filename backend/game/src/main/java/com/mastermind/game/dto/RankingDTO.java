package com.mastermind.game.dto;

import java.time.LocalDateTime;

public class RankingDTO {

    private Long gameId;
    private String username;
    private Integer score;
    private Integer attemptsUsed;
    private LocalDateTime endTime;

    public RankingDTO(Long gameId, String username, Integer score, Integer attemptsUsed, LocalDateTime endTime) {
        this.gameId = gameId;
        this.username = username;
        this.score = score;
        this.attemptsUsed = attemptsUsed;
        this.endTime = endTime;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getAttemptsUsed() {
        return attemptsUsed;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}