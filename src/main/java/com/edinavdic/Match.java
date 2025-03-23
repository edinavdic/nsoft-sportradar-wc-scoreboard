package com.edinavdic;

import java.time.Instant;

public class Match {

    private final String homeTeam;
    private final String awayTeam;
    private final Instant startTime;
    private int homeTeamScore;
    private int awayTeamScore;
    private boolean finished;

    public Match(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank() || awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("HomeTeam and AwayTeam names cannot be null or empty");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team cannot play against itself");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = Instant.now();
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    public void updateScore(int homeTeamScore, int awayTeamScore) {
        if (finished) {
            throw new IllegalStateException("Match is already finished");
        }
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public void finishMatch() {
        if (finished) {
            throw new IllegalStateException("Match is already finished");
        }
        finished = true;
    }

    @Override
    public String toString() {
        return String.format("%s %d - %s %d", homeTeam, homeTeamScore, awayTeam, awayTeamScore);
    }
}
