package sevenisles.player;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;

	@GetMapping(value = "/players")
	public String playersList(ModelMap modelMap) {
		String vista = "players/playersList";
		Iterable<Player> players = playerService.playerFindAll();
		modelMap.addAttribute("players", players);
		return vista;
	}
	
	
	@GetMapping(value = "/players/{playerId}")
	public String playersListById(ModelMap modelMap, @PathVariable("playerId") int playerId){
		String vista = "player/playerDetails";
		Player player = playerService.findPlayerById(playerId);
		modelMap.addAttribute("player", player);
		return vista;
	}

}
