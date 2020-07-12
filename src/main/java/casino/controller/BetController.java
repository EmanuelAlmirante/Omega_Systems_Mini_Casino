package casino.controller;

import casino.model.Bet;
import casino.service.bet.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static casino.controller.RestEndpoint.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/bets")
public class BetController extends AbstractController {

    @Autowired
    private BetService betService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> makeBet(@Valid @RequestBody Bet bet) {
        String result = betService.makeBet(bet);

        return ResponseEntity.ok().body(result);
    }
}