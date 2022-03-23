package czolek.scoreboard;

import java.util.List;

public interface ScoreBoard {
    Game startGame(String homeTeamName, String awayTeamName);

    Game finishGame(String homeTeamName, String awayTeamName);

    List<Game> getSummary();
}
