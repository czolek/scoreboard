package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.GameId;
import czolek.scoreboard.data.Score;
import czolek.scoreboard.data.Team;

import java.util.List;

public interface ScoreBoard {
    Game startGame(Team home, Team away);

    Game finishGame(GameId gameId);

    Game updateScore(GameId gameId, Score score);

    List<Game> getSummary();
}
