package czolek.scoreboard.data;

import java.util.Objects;

public record Team(String name) {
    public Team {
        Objects.requireNonNull(name, "Name cannot be null");
    }

    public static Team of(String name) {
        return new Team(name);
    }
}
