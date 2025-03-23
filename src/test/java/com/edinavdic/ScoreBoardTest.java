package com.edinavdic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @Test
    void startMatch_ValidInput_MatchIsAdded() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertEquals(1, sb.getSummary().size());
    }

    @Test
    void startMatch_TwoMatches_MatchesAreAddedToScoreboard() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.startMatch("Sweden", "Finland");
        assertEquals(2, sb.getSummary().size());
    }

    @Test
    void startMatch_UpdateScore_ScoreIsUpdatedCorrectly() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.updateScore("Denmark", "Norway", 0, 2);
        assertEquals("Denmark 0 - Norway 2", sb.getSummary().get(0));
    }

    @Test
    void startMatch_CaseSensitiveTeams_Allowed() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "denmark");
        sb.startMatch(" France", "France ");
        assertEquals(2, sb.getSummary().size());
    }

    @Test
    void finishMatch_MatchExists_MatchIsRemoved() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.finishMatch("Denmark", "Norway");
        assertEquals(0, sb.getSummary().size());
    }

    @Test
    void finishMatch_MatchNotFound_ThrowsException() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> sb.finishMatch("Denmark", "Sweden"));
    }

    @Test
    void startMatch_InvalidHomeTeam_ThrowsException() {
        var sb = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("", "Norway"));
    }

    @Test
    void startMatch_InvalidAwayTeam_ThrowsException() {
        var sb = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("Denmark", null));
    }

    @Test
    void startMatch_MatchAlreadyInPlay_ThrowsException() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("Denmark", "Norway"));
    }

    @Test
    void startMatch_TeamAlreadyPlaying_ThrowsException() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("Finland", "Norway"));
    }

    @Test
    void startMatch_TeamPlayingItself_ThrowsException() {
        var sb = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> sb.startMatch("Finland", "Finland"));
    }

    @Test
    void updateScore_MatchNotFound_ThrowsException() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> sb.updateScore("Denmark", "Sweden", 0, 1));
    }

    @Test
    void updateScore_InvalidScore_ThrowsException() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> sb.updateScore("Denmark", "Norway", -1, 2));
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScore() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Canada");
        sb.updateScore("Denmark", "Canada", 0, 5);
        sb.startMatch("Spain", "Brazil");
        sb.updateScore("Spain", "Brazil", 10, 2);
        sb.startMatch("Germany", "France");
        sb.updateScore("Germany", "France", 2, 2);
        var summary = sb.getSummary();
        assertEquals("Spain 10 - Brazil 2", summary.get(0));
        assertEquals("Denmark 0 - Canada 5", summary.get(1));
        assertEquals("Germany 2 - France 2", summary.get(2));
    }

    @Test
    void getSummary_NoMatches_ReturnsEmptyList() {
        var sb = new ScoreBoard();
        assertEquals(0, sb.getSummary().size());
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScore_SameScores_OrderedByMostRecent() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Canada");
        sb.updateScore("Denmark", "Canada", 4, 1);
        sb.startMatch("Spain", "Brazil");
        sb.updateScore("Spain", "Brazil", 2, 3);
        sb.startMatch("Germany", "France");
        sb.updateScore("Germany", "France", 1, 4);
        var summary = sb.getSummary();
        assertEquals("Germany 1 - France 4", summary.get(0));
        assertEquals("Spain 2 - Brazil 3", summary.get(1));
        assertEquals("Denmark 4 - Canada 1", summary.get(2));
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScoreThenOrderedByMostRecent() {
        var sb = new ScoreBoard();
        sb.startMatch("Denmark", "Canada");
        sb.updateScore("Denmark", "Canada", 4, 4);
        sb.startMatch("Spain", "Brazil");
        sb.updateScore("Spain", "Brazil", 2, 3);
        sb.startMatch("Germany", "France");
        sb.updateScore("Germany", "France", 6, 4);
        sb.startMatch("Sweden", "Norway");
        sb.updateScore("Sweden", "Norway", 1, 4);
        var summary = sb.getSummary();
        assertEquals("Germany 6 - France 4", summary.get(0));
        assertEquals("Denmark 4 - Canada 4", summary.get(1));
        assertEquals("Sweden 1 - Norway 4", summary.get(2));
        assertEquals("Spain 2 - Brazil 3", summary.get(3));
    }
}
