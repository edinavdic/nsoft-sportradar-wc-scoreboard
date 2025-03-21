package com.edinavdic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @Test
    void startMatch_ValidInput_MatchIsAdded() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertEquals(1, sb.getSummary().size());
    }

    @Test
    void startTwoMatches_MatchesAreAddedToScoreboard() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.startMatch("Sweden", "Finland");
        assertEquals(2, sb.getSummary().size());
    }

    @Test
    void startMatch_UpdateScore_ScoreIsUpdatedCorrectly() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.updateScore("Denmark", "Norway", 0, 2);
        assertEquals("Denmark 0 - Norway 2", sb.getSummary().get(0));
    }

    @Test
    void finishMatch_MatchExists_MatchIsRemoved() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.finishMatch("Denmark", "Norway");
        assertEquals(0, sb.getSummary().size());
    }

    @Test
    void startMatch_InvalidHomeTeam_ThrowsException() {
        ScoreBoard sb = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("", "Norway"));
    }

    @Test
    void startMatch_InvalidAwayTeam_ThrowsException() {
        ScoreBoard sb = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("Denmark", null));
    }
}
