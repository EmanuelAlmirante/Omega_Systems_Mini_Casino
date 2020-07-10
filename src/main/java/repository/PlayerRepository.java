package repository;

import model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalDouble;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p from Player p where p.username = :username")
    Optional<Player> getByUsername(String username);

    @Query("select p.balance from Player p where p.username = :username")
    OptionalDouble getBalanceByUsername(String username);
}
