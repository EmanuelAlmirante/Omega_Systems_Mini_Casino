package casino.service.player;

import casino.exception.BusinessException;
import casino.exception.ResourceNotFoundException;
import casino.model.Player;
import casino.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player registerPlayer(Player player) {
        verifyPlayerUsernameIsUnique(player);
        verifyAge(player);

        return playerRepository.save(player);
    }

    @Override
    public Player getByUsername(String username) {
        return playerRepository.getByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));
    }

    @Override
    public double getBalanceByUsername(String username) {
        checkPlayerExists(username);

        return playerRepository.getBalanceByUsername(username);
    }

    @Override
    public void makeDeposit(String username, double amount) {
        if (amount < 0) {
            throw new BusinessException("The amount of the deposit must be bigger than 0!");
        } else {
            Player player = playerRepository.getByUsername(username).orElseThrow(
                    () -> new ResourceNotFoundException("Player not found for this username: " + username));

            double currentBalance = player.getBalance();
            double newBalance = currentBalance + amount;

            player.setBalance(newBalance);
            playerRepository.save(player);
        }
    }

    @Override
    public void makeDebit(String username, double amount) {
        Player player = playerRepository.getByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));


        double newBalance = updateBalance(player, amount);

        player.setBalance(newBalance);
        playerRepository.save(player);
    }

    private void verifyPlayerUsernameIsUnique(Player player) {
        Player existsPlayer = playerRepository.getByUsername(player.getUsername()).orElse(null);

        if (existsPlayer != null) {
            throw new BusinessException("A player with this username already exists - " + existsPlayer.getUsername());
        }
    }

    private void verifyAge(Player player) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = player.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (Period.between(birthday, today).getYears() < 18) {
            throw new BusinessException("Player must be older than 18!");
        }
    }

    private void checkPlayerExists(String username) {
        playerRepository.getByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));
    }

    private double updateBalance(Player player, double amount) {
        if (player.getBalance() < amount) {
            throw new BusinessException("Player has not enough balance to make a bet of this value! Current balance: " +
                                                player.getBalance());
        }

        return player.getBalance() - amount;
    }
}
