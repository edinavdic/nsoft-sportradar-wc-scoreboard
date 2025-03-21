package com.edinavdic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreBoard {

    private final Map<String, Match> matches = new HashMap<>(); // ConcurrentHashMap?

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        var key = genKey(homeTeam, awayTeam);
        if (matches.containsKey(key)) {
            throw new IllegalArgumentException("Match is already in play");
        }
        matches.put(key, new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        validateTeamNames(homeTeam, awayTeam);
        var key = genKey(homeTeam, awayTeam);
        var match = matches.get(key);
        if (match == null) {
            throw new IllegalArgumentException("Match not found"); // custom exception?
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
                .map(Match::toString)
                .collect(Collectors.toList());
    }

    private String genKey(String homeTeam, String awayTeam) {
        return homeTeam + ":" + awayTeam;
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isEmpty() || awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("HomeTeam and AwayTeam names cannot be null or empty");
        }
    }
}
