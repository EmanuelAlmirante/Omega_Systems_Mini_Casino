package service.player;

import exception.ResourceNotFoundException;
import model.Player;

public interface PlayerService {
    Player getByUsername(String username) throws ResourceNotFoundException;

    double getBalanceByUsername(String username) throws ResourceNotFoundException;

    void makeDeposit(String username, double amount) throws ResourceNotFoundException;
}
