package org.springframework.samples.petclinic.model.game;

import javax.validation.Valid; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class GameController {
	
	private static final String VIEWS_GAMES_CREATE_FORM = "games/createGame";
	private static final String VIEWS_GAMES_ENTER_FORM = "games/enterGame";
	
	@Autowired
	private GameService gameService;
	
	@GetMapping(value = "/games")
	public String gamesList(ModelMap modelMap) {
		String vista = "games/gamesList";
		Iterable<Game> games = gameService.gameFindAll();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(value = "/games/{gameId}")
	public String gamesListById(ModelMap modelMap, @PathVariable("gameId") int gameId){
		String vista = "game/gameDetails";
		Game game = gameService.findGameById(gameId);
		modelMap.addAttribute("game", game);
		return vista;
	}
	
    @GetMapping(value = "/games/create")
    public String initCreateGameForm(Model model) {
    	Game game = new Game();
    	model.addAttribute(game);
    	return VIEWS_GAMES_CREATE_FORM;
    }

    @PostMapping(value = "/games/create")
    public String processUpdateGameForm(@Valid Game game,BindingResult result, Player player) {
    	if (result.hasErrors()) {
    		return VIEWS_GAMES_CREATE_FORM;
    	}
    	else {
    		game.addPlayer(player);
    		this.gameService.saveGame(game);
    		return "redirect:/games/{gameId}";
    	}
    }
    
    @GetMapping(value = "/games/{gameId}/enter")
    public String initEnterGameForm(@PathVariable("gameId") int gameId, Model model, Player player) {
    	Game game = this.gameService.findGameById(gameId);
    	model.addAttribute(game);
    	return VIEWS_GAMES_ENTER_FORM;
    }

    @PostMapping(value = "/games/{gameId}/enter")
    public String processEnterGameForm(@Valid Game game, BindingResult result,
    		@PathVariable("gameId") int gameId, Player player) {
    	if (result.hasErrors()) {
    		return VIEWS_GAMES_ENTER_FORM;
    	}
    	else {
    		if(game.isNotFull()) {
    			game.addPlayer(player);
    			this.gameService.saveGame(game);
        		return "redirect:/games/{gameId}";
    		}else{
    			throw new IllegalArgumentException("This game is already full.");
    		}
    	}
    }
    
    /*@GetMapping(value = "/games/{gameId}/edit")
    public String initUpdateGameForm(@PathVariable("gameId") int gameId, Model model) {
    	Game game = this.gameService.findGameById(gameId);
    	model.addAttribute(game);
    	return VIEWS_GAMES_UPDATE_FORM;
    }

    @PostMapping(value = "/games/{gameId}/edit")
    public String processUpdateGameForm(@Valid Game game, BindingResult result,
    		@PathVariable("gameId") int gameId) {
    	if (result.hasErrors()) {
    		return VIEWS_GAMES_UPDATE_FORM;
    	}
    	else {
    		game.setId(gameId);
    		this.gameService.saveGame(game);
    		return "redirect:/games/{gameId}";
    	}
    }*/
    
}
