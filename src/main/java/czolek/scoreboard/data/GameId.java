package czolek.scoreboard.data;

import java.util.Objects;

public record GameId(String home, String away) {
    public GameId {
        Objects.requireNonNull(home, "Home team name cannot be null");
        Objects.requireNonNull(away, "Away team name cannot be null");
    }

    public static GameId id(String home, String away) {
        return new GameId(home, away);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", home(), away());
    }
}
