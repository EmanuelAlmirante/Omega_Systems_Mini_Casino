package service.game;

import exception.ResourceNotFoundException;
import model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game getGameById(Long id) throws ResourceNotFoundException {
        return gameRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game not found for this id: " + id));
    }
}
