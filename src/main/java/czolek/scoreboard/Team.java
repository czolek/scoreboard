package czolek.scoreboard;

import java.util.Objects;

public record Team(String name, int score) {
    public Team {
        Objects.requireNonNull(name, "Name cannot be null");
    }

    public Team(String name) {
        this(name, 0);
    }

    @Override
    public String toString() {
        return String.format("%s: %d", name, score);
    }
}
