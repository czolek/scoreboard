package czolek.scoreboard;

import java.util.Objects;

public record Team(String name) {
    public Team {
        Objects.requireNonNull(name, "Name cannot be null");
    }
}
