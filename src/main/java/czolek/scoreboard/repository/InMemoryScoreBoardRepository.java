package czolek.scoreboard.repository;

import czolek.scoreboard.data.Game;
import czolek.scoreboard.data.GameId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryScoreBoardRepository implements ScoreBoardRepository {
    private final Map<GameId, Game> matches = new ConcurrentHashMap<>();

    @Override
    public Game save(Game game) {
        return matches.put(game.id(), game);
    }

    @Override
    public List<Game> findAll() {
        return List.copyOf(matches.values());
    }

    @Override
    public Optional<Game> findById(GameId id) {
        return Optional.ofNullable(matches.get(id));
    }

    @Override
    public Game deleteById(GameId id) {
        return matches.remove(id);
    }
}
