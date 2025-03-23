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
        var match = sb.startMatch("Denmark", "Norway");
        match.updateScore(0, 2);
        assertEquals("Denmark 0 - Norway 2", match.toString());
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
        var match = sb.startMatch("Denmark", "Norway");
        match.finishMatch();
        assertEquals(0, sb.getSummary().size());
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
    void updateScore_InvalidScore_ThrowsException() {
        var sb = new ScoreBoard();
        var match = sb.startMatch("Denmark", "Norway");
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, 2));
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScore() {
        var sb = new ScoreBoard();
        var matchOne = sb.startMatch("Denmark", "Canada");
        matchOne.updateScore(0, 5);
        var matchTwo = sb.startMatch("Spain", "Brazil");
        matchTwo.updateScore(10, 2);
        var matchThree = sb.startMatch("Germany", "France");
        matchThree.updateScore(2, 2);
        var summary = sb.getSummary();
        assertEquals("Spain 10 - Brazil 2", summary.get(0).toString());
        assertEquals("Denmark 0 - Canada 5", summary.get(1).toString());
        assertEquals("Germany 2 - France 2", summary.get(2).toString());
    }

    @Test
    void getSummary_NoMatches_ReturnsEmptyList() {
        var sb = new ScoreBoard();
        assertEquals(0, sb.getSummary().size());
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScore_SameScores_OrderedByMostRecent() {
        var sb = new ScoreBoard();
        var matchOne = sb.startMatch("Denmark", "Canada");
        matchOne.updateScore(4, 1);
        var matchTwo = sb.startMatch("Spain", "Brazil");
        matchTwo.updateScore(2, 3);
        var matchThree = sb.startMatch("Germany", "France");
        matchThree.updateScore(1, 4);
        var summary = sb.getSummary();
        assertEquals("Germany 1 - France 4", summary.get(0).toString());
        assertEquals("Spain 2 - Brazil 3", summary.get(1).toString());
        assertEquals("Denmark 4 - Canada 1", summary.get(2).toString());
    }

    @Test
    void getSummary_MultipleMatches_OrderedByTotalScoreThenOrderedByMostRecent() {
        var sb = new ScoreBoard();
        var matchOne = sb.startMatch("Denmark", "Canada");
        matchOne.updateScore(4, 4);
        var matchTwo = sb.startMatch("Spain", "Brazil");
        matchTwo.updateScore(2, 3);
        var matchThree = sb.startMatch("Germany", "France");
        matchThree.updateScore(6, 4);
        var matchFour = sb.startMatch("Sweden", "Norway");
        matchFour.updateScore(1, 4);
        var summary = sb.getSummary();
        assertEquals("Germany 6 - France 4", summary.get(0).toString());
        assertEquals("Denmark 4 - Canada 4", summary.get(1).toString());
        assertEquals("Sweden 1 - Norway 4", summary.get(2).toString());
        assertEquals("Spain 2 - Brazil 3", summary.get(3).toString());
    }

    @Test
    void updateScore_MatchFinished_ThrowsException() {
        var sb = new ScoreBoard();
        var match = sb.startMatch("Denmark", "Norway");
        match.finishMatch();
        assertThrows(IllegalStateException.class, () -> match.updateScore(1, 2));
    }

    @Test
    void finishMatch_MatchFinished_ThrowsException() {
        var sb = new ScoreBoard();
        var match = sb.startMatch("Denmark", "Norway");
        match.finishMatch();
        assertThrows(IllegalStateException.class, match::finishMatch);
    }
}
