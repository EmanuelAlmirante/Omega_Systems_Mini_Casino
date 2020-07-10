package service.player;

import exception.ResourceNotFoundException;
import model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public Player getByUsername(String username) throws ResourceNotFoundException {
        return playerRepository.getByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));
    }

    @Override
    public double getBalanceByUsername(String username) throws ResourceNotFoundException {
        return playerRepository.getBalanceByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));
    }

    @Override
    public void makeDeposit(String username, double amount) throws ResourceNotFoundException {
        if (amount < 0) {
            throw new IllegalArgumentException("The amount of the deposit must be bigger than 0!");
        } else {
            Player player = playerRepository.getByUsername(username).orElseThrow(
                    () -> new ResourceNotFoundException("Player not found for this username: " + username));

            double currentBalance = player.getBalance();
            double newBalance = currentBalance + amount;

            player.setBalance(newBalance);
            playerRepository.save(player);
        }
    }
}
