package service.game;

import exception.ResourceNotFoundException;
import model.Game;

public interface GameService {

    Game getGameById(Long id) throws ResourceNotFoundException;
}
