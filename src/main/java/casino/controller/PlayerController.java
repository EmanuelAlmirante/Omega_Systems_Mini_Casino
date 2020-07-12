package casino.controller;

import casino.exception.ResourceNotFoundException;
import casino.model.Player;
import casino.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static casino.controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/players")
public class PlayerController extends AbstractController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Player registerPlayer(@Valid @RequestBody Player player) {
        return playerService.registerPlayer(player);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayerByUsername(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        Player player = playerService.getByUsername(username);

        return ResponseEntity.ok().body(player);
    }

    @GetMapping("/balance/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getBalanceOfPlayer(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        double balance = playerService.getBalanceByUsername(username);

        return ResponseEntity.ok().body(balance);
    }

    @PostMapping("/{username}/deposit/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> makeDeposit(@PathVariable(value = "username") String username,
                                            @PathVariable(value = "amount") double amount) {
        playerService.makeDeposit(username, amount);

        return ResponseEntity.noContent().build();
    }
}
