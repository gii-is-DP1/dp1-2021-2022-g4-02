package sevenisles.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import sevenisles.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authService;
	
	@Test
	public void testCountWithInitialData() {
        int count = playerService.playerCount();
        assertEquals(5,count);
    }
	
	@Test
	public void testFindAll() {
		Integer count = playerService.playerCount();
		List<Player> players =(List<Player>) playerService.playerFindAll();
		assertEquals(count,players.size());
	}
	
	@Test
	public void testFindPlayerById() {
		/* Sabemos que el id= 2 es el player2, con username = player2*/
		int id = 2;
		Player player = playerService.findPlayerById(id).get();
		assertEquals("player2",player.getUser().getUsername());
	}
	

	@Test
	public void testFindPlayerByUsername() {
		/* Sabemos que el id= 2 es el player2, con username = player2*/
		String name = "player2";
		Player player = playerService.findPlayerByUsername(name).get();
		assertEquals("player2",player.getUser().getUsername());
	}
	
	
	@Test
	public void TestSavePlayer() {
		Player player = new Player();
		
		int countBefore = playerService.playerCount();
		
		playerService.savePlayer(player);
        int countAfter = playerService.playerCount();
		
	
		assertEquals(countBefore+1, countAfter);
		
		playerService.deletePlayer(player.getId());
		
	}
	
	@Test
	public void testDeletePlayer() {
		Player player = new Player();
		playerService.savePlayer(player);
		int countBefore = playerService.playerCount();
		
		playerService.deletePlayer(player.getId());;
		int countAfter = playerService.playerCount();
		
		assertEquals(countAfter,countBefore-1);
	}
	
	@BeforeEach
	public void init(){
		User user = new User();
		user.setFirstName("prueba");
		user.setLastName("prueba");
		user.setUsername("username");
		user.setPassword("password");
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		auth.setUser(user);
		authService.saveAuthorities(auth);
		
		Player player = new Player();
		player.setUser(user);
		playerService.savePlayer(player);
		user.setPlayer(player);
		user.setAuthorities(auth);
		userService.saveUser(user);
	}
	
	@Test
	@WithMockUser(username="username")
	public void testFindCurrentPlayer() {
		
		Optional<Player> p = playerService.findCurrentPlayer();
		assertEquals("username", p.get().getUser().getUsername());
		
	}
	
	@Test
	public void testFindCurrentPlayerNotLogged() {
		
		Optional<Player> p = playerService.findCurrentPlayer();
		assertFalse(p.isPresent());
		
	}
}
