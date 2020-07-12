package casino.service.game;

import casino.exception.BusinessException;
import casino.exception.ResourceNotFoundException;
import casino.model.Game;
import casino.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(Game game) {
        verifyGameBusinessRules(game);

        return gameRepository.save(game);
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game not found for this id: " + id));
    }

    private void verifyGameBusinessRules(Game game) {
        if (0 > game.getChanceOfWinning().doubleValue() || game.getChanceOfWinning().doubleValue() > 1) {
            throw new BusinessException("The chance of winning must be between 0 and 1!");
        }

        if (game.getChanceOfWinning().scale() != 2) {
            throw new BusinessException("The chance of winning must have two decimals!");
        }

        if (game.getWinningMultiplier() <= 0) {
            throw new BusinessException("The winning multiplier must be bigger than 0!");
        }

        if (game.getMaxBet() < 0) {
            throw new BusinessException("The max bet must be bigger than 0!");
        }

        if (game.getMaxBet() < game.getMinBet()) {
            throw new BusinessException("The max bet must be bigger than the min bet!");
        }

        if (game.getMinBet() < 0) {
            throw new BusinessException("The min bet must be bigger than 0!");
        }

        if (game.getMinBet() > game.getMaxBet()) {
            throw new BusinessException("The min bet must be smaller than the max bet!");
        }
    }
}
