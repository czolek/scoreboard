package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static czolek.scoreboard.data.Score.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

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
        assertThat(summary).containsExactly(Game.builder().home("Mexico").away("Canada").score(0, 0).build());
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'startGame' should add another game with 0-0 result")
    void shouldAddAGame() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(5, 2).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.startGame("Spain", "Brazil");

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(2);
        assertThat(summary).containsExactly(
                MEXICO_CANADA,
                Game.builder().home("Spain").away("Brazil").score(0,0).build()
        );
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'finishGame' should remove it and leave empty board")
    void shouldRemoveFinishedGameAndLeaveEmptyBoard() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(1, 0).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.finishGame("Mexico", "Canada");

        //then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(0);
    }

    @Test
    @DisplayName("Given empty ScoreBoard, 'finishGame' should do nothing, board remains empty")
    void shouldLeaveEmptyBoardAfterRemovingNonExistingGame() {
        // given
        ScoreBoard scoreBoard = new InMemoryScoreBoard();

        // when
        scoreBoard.finishGame("Mexico", "Canada");

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(0);
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'finishGame' that does not exist should do nothing")
    void shouldDoNothingWhenFinishingNonExisingGame() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(1, 0).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.finishGame("Spain", "Brazil");

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).containsExactly(MEXICO_CANADA);
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'updateScore' should update the score")
    void shouldUpdateTheScore() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(1, 0).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        var score = of(1, 3);
        scoreBoard.updateScore("Mexico", "Canada", score);

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).containsExactly(Game.builder().home("Mexico").away("Canada").score(1, 3).build());
    }

    @Test
    @DisplayName("Should throw an exception when updating score of non-existent game")
    void shouldThrowExceptionWhenUpdatingScoreOfNonExistentGame() {
        // given
        ScoreBoard scoreBoard = new InMemoryScoreBoard();

        // when
        Throwable throwable = catchThrowable(() -> scoreBoard.updateScore("Spain", "Brazil", of(1, 3)));

        // then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot find game 'Spain - Brazil'");
    }

    @Test
    @DisplayName("Given initial matches, 'getSummary' should sort results by score and start timestamp")
    void shouldSortSummary() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(0, 5).build();
        var SPAIN_BRAZIL = Game.builder().away("Spain").home("Brazil").score(10, 2).build();
        var GERMANY_FRANCE = Game.builder().home("Germany").away("France").score(2, 2).build();
        var URUGUAY_ITALY = Game.builder().home("Uruguay").away("Italy").score(6, 6).build();
        var ARGENTINA_AUSTRALIA = Game.builder().home("Argentina").away("Australia").score(3, 1).build();

        ScoreBoard scoreBoard = new InMemoryScoreBoard(
                MEXICO_CANADA,
                SPAIN_BRAZIL,
                GERMANY_FRANCE,
                URUGUAY_ITALY,
                ARGENTINA_AUSTRALIA
        );

        // when
        var summary = scoreBoard.getSummary();

        // then
        assertThat(summary).hasSize(5);
        assertThat(summary).containsExactly(
                URUGUAY_ITALY,
                SPAIN_BRAZIL,
                MEXICO_CANADA,
                ARGENTINA_AUSTRALIA,
                GERMANY_FRANCE
        );
    }
}
