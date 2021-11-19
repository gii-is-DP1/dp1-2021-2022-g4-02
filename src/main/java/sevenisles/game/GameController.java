package sevenisles.game;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.user.User;
import sevenisles.user.UserService;


@Controller
public class GameController {
	
	private static final String VIEWS_GAMES_CREATE_FORM = "games/create";
	private static final String VIEW_GAME_LOBBY = "games/lobby";
	
	@Autowired
	private UserService userService;
	
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
	
	@GetMapping(value = "/games/{code}")
	public String gameDetailsByCode(ModelMap modelMap, @PathVariable("code") String code){
		String vista = "games/gameDetails";
		Optional<Game> game = gameService.findGameByCode(code);
		if(game.isPresent()) {
			modelMap.addAttribute("game", game.get());
		}else {
			modelMap.addAttribute("message", "Partida no encontrada");
		}
		return vista;
	}	
	
	
	/* CREACIÓN DE LA PARTIDA   */
	
    @GetMapping(value = "/games/create")
    public String initCreateGameForm(Map<String, Object> model) {
    	Game game = new Game();
    	System.out.println(game.getCode());
    	if(playerService.findCurrentPlayer().isPresent()) {
    		game.addPlayer(playerService.findCurrentPlayer().get());
        	this.gameService.saveGame(game);
        	return "redirect:/games/" + game.getCode();
    	}else {
    		//Crear vista que nos informe del fallo
    		return "fallo";
    	}
//		model.put("game", game);
		//return "redirect:/games/code/"+game.getCode();

    } 
    
    
    /*  UNIRSE A PARTIDA   */
//    @GetMapping(value = "/games/{code}/enter")
//    public String initEnterGameForm(@PathVariable("code") String code, Model model) {
//    	
//    	Optional<Game> game = this.gameService.findGameByCode(code);
//    	if(game.isPresent()) {
//    		model.addAttribute(game.get());
//    	
//    	}else {
//    		model.addAttribute("message", "El código introducido no coincide con ninguna partida creada");
//    	}
//    	return VIEW_GAME_LOBBY;
//    }

    @GetMapping(value = "/games/{code}/enter")
    public String processEnterGameForm(
    		@PathVariable("code") String code, ModelMap model) {
//    	if (result.hasErrors()) {
//    		return VIEW_GAME_LOBBY;
//    	}
//    	else {
    	Optional<Game> game = gameService.findGameByCode(code);
    	if(game.isPresent()) {
    		if(game.get().isNotFull()) {
    			Optional<Player> player = playerService.findCurrentPlayer();
    			System.out.println(player.get()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    			if(player.isPresent()) {
    				game.get().addPlayer(player.get());
        			this.gameService.saveGame(game.get());
        			return "redirect:/games/{code}";
    			}else {
    				model.put("message", "Jugador no encontrado");
    				return "redirect:/games/{code}";
    			}
    	}else {
			throw new IllegalArgumentException("This game is already full.");
		}	
    		}else {
    			model.put("message", "Jugador no encontrado");
    			return "redirect:/games/{code}";
    			
    		}
//    	}
    }
    
//    @GetMapping(value = "/games/{gameId}/edit")
//    public String initUpdateGameForm(@PathVariable("gameId") int gameId, Model model) {
//    	Game game = this.gameService.findGameById(gameId);
//    	model.addAttribute(game);
//    	return VIEWS_GAMES_UPDATE_FORM;
//    }
//
//    @PostMapping(value = "/games/{gameId}/edit")
//    public String processUpdateGameForm(@Valid Game game, BindingResult result,
//    		@PathVariable("gameId") int gameId) {
//    	if (result.hasErrors()) {
//    		return VIEWS_GAMES_UPDATE_FORM;
//    	}
//    	else {
//    		game.setId(gameId);
//    		this.gameService.saveGame(game);
//    		return "redirect:/games/{gameId}";
//    	}
//    }
    
    
	@GetMapping(value = "/games/searchGame")
	public String searchByCodeView(ModelMap modelMap) {
		String vista = "games/searchGame";
		return vista;
	}
    

}
