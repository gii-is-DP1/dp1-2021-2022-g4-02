package sevenisles.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.card.CardService;
import sevenisles.island.IslandService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.UserService;
import sevenisles.util.ThrowDice;


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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private IslandService islandService;
	
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
		Iterable<Game> games = gameService.findAvailableGames();
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
		String vistaError = "error";
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
	
    @GetMapping(value = "/games/create")
    public String createGame(ModelMap modelMap) {
    	Game game = new Game();
    	if(playerService.findCurrentPlayer().isPresent()) {
    		Player player = playerService.findCurrentPlayer().get();
    		if(!statusService.isInAnotherGame(player)) {	        	
        		Status status = new Status();
        		statusService.addPlayer(status, game, player);
        		statusService.addStatus(status, game);
        		statusService.addStatus(status, player);
        		this.gameService.saveGame(game);
	        	return "redirect:/games/" + game.getCode();
    		}else{
				modelMap.put("message", "Ya estás dentro de una partida.");
				return "error";
    		}
    	}else {
    		//Crear vista que nos informe del fallo
    		modelMap.addAttribute("message", "Necesitas estar logueado como jugador para crear una partida.");
			return "error";
    	}
//		model.put("game", game);
		//return "redirect:/games/code/"+game.getCode();

    } 

    @GetMapping(value = "/games/{code}/enter")
    public String enterGame(
    		@PathVariable("code") String code, ModelMap model) {
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(statusService.isNotFull(game.getId())) {
    			Optional<Player> optPlayer = playerService.findCurrentPlayer();
    			if(optPlayer.isPresent()) {
    				Player player = optPlayer.get();
    				if(!statusService.isInAnotherGame(player)) {
    					gameService.createGame(game, player);
	        			return "redirect:/games/{code}";
    				}else {
        				model.put("message", "Ya estás dentro de una partida.");
        				return "error";
    				}
    			}else {
    				model.put("message", "Necesitas iniciar sesión antes de unirte a una partida.");
    				return "error";
    			}
	    	}else {
				//throw new IllegalArgumentException("This game is already full.");
				model.put("message", "Lo sentimos, esta partida ya está llena");
				return "error";
			}	
		}else {
			model.put("message", "Lo sentimos, pero dicha partida no existe.");
			return "error";
			
		}
    }
    //Hay que crear vista del tablero, para volver si sales de la partida
    
    @GetMapping(value = "/games/{code}/start")
    public String startGame(
    		@PathVariable("code") String code, ModelMap model) {
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(statusService.isReadyToStart(game.getId())) {
    			gameService.startGame(game);
        		model.addAttribute("game",game);
        		return "games/board";
    		}else {
    			model.put("message", "Necesitas al menos 2 jugadores para empezar.");
    			return "error";
    		}	
    	}else {
    		model.put("message", "Partida no encontrada");
			return "error";
    	}
    }
    
//    //Esto estaría en todas las vistas
//    @ModelAttribute
    public void getPlayerTurnUsername(Game game, ModelMap model){
    	Integer pn = game.getCurrentPlayer();
    	Status status = game.getStatus().get(pn);
    	Integer playerUserId = status.getPlayer().getUser().getId();
    	Integer loggedUserId = userService.findCurrentUser().get().getId();
    	model.addAttribute("playerUserId", playerUserId);
    	model.addAttribute("loggedUserId", loggedUserId);
    	System.out.println(playerUserId);
    	System.out.println(loggedUserId);
    }
    
    
    @GetMapping(value = "/games/{code}/dice")
    public String playerThrowDice(
    		@PathVariable("code") String code, ModelMap model){
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		Integer number = ThrowDice.throwDice(6);
    		
    		List<Status> status = game.getStatus();
    		Status playerstatus = status.get(game.getCurrentPlayer());
    		playerstatus.setDiceNumber(number);
    		status.set(game.getCurrentPlayer(), playerstatus);
    		statusService.saveStatus(playerstatus);
    		game.setStatus(status);
    		gameService.saveGame(game);
    		getPlayerTurnUsername(game, model);
    		model.addAttribute("game",game);
    		model.addAttribute("number",number );	
    		return "games/board";
    		}else {
        		model.put("message", "Partida no encontrada");
    			return "error";
        	}
    	
    } 
    
    
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
