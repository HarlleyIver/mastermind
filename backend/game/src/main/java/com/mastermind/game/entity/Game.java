package com.mastermind.game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretCode;
    private Integer attemptsUsed;
    private Boolean finished;
    private Integer score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attempt> attempts;

    public Game() {}

    public Long getId() { return id; }
    public String getSecretCode() { return secretCode; }
    public void setSecretCode(String secretCode) { this.secretCode = secretCode; }
    public Integer getAttemptsUsed() { return attemptsUsed; }
    public void setAttemptsUsed(Integer attemptsUsed) { this.attemptsUsed = attemptsUsed; }
    public Boolean getFinished() { return finished; }
    public void setFinished(Boolean finished) { this.finished = finished; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<Attempt> getAttempts() { return attempts; }
}