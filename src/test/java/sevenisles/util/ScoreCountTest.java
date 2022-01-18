package sevenisles.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import sevenisles.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ScoreCountTest {
	
	private GameService gameService;
	private PlayerService playerService;
	private CardService cardService;
	private StatusService statusService;
	private UserService userService;
	private AuthoritiesService authService;
	
	Game game;
	Integer playerId;
	List<Status> status;
	
	@BeforeEach
	public void init() {
        game = new Game();
        status = new ArrayList<Status>();
        List<Card> deck = new ArrayList<Card>();
        deck.add(cardService.findCardById(1).get());
        deck.add(cardService.findCardById(28).get());
        deck.add(cardService.findCardById(28).get());
        deck.add(cardService.findCardById(31).get());
        
        User user = new User();
        user.setUsername("test");
        user.setFirstName("Perico");
        user.setLastName("El de los palotes");
        user.setPassword("prueba");
        
        Authorities auth = new Authorities();
        auth.setAuthority("player");
        auth.setUser(user);
        authService.saveAuthorities(auth);
        user.setAuthorities(auth);
        
        Player player = new Player();
        player.setUser(user);
        playerId=player.getId();
        playerService.savePlayer(player);
        user.setPlayer(player);
        
        userService.saveUser(user);
        
        Status status1 = new Status();
        status1.setPlayer(player);
        status1.setGame(game);
        status1.setScore(5);
        statusService.saveStatus(status1);
        status.add(status1);
        
        game.setStatus(status);
        game.setCards(deck);
        gameService.saveGame(game);
	}
	
	@Test
	public void testNormalGameMode() {
		Map<String, List<Card>> map = gameService.normalGameMode(status.get(0));
		System.out.println("**********************************"+map);
		// TODO
		// El map resultante del normalGameMode deberia ser:
		// - Doblones 1
		// Lista 1 : 2
		// Lista 2 : 1
		// Total 5 puntos
	}

}
