package czolek.scoreboard;

import java.time.Instant;

public record Game(Team home, Team away, Instant startTimestamp) {
    public Game(Team home, Team away) {
        this(home, away, Instant.now());
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %d - %d", home().name(), away().name(), home().score(), away().score());
    }
}
