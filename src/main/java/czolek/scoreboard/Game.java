package czolek.scoreboard;

import java.time.Instant;
import java.util.Objects;

public record Game(Team home, Team away, Instant startTimestamp) {
    public Game {
        Objects.requireNonNull(home, "Home team cannot be null");
        Objects.requireNonNull(away, "Away team cannot be null");
        Objects.requireNonNull(startTimestamp, "Game start timestamp cannot be null");
    }

    public Game(Team home, Team away) {
        this(home, away, Instant.now());
    }

    public GameId id() {
        return new GameId(home.name(), away.name());
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %d - %d", home().name(), away().name(), home().score(), away().score());
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
}
