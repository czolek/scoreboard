package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.GameId;
import czolek.scoreboard.data.Score;
import czolek.scoreboard.data.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;

public class InMemoryScoreBoard implements ScoreBoard {
    private final ConcurrentMap<GameId, Game> matches = new ConcurrentHashMap<>();

    public InMemoryScoreBoard() {

    }

    public InMemoryScoreBoard(Game... initialGames) {
        Objects.requireNonNull(initialGames, "Initial games cannot be null");

        Arrays.stream(initialGames).forEach(game -> matches.put(game.id(), game));
    }

    @Override
    public Game startGame(String homeTeamName, String awayTeamName) {
        var game = new Game(new Team(homeTeamName), new Team(awayTeamName));
        matches.put(game.id(), game);
        return game;
    }

    @Override
    public Game finishGame(String homeTeamName, String awayTeamName) {
        var id = new GameId(homeTeamName, awayTeamName);
        return matches.remove(id);
    }

    @Override
    public Game updateScore(String homeTeamName, String awayTeamName, Score score) {
        var id = new GameId(homeTeamName, awayTeamName);
        var existingGame = ofNullable(matches.get(id))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot find game '%s - %s'", homeTeamName, awayTeamName)));
        var updatedGame = existingGame.updateScore(score);
        matches.put(id, updatedGame);
        return updatedGame;
    }

    @Override
    public List<Game> getSummary() {
        return List.copyOf(matches.values());
    }
}
