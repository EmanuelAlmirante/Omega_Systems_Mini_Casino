package casino.service.bet;

import casino.exception.BusinessException;
import casino.model.Bet;
import casino.model.Game;
import casino.model.Player;
import casino.service.game.GameService;
import casino.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class BetServiceImpl implements BetService {
    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public BetServiceImpl(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Override
    public String makeBet(Bet bet) {
        Game game = getGame(bet);
        Player player = getPlayer(bet);

        verifyValidityOfBet(bet, game, player);

        return hasWon(bet, game, player);
    }

    private Game getGame(Bet bet) {
        long gameId = bet.getGameId();

        return gameService.getGameById(gameId);
    }

    private Player getPlayer(Bet bet) {
        String username = bet.getUsername();

        return playerService.getByUsername(username);
    }

    private void verifyValidityOfBet(Bet bet, Game game, Player player) {
        double betValue = bet.getBetValue();

        if (player.getBalance() < betValue) {
            throw new BusinessException("Player has not enough balance to make a bet of this value! Current balance: " +
                                                player.getBalance());
        }

        if (game.getMinBet() > betValue) {
            throw new BusinessException("The value of the bet must be higher than " + game.getMinBet() + "!");
        }

        if (game.getMaxBet() < betValue) {
            throw new BusinessException("The value of the bet must be lower than " + game.getMaxBet() + "!");
        }
    }

    private String hasWon(Bet bet, Game game, Player player) {
        Random rand = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (rand.nextDouble() < game.getChanceOfWinning().doubleValue()) {
            creditPlayerBalance(bet, game, player);
            double valueWon = bet.getBetValue() * game.getWinningMultiplier() - bet.getBetValue();
            double newBalance = player.getBalance();

            return "You won " + decimalFormat.format(valueWon) + "! \n" + "New balance: " +
                    decimalFormat.format(newBalance);
        } else {
            debitPlayerBalance(bet, player);
            double valueLost = bet.getBetValue();
            double newBalance = player.getBalance();

            return "You lost " + decimalFormat.format(valueLost) + "! \n" + "New balance: " +
                    decimalFormat.format(newBalance);
        }
    }

    private void creditPlayerBalance(Bet bet, Game game, Player player) {
        String username = player.getUsername();
        double betValue = bet.getBetValue();
        double winningValue = betValue * game.getWinningMultiplier();

        playerService.makeDeposit(username, winningValue);
    }

    private void debitPlayerBalance(Bet bet, Player player) {
        String username = player.getUsername();
        double betValue = bet.getBetValue();

        playerService.makeDebit(username, betValue);
    }
}
