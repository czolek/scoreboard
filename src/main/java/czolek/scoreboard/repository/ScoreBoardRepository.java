package czolek.scoreboard.repository;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.GameId;

import java.util.List;
import java.util.Optional;

public interface ScoreBoardRepository {
    Game save(Game game);

    List<Game> findAll();

    Optional<Game> findById(GameId id);

    Game deleteById(GameId id);
}
