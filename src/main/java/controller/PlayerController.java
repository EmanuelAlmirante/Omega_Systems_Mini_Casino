package controller;

import exception.ResourceNotFoundException;
import model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PlayerRepository;

import javax.validation.Valid;

import static controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Player registerPlayer(@Valid @RequestBody Player player) {
        return playerRepository.save(player);
    }

    @GetMapping("/balance/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getBalanceOfPlayer(@PathVariable(value = "username") String username) {

    }

    @PostMapping("/{username}/deposit/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> makeDeposit(@PathVariable(value = "username") String username,
                                            @PathVariable(value = "amount") double amount) {


    }


}
