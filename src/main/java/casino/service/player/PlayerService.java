package casino.service.player;

import casino.model.Player;
import org.springframework.stereotype.Component;

@Component
public interface PlayerService {
    Player registerPlayer(Player player);

    Player getByUsername(String username);

    double getBalanceByUsername(String username);

    void makeDeposit(String username, double amount);

    void makeDebit(String username, double amount);
}
