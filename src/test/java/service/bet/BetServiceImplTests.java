package service.bet;

import casino.exception.BusinessException;
import casino.model.Bet;
import casino.model.Game;
import casino.model.Player;
import casino.repository.GameRepository;
import casino.repository.PlayerRepository;
import casino.service.bet.BetServiceImpl;
import casino.service.game.GameServiceImpl;
import casino.service.player.PlayerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BetServiceImplTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    private BetServiceImpl betServiceImpl;

    Player player;

    Game game;

    @Before
    public void setUp() {
        betServiceImpl = new BetServiceImpl(gameServiceImpl, playerServiceImpl);

        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        player = Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                               .build();
        double depositAmount = 50;
        player.setBalance(depositAmount);
        playerServiceImpl.registerPlayer(player);

        game = Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                           .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();
        gameServiceImpl.createGame(game);
    }

    @Test
    public void makeBetSuccess() {
        // Arrange
        Bet bet = Bet.Builder.betWith().withGameId(1L).withUsername("jonh_omega").withBetValue(15).build();

        // Act
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        Mockito.when(playerRepository.getByUsername(player.getUsername())).thenReturn(Optional.ofNullable(player));

        double initialBalance = player.getBalance();

        String betResult = betServiceImpl.makeBet(bet);

        double valueWon = bet.getBetValue() * game.getWinningMultiplier() - bet.getBetValue();
        double newBalanceIfWon = initialBalance + valueWon + bet.getBetValue();
        double valueLost = bet.getBetValue();
        double newBalanceIfLost = initialBalance - valueLost;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Assert
        assertNotNull(betResult);
        assertThat(betResult, anyOf(is("You won " + decimalFormat.format(valueWon) + "! \n" + "New balance: " +
                                               decimalFormat.format(newBalanceIfWon)),
                                    is("You lost " + decimalFormat.format(valueLost) + "! \n" + "New balance: " +
                                               decimalFormat.format(newBalanceIfLost))));
        assertThat(decimalFormat.format(player.getBalance()),
                   anyOf(is(decimalFormat.format(newBalanceIfWon)), is(decimalFormat.format(newBalanceIfLost))));
    }

    @Test(expected = BusinessException.class)
    public void makeBetPlayerNotEnoughBalanceFail() {
        // Arrange
        Bet bet = Bet.Builder.betWith().withGameId(1L).withUsername("jonh_omega").withBetValue(100).build();

        // Act
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        Mockito.when(playerRepository.getByUsername(player.getUsername())).thenReturn(Optional.ofNullable(player));

        betServiceImpl.makeBet(bet);
    }

    @Test(expected = BusinessException.class)
    public void makeBetValueSmallerThanGameMinBetFail() {
        // Arrange
        Bet bet = Bet.Builder.betWith().withGameId(1L).withUsername("jonh_omega").withBetValue(1).build();

        // Act
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        Mockito.when(playerRepository.getByUsername(player.getUsername())).thenReturn(Optional.ofNullable(player));

        betServiceImpl.makeBet(bet);
    }

    @Test(expected = BusinessException.class)
    public void makeBetValueBiggerThanGameMaxBetFail() {
        // Arrange
        Bet bet = Bet.Builder.betWith().withGameId(1L).withUsername("jonh_omega").withBetValue(1000).build();

        // Act
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        Mockito.when(playerRepository.getByUsername(player.getUsername())).thenReturn(Optional.ofNullable(player));

        betServiceImpl.makeBet(bet);
    }
}
