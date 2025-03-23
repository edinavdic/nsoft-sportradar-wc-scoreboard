# Alternative Solution - Live Football World Cup Score Board Library

## Key Differences
- `Match` Class
    - `finished` Field: Boolean flag to track if the match is complete.
    - `finishMatch()` Method: Marks the match as finished.
    - Manages its own state (scores and finished status).
- `ScoreBoard` Class
    - Acts as a factory for Match objects and a summary provider, but delegates `updateScore(...)` and `finishMatch()` to Match.

### Usage
```java
Match match = scoreBoard.startMatch("Denmark", "Norway");
match.updateScore(3, 1);
match.finishMatch();
```