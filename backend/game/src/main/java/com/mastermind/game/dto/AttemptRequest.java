package com.mastermind.game.dto;

public class AttemptRequest {

    private Long gameId;
    private String guess;

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public String getGuess() { return guess; }
    public void setGuess(String guess) { this.guess = guess; }
}