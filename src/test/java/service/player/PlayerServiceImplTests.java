package service.player;

import casino.exception.BusinessException;
import casino.exception.ResourceNotFoundException;
import casino.model.Player;
import casino.repository.PlayerRepository;
import casino.service.player.PlayerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImplTests {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    public void registerPlayerSuccessfully() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Assert
        assertNotNull(playerRegistered);
        assertEquals(playerRegistered.getId(), playerToRegister.getId());
        assertEquals(playerRegistered.getName(), playerToRegister.getName());
        assertEquals(playerRegistered.getUsername(), playerToRegister.getUsername());
        assertEquals(playerRegistered.getBirthdate(), playerToRegister.getBirthdate());
        assertEquals(playerToRegister.getBalance(), playerRegistered.getBalance(), 0);
    }

    @Test(expected = BusinessException.class)
    public void registerPlayerWithExistingUsernameFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegisterOriginal =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        Player playerToRegisterExistingUsername =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        Mockito.when(playerRepository.getByUsername(playerToRegisterExistingUsername.getUsername())).thenReturn(
                Optional.of(playerToRegisterOriginal));

        playerServiceImpl.registerPlayer(playerToRegisterOriginal);
        playerServiceImpl.registerPlayer(playerToRegisterExistingUsername);
    }

    @Test(expected = BusinessException.class)
    public void registerPlayerYoungerThanEighteenFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2020, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        playerServiceImpl.registerPlayer(playerToRegister);
    }

    @Test
    public void getPlayerByUsernameSuccess() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Act
        Mockito.when(playerRepository.getByUsername(playerToRegister.getUsername())).thenReturn(
                Optional.of(playerRegistered));

        Player playerByUsername = playerServiceImpl.getByUsername(playerToRegister.getUsername());

        // Assert
        assertNotNull(playerByUsername);
        assertEquals(playerByUsername.getId(), playerRegistered.getId());
        assertEquals(playerByUsername.getName(), playerRegistered.getName());
        assertEquals(playerByUsername.getUsername(), playerRegistered.getUsername());
        assertEquals(playerByUsername.getBirthdate(), playerRegistered.getBirthdate());
        assertEquals(playerRegistered.getBalance(), playerByUsername.getBalance(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPlayerByUsernameFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        playerServiceImpl.getByUsername(playerToRegister.getUsername());
    }

    @Test
    public void getPlayerBalanceSuccess() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Act
        Mockito.when(playerRepository.getByUsername(playerRegistered.getUsername())).thenReturn(
                Optional.of(playerRegistered));

        double balance = playerServiceImpl.getBalanceByUsername(playerRegistered.getUsername());

        // Assert
        assertEquals(balance, playerRegistered.getBalance(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getNonExistingPlayerBalanceFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        playerServiceImpl.getBalanceByUsername(playerToRegister.getUsername());
    }

    @Test
    public void makePlayerDepositSuccess() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Act
        Mockito.when(playerRepository.getByUsername(playerRegistered.getUsername())).thenReturn(
                Optional.of(playerRegistered));

        double depositAmount = 1000;

        playerServiceImpl.makeDeposit(playerRegistered.getUsername(), depositAmount);

        // Assert
        assertEquals(depositAmount, playerRegistered.getBalance(), 0);
    }

    @Test(expected = BusinessException.class)
    public void makePlayerDepositSmallerThanZeroFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Act
        double depositAmount = -1000;

        playerServiceImpl.makeDeposit(playerRegistered.getUsername(), depositAmount);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void makeNonExistingPlayerDepositFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        // Act
        double depositAmount = 1000;

        playerServiceImpl.makeDeposit(playerToRegister.getUsername(), depositAmount);
    }

    @Test
    public void makePlayerDebitSuccess() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        double depositAmount = 1000;

        playerToRegister.setBalance(depositAmount);

        Mockito.when(playerRepository.save(playerToRegister)).thenReturn(playerToRegister);

        Player playerRegistered = playerServiceImpl.registerPlayer(playerToRegister);

        // Act
        Mockito.when(playerRepository.getByUsername(playerRegistered.getUsername())).thenReturn(
                Optional.of(playerRegistered));

        double debitAmount = 500;

        playerServiceImpl.makeDebit(playerRegistered.getUsername(), debitAmount);

        // Assert
        assertEquals(depositAmount - debitAmount, playerRegistered.getBalance(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void makeNonExistingPlayerDebitFail() {
        // Arrange
        Calendar calendar = new GregorianCalendar(2002, Calendar.JULY, 11);
        Date birthDate = calendar.getTime();

        Player playerToRegister =
                Player.Builder.playerWith().withName("John").withUsername("jonh_omega").withBirthdate(birthDate)
                              .build();

        double depositAmount = 1000;

        playerToRegister.setBalance(depositAmount);

        // Act
        double debitAmount = 500;

        playerServiceImpl.makeDebit(playerToRegister.getUsername(), debitAmount);
    }
}
