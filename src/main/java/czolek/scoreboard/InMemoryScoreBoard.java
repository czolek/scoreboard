package czolek.scoreboard;

import java.util.List;

public class InMemoryScoreBoard implements ScoreBoard {
    public InMemoryScoreBoard() {

    }

    public InMemoryScoreBoard(Game... games) {

    }

    @Override
    public Game startGame(String homeTeamName, String awayTeamName) {
        return null;
    }

    @Override
    public List<Game> getSummary() {
        return List.of();
    }
}
