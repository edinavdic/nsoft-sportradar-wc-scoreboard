package com.edinavdic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public Match startMatch(String homeTeam, String awayTeam) {
        var match = new Match(homeTeam, awayTeam);
        if (teamAlreadyPlaying(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("One of the teams is already playing");
        }
        matches.add(match);
        return match;
    }

    public List<Match> getSummary() {
        return matches.stream()
                .filter( m -> !m.isFinished())
                .sorted( (m1, m2) -> {
                    int scoreCompare = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
                    return (scoreCompare != 0) ? scoreCompare : m2.getStartTime().compareTo(m1.getStartTime());
                })
                .collect(Collectors.toList());
    }

    private boolean teamAlreadyPlaying(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(match -> !match.isFinished())
                .anyMatch(match ->
                match.getHomeTeam().equals(homeTeam) || match.getAwayTeam().equals(homeTeam) ||
                match.getHomeTeam().equals(awayTeam) || match.getAwayTeam().equals(awayTeam));
    }
}
