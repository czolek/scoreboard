package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.Score;
import czolek.scoreboard.data.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DataValidationTest {

    @Test
    @DisplayName("Should throw validation exception when creating team with null name")
    void shouldThrowValidationExceptionWhenCreatingTeamWithNullName() {
        Throwable throwable = catchThrowable(() -> new Team(null));

        assertThat(throwable)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Name cannot be null");
    }

    @Test
    @DisplayName("Should throw validation exception when creating game with null home team")
    void shouldThrowValidationExceptionWhenCreatingGameWithNullHomeTeam() {
        Throwable throwable = catchThrowable(() -> new Game(null, new Team("away")));

        assertThat(throwable)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Home team cannot be null");
    }

    @Test
    @DisplayName("Should throw validation exception when creating game with null away team")
    void shouldThrowValidationExceptionWhenCreatingGameWithNullAwayTeam() {
        Throwable throwable = catchThrowable(() -> new Game(new Team("home"), null));

        assertThat(throwable)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Away team cannot be null");
    }

    @Test
    @DisplayName("Should throw validation exception when creating game with null start timestamp")
    void shouldThrowValidationExceptionWhenCreatingGameWithNullStartTimestamp() {
        Throwable throwable = catchThrowable(() -> new Game(new Team("home"), new Team("away"), new Score(), null));

        assertThat(throwable)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Game start timestamp cannot be null");
    }

    @Test
    @DisplayName("Should throw validation exception when creating game with null score")
    void ShouldThrowValidationExceptionWhenCreatingGameWithNullScore() {
        Throwable throwable = catchThrowable(() -> new Game(new Team("home"), new Team("away"), null));
    }
}
