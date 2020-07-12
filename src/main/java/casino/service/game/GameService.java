package casino.service.game;

import casino.model.Game;

public interface GameService {

    Game createGame(Game game);

    Game getGameById(Long id);
}
