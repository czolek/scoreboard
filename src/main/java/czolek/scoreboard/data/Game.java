package czolek.scoreboard.data;

import java.time.Instant;
import java.util.Objects;

public record Game(Team home, Team away, Score score, Instant startTimestamp) {
    public Game {
        Objects.requireNonNull(home, "Home team cannot be null");
        Objects.requireNonNull(away, "Away team cannot be null");
        Objects.requireNonNull(score, "Score cannot be null");
        Objects.requireNonNull(startTimestamp, "Game start timestamp cannot be null");
    }

    public Game(Team home, Team away, Score score) {
        this(home, away, score, Instant.now());
    }

    public Game(Team home, Team away) {
        this(home, away, new Score(0, 0), Instant.now());
    }

    public GameId id() {
        return new GameId(home.name(), away.name());
    }

    public Game updateScore(Score score) {
        return new Game(home(), away(), score, startTimestamp());
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %d - %d", home().name(), away().name(), score().home(), score().away());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return home.equals(game.home) && away.equals(game.away);
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away);
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private String home;
        private String away;
        private Score score;

        public GameBuilder home(String homeTeamName) {
            home = homeTeamName;
            return this;
        }

        public GameBuilder away(String awayTeamName) {
            away = awayTeamName;
            return this;
        }

        public GameBuilder score(int home, int away) {
            score = new Score(home, away);
            return this;
        }

        public Game build() {
            return new Game(new Team(home), new Team(away), score);
        }
    }
}
