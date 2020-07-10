package controller;

import exception.ResourceNotFoundException;
import model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.GameRepository;
import service.game.GameService;

import static controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    private GameRepository gameRepository;

    @GetMapping("{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getGameById(@PathVariable(value = "gameId") Long gameId)
            throws ResourceNotFoundException {

    }
}
