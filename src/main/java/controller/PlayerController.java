package controller;

import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PlayerRepository;

import static controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;



    @GetMapping("/balance/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getBalanceOfPlayer(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        Long balance = playerRepository.getBalanceByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this username: " + username));

        return ResponseEntity.ok().body(balance);
    }
}
