package com.mastermind.game.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guess;
    private int correctPositions;
    private int correctColors;
    private int attemptNumber;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Attempt() {}

    public Long getId() { return id; }
    public String getGuess() { return guess; }
    public void setGuess(String guess) { this.guess = guess; }
    public int getCorrectPositions() { return correctPositions; }
    public void setCorrectPositions(int correctPositions) { this.correctPositions = correctPositions; }
    public int getCorrectColors() { return correctColors; }
    public void setCorrectColors(int correctColors) { this.correctColors = correctColors; }
    public int getAttemptNumber() { return attemptNumber; }
    public void setAttemptNumber(int attemptNumber) { this.attemptNumber = attemptNumber; }
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}