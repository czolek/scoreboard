package czolek.scoreboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreBoardTest {

    @Test
    @DisplayName("Given empty ScoreBoard, 'startGame' should add a game with home and away teams with 0-0 starting result")
    void shouldStartAGame() {
        // given
        ScoreBoard scoreBoard = new InMemoryScoreBoard();

        // when
        var game = scoreBoard.startGame("Mexico", "Canada");

        //then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).containsExactly(new Game(new Team("Mexico", 0), new Team("Canada", 0)));
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'startGame' should add another game with 0-0 result")
    void shouldAddAGame() {
        // given
        var MEXICO_CANADA = new Game(new Team("Mexico", 1), new Team("Canada", 0));
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.startGame("Spain", "Brazil");

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(2);
        assertThat(summary).containsExactly(
                MEXICO_CANADA,
                new Game(new Team("Spain", 0), new Team("Brazil", 0))
        );
    }
}
