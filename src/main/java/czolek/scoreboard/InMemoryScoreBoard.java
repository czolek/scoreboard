package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.GameId;
import czolek.scoreboard.data.Score;
import czolek.scoreboard.data.Team;
import czolek.scoreboard.repository.InMemoryScoreBoardRepository;
import czolek.scoreboard.repository.ScoreBoardRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class InMemoryScoreBoard implements ScoreBoard {
    private final ScoreBoardRepository repository;
    private final Comparator<Game> summaryComparator;

    public InMemoryScoreBoard() {
        this(defaultSummaryComparator());
    }

    public InMemoryScoreBoard(Game... initialGames) {
        this(defaultSummaryComparator(), initialGames);
    }

    public InMemoryScoreBoard(Comparator<Game> summaryComparator, Game... initialGames) {
        Objects.requireNonNull(summaryComparator, "Summary comparator cannot be null");
        Objects.requireNonNull(initialGames, "Initial games cannot be null");

        this.repository = new InMemoryScoreBoardRepository();
        this.summaryComparator = summaryComparator;
        Arrays.stream(initialGames).forEach(game -> repository.save(game));
    }

    @Override
    public Game startGame(Team home, Team away) {
        var game = new Game(home, away);
        repository.save(game);
        return game;
    }

    @Override
    public Game finishGame(GameId id) {
        return repository.deleteById(id);
    }

    @Override
    public Game updateScore(GameId id, Score score) {
        var existingGame = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot find game '%s'", id)));
        var updatedGame = existingGame.withScore(score);
        repository.save(updatedGame);
        return updatedGame;
    }

    @Override
    public List<Game> getSummary() {
        return repository.findAll().stream()
                .sorted(summaryComparator)
                .toList();
    }

    private static Comparator<Game> defaultSummaryComparator() {
        var scoreComparator = Comparator.comparing(Game::totalScore).reversed();
        var timestampComparator = Comparator.comparing(Game::startTimestamp).reversed();
        return scoreComparator.thenComparing(timestampComparator);
    }
}
