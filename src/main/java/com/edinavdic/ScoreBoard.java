package com.edinavdic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreBoard {

    private final Map<String, Match> matches = new HashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        if (teamAlreadyPlaying(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("One of the teams is already playing");
        }
        matches.put(genKey(homeTeam, awayTeam), new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        validateTeamNames(homeTeam, awayTeam);
        var key = genKey(homeTeam, awayTeam);
        var match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match not found");
        }
        match.updateScore(homeTeamScore, awayTeamScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        var key = genKey(homeTeam, awayTeam);
        if (!matches.containsKey(key)) {
            throw new IllegalArgumentException("Match not found");
        }
        matches.remove(key);
    }

    public List<String> getSummary() {
        return matches.values().stream()
                .sorted( (m1, m2) -> {
                    int scoreCompare = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
                    return (scoreCompare != 0) ? scoreCompare : m2.getStartTime().compareTo(m1.getStartTime());
                })
                .map(Match::toString)
                .collect(Collectors.toList());
    }

    private String genKey(String homeTeam, String awayTeam) {
        var encodeHome = homeTeam.replace(":", "::");
        var encodeAway = awayTeam.replace(":", "::");
        return encodeHome + ":" + encodeAway;
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank() || awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("HomeTeam and AwayTeam names cannot be null or empty");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team cannot play against itself");
        }
    }

    private boolean teamAlreadyPlaying(String homeTeam, String awayTeam) {
        return matches.values().stream().anyMatch(match ->
                match.getHomeTeam().equals(homeTeam) || match.getAwayTeam().equals(homeTeam) ||
                match.getHomeTeam().equals(awayTeam) || match.getAwayTeam().equals(awayTeam));
    }
}
