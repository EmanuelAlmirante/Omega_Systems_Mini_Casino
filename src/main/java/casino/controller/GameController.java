package casino.controller;

import casino.model.Game;
import casino.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static casino.controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/games")
public class GameController extends AbstractController {

    @Autowired
    private GameService gameService;

    @GetMapping("{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getGameById(@PathVariable(value = "gameId") Long gameId) {
        Game game = gameService.getGameById(gameId);

        return ResponseEntity.ok().body(game);
    }
}
