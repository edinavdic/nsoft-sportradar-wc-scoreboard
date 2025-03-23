# Live Football World Cup Score Board Library   

Java library for live football match scores updates, built with Java 17, Maven 3.9, and JUnit 5.

[alternative_solution branch](https://github.com/edinavdic/nsoft-sportradar-wc-scoreboard/tree/alternative_solution) - Alternative implementation where the `Match` class manages its own state and operations while the `ScoreBoard` acts as a factory and summary provider.

## Features
- **Start matches** between two teams (initial score 0-0).
- **Update scores** with absolute values.
- **Finish matches** to remove them from the scoreboard.
- **Get summaries** of ongoing matches sorted by highest total score and most recent start time.

## Assumptions
- Team names uniquely identify matches (no two ongoing matches can have the same home and/or away team).
- Scores are non-negative integers.
- The library is single-threaded by default. For multithreaded use see Notes below.
- Team names are case-sensitive strings (e.g., 'Germany', 'germany', and ' Germany' are considered distinct).

## Installation

### Maven
Install to Local Maven Repository
```bash
mvn clean install
```

Add the dependency to your project’s `pom.xml`:
```xml
<dependency>
    <groupId>com.edinavdic</groupId>
    <artifactId>nsoft-sportradar-wc-scoreboard</artifactId>
    <version>1.0</version>
</dependency>
```
#### Usage
```java
ScoreBoard scoreBoard = new ScoreBoard();
// Start matches
scoreBoard.startMatch("Spain", "Brazil");
scoreBoard.startMatch("Denmark", "Norway");
// Update scores
scoreBoard.updateScore("Spain", "Brazil", 3, 1);
// Finish a match
scoreBoard.finishMatch("Spain", "Brazil");
// Get summary
scoreBoard.getSummary().forEach(System.out::println) // Denmark 0 - Norway 0
```

## Notes on Improvements
### Thread Safety
The current implementation uses `HashMap`, which is not thread-safe. For multithreaded environments:
```java
// In ScoreBoard.java
private final Map<String, Match> matches = new ConcurrentHashMap<>();

// In Match.java
public synchronized void updateScore(int homeTeamScore, int awayTeamScore) { ... }
```

### Error Handling
The library uses `IllegalArgumentException` for simplicity, with clear error messages for common issues.
For better clarity we can introduce custom exceptions:
 - `MatchNotFoundException`
 - `TeamAlreadyPlayingException`
### Validation Duplication
Team name validation is duplicated in `Match` and `ScoreBoard`.
This could be refactored into a utility class (e.g., `TeamValidator`) for reusability:
```java
public class TeamValidator {
    public static void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank() || awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be null or empty");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Team cannot play against itself");
        }
    }
}
```

### Efficiency of Team Already Playing Check
The `teamAlreadyPlaying` method in `ScoreBoard` has O(n) time complexity. For better performance we can introduce a `Set<String>` of active teams for O(1) lookups with slight memory overhead costs:
```java
// In ScoreBoard.java
private final Set<String> activeTeams = new HashSet<>();

public void startMatch(String homeTeam, String awayTeam) {
    validateTeamNames(homeTeam, awayTeam);
    if (activeTeams.contains(homeTeam) || activeTeams.contains(awayTeam)) {
        throw new IllegalArgumentException("One of the teams is already playing");
    }
    ...
```