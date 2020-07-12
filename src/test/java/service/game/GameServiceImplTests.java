package service.game;

import casino.exception.BusinessException;
import casino.exception.ResourceNotFoundException;
import casino.model.Game;
import casino.repository.GameRepository;
import casino.service.game.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Test
    public void createGameSuccess() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();

        // Act
        Mockito.when(gameRepository.save(gameToCreate)).thenReturn(gameToCreate);

        Game gameCreated = gameServiceImpl.createGame(gameToCreate);

        // Assert
        assertNotNull(gameCreated);
        assertEquals(gameToCreate.getId(), gameCreated.getId());
        assertEquals(gameToCreate.getName(), gameCreated.getName());
        assertEquals(gameToCreate.getChanceOfWinning(), gameCreated.getChanceOfWinning());
        assertEquals(gameToCreate.getWinningMultiplier(), gameCreated.getWinningMultiplier(), 0);
        assertEquals(gameToCreate.getMaxBet(), gameCreated.getMaxBet(), 0);
        assertEquals(gameToCreate.getMinBet(), gameCreated.getMinBet(), 0);
    }

    @Test(expected = BusinessException.class)
    public void createGameChanceOfWinningNotBetweenZeroAndOneFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("2.64"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameChanceOfWinningWithoutTwoDecimalPlaces() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.6"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameWinningMultiplierNegativeFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(-1.21).withMaxBet(100).withMinBet(10).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameMaxBetNegativeFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(-100).withMinBet(10).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameMaxBetSmallerThanMinBetFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(10).withMinBet(100).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameMinBetNegativeFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(-10).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test(expected = BusinessException.class)
    public void createGameMinBetBiggerThanMaxBetFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(10).withMinBet(100).build();

        // Act
        gameServiceImpl.createGame(gameToCreate);
    }

    @Test
    public void getGameByIdSuccess() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();

        Mockito.when(gameRepository.findById(gameToCreate.getId())).thenReturn(Optional.of(gameToCreate));

        gameServiceImpl.createGame(gameToCreate);

        // Act
        Game gameById = gameServiceImpl.getGameById(gameToCreate.getId());

        // Assert
        assertNotNull(gameById);
        assertEquals(gameToCreate.getId(), gameById.getId());
        assertEquals(gameToCreate.getName(), gameById.getName());
        assertEquals(gameToCreate.getChanceOfWinning(), gameById.getChanceOfWinning());
        assertEquals(gameToCreate.getWinningMultiplier(), gameById.getWinningMultiplier(), 0);
        assertEquals(gameToCreate.getMaxBet(), gameById.getMaxBet(), 0);
        assertEquals(gameToCreate.getMinBet(), gameById.getMinBet(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getNonExistingGameByIdFail() {
        // Arrange
        Game gameToCreate =
                Game.Builder.gameWith().withId(1L).withName("Game_Test").withChanceOfWinning(new BigDecimal("0.64"))
                            .withWinningMultiplier(1.21).withMaxBet(100).withMinBet(10).build();

        // Act
        gameServiceImpl.getGameById(gameToCreate.getId());
    }
}
