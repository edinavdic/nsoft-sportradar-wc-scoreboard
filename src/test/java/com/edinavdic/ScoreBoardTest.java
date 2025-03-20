package com.edinavdic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @Test
    void startMatchAndVerifyMatchExists() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        assertEquals(1, sb.getSummary().size());
    }

    @Test
    void startTwoMatchesAndVerifyMatchesExist() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.startMatch("Sweden", "Finland");
        assertEquals(2, sb.getSummary().size());
    }

    @Test
    void startMatchAndVerifyMatchUpdatesCorrectly() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.updateScore("Denmark", "Norway", 0, 2);
        assertEquals("Denmark 0 - Norway 2", sb.getSummary().get(0));
    }

    @Test
    void finishMatchAndVerifyMatchIsRemoved() {
        ScoreBoard sb = new ScoreBoard();
        sb.startMatch("Denmark", "Norway");
        sb.finishMatch("Denmark", "Norway");
        assertEquals(0, sb.getSummary().size());
    }
}
