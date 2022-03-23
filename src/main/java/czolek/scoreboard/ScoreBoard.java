package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.Score;

import java.util.List;

public interface ScoreBoard {
    Game startGame(String homeTeamName, String awayTeamName);

    Game finishGame(String homeTeamName, String awayTeamName);

    Game updateScore(String homeTeamName, String awayTeamName, Score score);

    List<Game> getSummary();
}
