package sevenisles.game;

import java.time.LocalTime; 
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.card.CardService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;


@Controller
public class GameController {
	
	private static final String VIEWS_GAMES_CREATE_FORM = "games/create";
	private static final String VIEW_GAME_LOBBY = "games/lobby";
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	@GetMapping(value = "/games")
	public String gamesList(ModelMap modelMap) {
		String vista = "games/gamesList";
		Iterable<Game> games = gameService.gameFindAll();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(value = "/games/availableGames")
	public String availableGamesList(ModelMap modelMap) {
		String vista = "games/availableGames";
		Iterable<Game> games = gameService.findNotStartedGames();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(value = "/games/startedGames")
	public String startedGamesList(ModelMap modelMap) {
		String vista = "games/startedGames";
		Iterable<Game> games = gameService.findStartedGames();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(value = "/games/{code}")
	public String gameDetailsByCode(ModelMap modelMap, @PathVariable("code") String code){
		String vista = "games/gameDetails";
		String vistaError = "games/gameErrorScreen";
		Optional<Game> game = gameService.findGameByCode(code);
		if(game.isPresent()) {
			modelMap.addAttribute("game", game.get());
			return vista;
		}else {
			modelMap.addAttribute("message", "Partida no encontrada");
			return vistaError;
		}
	}	
	
	
	/* CREACIÓN DE LA PARTIDA   */
	
//    @GetMapping(value = "/games/create")
//    public String initCreateGameForm(ModelMap modelMap) {
//    	Game game = new Game();
//    	if(playerService.findCurrentPlayer().isPresent()) {
//    		Player player = playerService.findCurrentPlayer().get();
//    		if(player.getGame()==null) {
//	    		game.addPlayer(player);
//	    		player.setGame(game);
//	    		game.setStartHour(LocalTime.now());
//	    		game.setCards(cardService.llenarMazo());
//	        	this.gameService.saveGame(game);
//	        	return "redirect:/games/" + game.getCode();
//    		}else{
//				modelMap.put("message", "Ya estás dentro de una partida.");
//				return "games/gameErrorScreen";
//    		}
//    	}else {
//    		//Crear vista que nos informe del fallo
//    		modelMap.addAttribute("message", "Necesitas estar logueado como jugador para crear una partida.");
//			return "games/gameErrorScreen";
//    	}
////		model.put("game", game);
//		//return "redirect:/games/code/"+game.getCode();
//
//    } 
//
//    @GetMapping(value = "/games/{code}/enter")
//    public String processEnterGameForm(
//    		@PathVariable("code") String code, ModelMap model) {
//    	Optional<Game> optGame = gameService.findGameByCode(code);
//    	if(optGame.isPresent()) {
//    		Game game = optGame.get();
//    		if(optGame.get().isNotFull()) {
//    			Optional<Player> optPlayer = playerService.findCurrentPlayer();
//    			if(optPlayer.isPresent()) {
//    				Player player = optPlayer.get();
//    				if(player.getGame()==null) {
//    					player.setGame(game);
//	    				game.addPlayer(player);
//	        			this.gameService.saveGame(game);
//	        			return "redirect:/games/{code}";
//    				}else {
//        				model.put("message", "Ya estás dentro de una partida.");
//        				return "games/gameErrorScreen";
//    				}
//    			}else {
//    				model.put("message", "Necesitas iniciar sesión antes de unirte a una partida.");
//    				return "games/gameErrorScreen";
//    			}
//	    	}else {
//				//throw new IllegalArgumentException("This game is already full.");
//				model.put("message", "Lo sentimos, esta partida ya está llena");
//				return "games/gameErrorScreen";
//			}	
//		}else {
//			model.put("message", "Lo sentimos, pero dicha partida no existe.");
//			return "games/gameErrorScreen";
//			
//		}
//    }
    
	@GetMapping(value = "/games/searchGame")
	public String searchByCodeView(ModelMap modelMap) {
		String vista = "games/searchGame";
		return vista;
	}
    
	@PostMapping(value = "/games/searchGame")
	public String searchByCodeView(String code, ModelMap modelMap) {
		String vista = "games/"+code+"/enter";
		return "redirect:/"+vista;
	}
	
	@GetMapping(value = "/rules")
	public String rulesView(ModelMap modelMap) {
		String vista = "games/gameRules";
		return vista;
	}

}
