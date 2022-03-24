package czolek.scoreboard;

import czolek.scoreboard.data.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static czolek.scoreboard.data.GameId.id;
import static czolek.scoreboard.data.Score.score;
import static czolek.scoreboard.data.Team.team;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.data.Index.atIndex;

class ScoreBoardTest {

    @Test
    @DisplayName("Given empty ScoreBoard, 'startGame' should add a game with home and away teams with 0-0 starting result")
    void shouldStartAGame() {
        // given
        ScoreBoard scoreBoard = new InMemoryScoreBoard();

        // when
        scoreBoard.startGame(team("Mexico"), team("Canada"));

        //then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).satisfies(verifyGame(Game.builder().home("Mexico").away("Canada").score(0, 0).build()), atIndex(0));
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'startGame' should add another game with 0-0 result")
    void shouldAddAGame() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(5, 2).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.startGame(team("Spain"), team("Brazil"));

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(2);
        assertThat(summary).satisfies(verifyGame(MEXICO_CANADA), atIndex(0));
        assertThat(summary).satisfies(verifyGame(Game.builder().home("Spain").away("Brazil").score(0, 0).build()), atIndex(1));
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'finishGame' should remove it and leave empty board")
    void shouldRemoveFinishedGameAndLeaveEmptyBoard() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(1, 0).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        scoreBoard.finishGame(id("Mexico", "Canada"));

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
        scoreBoard.finishGame(id("Mexico", "Canada"));

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
        scoreBoard.finishGame(id("Spain", "Brazil"));

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).satisfies(verifyGame(MEXICO_CANADA), atIndex(0));
    }

    @Test
    @DisplayName("Given ScoreBoard with existing game, 'updateScore' should update the score")
    void shouldUpdateTheScore() {
        // given
        var MEXICO_CANADA = Game.builder().home("Mexico").away("Canada").score(1, 0).build();
        ScoreBoard scoreBoard = new InMemoryScoreBoard(MEXICO_CANADA);

        // when
        var score = score(1, 3);
        scoreBoard.updateScore(id("Mexico", "Canada"), score);

        // then
        var summary = scoreBoard.getSummary();
        assertThat(summary).hasSize(1);
        assertThat(summary).satisfies(verifyGame(Game.builder().home("Mexico").away("Canada").score(1, 3).build()), atIndex(0));
    }

    @Test
    @DisplayName("Should throw an exception when updating score of non-existent game")
    void shouldThrowExceptionWhenUpdatingScoreOfNonExistentGame() {
        // given
        ScoreBoard scoreBoard = new InMemoryScoreBoard();

        // when
        Throwable throwable = catchThrowable(() -> scoreBoard.updateScore(id("Spain", "Brazil"), score(1, 3)));

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
        assertThat(summary).satisfies(verifyGame(URUGUAY_ITALY), atIndex(0));
        assertThat(summary).satisfies(verifyGame(SPAIN_BRAZIL), atIndex(1));
        assertThat(summary).satisfies(verifyGame(MEXICO_CANADA), atIndex(2));
        assertThat(summary).satisfies(verifyGame(ARGENTINA_AUSTRALIA), atIndex(3));
        assertThat(summary).satisfies(verifyGame(GERMANY_FRANCE), atIndex(4));
    }

    Consumer<? super Game> verifyGame(Game expected) {
        return actual -> {
            assertThat(actual.home().name()).isEqualTo(expected.home().name());
            assertThat(actual.away().name()).isEqualTo(expected.away().name());
            assertThat(actual.score()).isEqualTo(expected.score());
        };
    }
}
