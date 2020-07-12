package casino;

import casino.model.Game;
import casino.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private final GameService gameService;

    public DataLoader(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Game game = Game.Builder.gameWith().withId(1L).withName("Test").withChanceOfWinning(new BigDecimal("0.40"))
                                .withWinningMultiplier(1.34).withMaxBet(100).withMinBet(10).build();

        gameService.createGame(game);
    }
}
