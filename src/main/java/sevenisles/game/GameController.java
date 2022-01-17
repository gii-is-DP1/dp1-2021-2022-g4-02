package sevenisles.game;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;


@Controller
public class GameController {
	
	
	private CardService cardService;
	
	private GameService gameService;
	
	private PlayerService playerService;
	
	private StatusService statusService;
	
	private final String VIEW_CHOOSE_GAME_MODE = "games/gameMode";
	
	@Autowired
	public GameController(CardService cardService, GameService gameService, PlayerService playerService, StatusService statusService) {
		this.cardService = cardService;
		this.gameService = gameService;
		this.playerService = playerService;
		this.statusService = statusService;
	}
	
	@GetMapping(value = "/games")
	public String gamesList(ModelMap modelMap) {
		String vista = "games/gamesList";
		Iterable<Game> games = gameService.gameFindAll();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(value = "/games/availableGames")
	public String availableGamesList(ModelMap modelMap, HttpServletResponse response) {
		response.addHeader("Refresh", "5");
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
	
	@GetMapping(value = "/games/startedGame")
	public String startedGame(ModelMap modelMap) throws GameControllerException{
		String vista = "games/startedGame";
		Optional<List<Status>> opt = statusService.findStatusOfPlayer(playerService.findCurrentPlayer().get().getId());
		if(opt.isPresent()) {
			List<Status> statuses = opt.get();
			Optional<Status> status = statuses.stream().filter(s->s.getScore()==null).findFirst();
			if(status.isPresent()) {
				modelMap.addAttribute("game", status.get().getGame());
				return vista;
			}else{
				throw new GameControllerException("No tienes ninguna partida empezada.");
			}
		}else{
			throw new GameControllerException("No has jugado ninguna partida.");
		}
	}
	
	@GetMapping(value = "/games/{code}")
	public String gameDetailsByCode(ModelMap modelMap, @PathVariable("code") String code, HttpServletResponse response) throws GameControllerException{
		response.addHeader("Refresh", "5");
		String vista = "games/gameDetails";
		//String vistaError = "error";
		Optional<Game> game = gameService.findGameByCode(code);
		if(game.isPresent()) {
			modelMap.addAttribute("game", game.get());
			return vista;
		}else {
			throw new GameControllerException("Partida no encontrada");
		}
	}
	
	//Tablero
	@GetMapping(value = "/games/{code}/board")
	public String gameBoardByCode(ModelMap modelMap, @PathVariable("code") String code, HttpServletResponse response) throws GameControllerException{
		String vista = "games/board";
		//String vistaError = "error";
		Optional<Game> game = gameService.findGameByCode(code);
		if(game.isPresent()) {
			if(gameService.loggedUserBelongsToGame(game.get())) {
				if(game.get().getStatus().get(game.get().getCurrentPlayer()).getPlayer().equals(playerService.findCurrentPlayer().get())) {
					response.addHeader("Refresh", "12");
				}else response.addHeader("Refresh", "7");
				modelMap.addAttribute("game", game.get());
				gameService.utilAttributes(game.get(), modelMap);
				return vista;
    		}else {
    			throw new GameControllerException("No perteneces a esta partida.");
    		}
		}else {
			throw new GameControllerException("Partida no encontrada");
		}
	}	
	

	/* CREACIÓN DE LA PARTIDA   */
	
    @GetMapping(value = "/games/create")
    public String initCreateGame(ModelMap modelMap) throws GameControllerException{
    	Game game = new Game();
    	if(playerService.findCurrentPlayer().isPresent()) {
    		Player player = playerService.findCurrentPlayer().get();
    		if(!statusService.isInAnotherGame(player)) {
    			gameService.createGame(game, player);
    			modelMap.addAttribute("game", game);
	        	return VIEW_CHOOSE_GAME_MODE;
    		}else{
    			throw new GameControllerException("Ya estás dentro de una partida.");
    		}
    	}else {
    		throw new GameControllerException("Necesitas estar logueado como jugador para crear una partida.");
    	}

    }
    
    @PostMapping(value = "/games/create")
    public String processCreateGame(@Valid Game game, BindingResult result) throws GameControllerException{
    	if (result.hasErrors()) {
			return VIEW_CHOOSE_GAME_MODE;
		}
		else {
			
			Game gameToUpdate = this.gameService.findGameById(game.getId()).get();
			gameToUpdate.setGameMode(game.getGameMode());
			this.gameService.saveGame(gameToUpdate);
			
			return "redirect:/games/" + gameToUpdate.getCode();
		}

    } 

    @GetMapping(value = "/games/{code}/enter")
    public String enterGame(
    		@PathVariable("code") String code, ModelMap model) throws GameControllerException {
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		Player player = gameService.enterGameUtil(game);
    		if(!statusService.isInAnotherGame(player)) {
    			if(game.getStartHour()==null) {
    				if(game.getEndHour()==null) {
    					gameService.enterGame(game, player);
            			return "redirect:/games/{code}";
    				}else {
        				throw new GameControllerException("La partida ya ha terminado.");
        			}
    			}else {
    				throw new GameControllerException("La partida ya ha empezado.");
    			}
    		}else {
    			throw new GameControllerException("Ya estás dentro de una partida.");
    		}	
    	}else {
    		throw new GameControllerException("Lo sentimos, pero dicha partida no existe.");
    	}
    }
    //Hay que crear vista del tablero, para volver si sales de la partida
    
    @GetMapping(value = "/games/{code}/start")
    public String startGame(
    		@PathVariable("code") String code, ModelMap model) throws GameControllerException{
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedUserBelongsToGame(game)) {
    			if(statusService.isReadyToStart(game.getId())) {
        			gameService.startGame(game);
            		return "redirect:/games/{code}/board";
        		}else {
        			throw new GameControllerException("Necesitas al menos 2 jugadores para empezar.");
        		}
    		}else {
    			throw new GameControllerException("No perteneces a esta partida.");

    		}   		
    	}else {
    		throw new GameControllerException("Partida no encontrada");
    	}
    }
    
    //Lanzar dado
    @GetMapping(value = "/games/{code}/dice")
    public String playerThrowDice(
    		@PathVariable("code") String code, ModelMap model) throws GameControllerException{
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedPlayerCheckTurn(game)) {
    			Status status = game.getStatus().get(game.getCurrentPlayer());
    			if(status.getDiceNumber()==null) {
        			gameService.playerThrowDice(game);	
                	return "redirect:/games/{code}/board";
        		}else {
        			throw new GameControllerException("Ya has tirado el dado este turno.");
            	}
    		}else {
        		return "";
        	}    				       		
    	}else {
    		throw new GameControllerException("Partida no encontrada");
        }	
    }
    
    //Saquear isla
    @GetMapping(value = "/games/{code}/robIsland/{islandId}")
    public String robIsland(
    		@PathVariable("code") String code, @PathVariable("islandId") Integer islandId, ModelMap model) throws GameControllerException{
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedPlayerCheckTurn(game)) {
    			Status status = game.getStatus().get(game.getCurrentPlayer());
    			if(gameService.chooseIslandCondition(game, islandId, status)) {
    				if(status.getNumberOfCardsToPay()==null) {
    					gameService.chooseIsland(game, islandId, status);
    					return "redirect:/games/{code}/robIsland/{islandId}/payCard";					
    				}else {
    					return "redirect:/games/{code}/robIsland/{islandId}/payCard";
    				}		
    			}else {
    				return "exception";
    			}   				       			
    		}else {
    			return "exception";
    		}   				 	
    	}else {
    		throw new GameControllerException("Partida no encontrada");
    	}
    }

    
    //Pagar carta
	@GetMapping(value = "games/{code}/robIsland/{islandId}/payCard")
	public String initPayCardForm(@PathVariable("code") String code,@PathVariable("islandId") Integer islandId,
			ModelMap model) throws GameControllerException{	
		Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
			if(gameService.loggedPlayerCheckTurn(game)){
				Status status = game.getStatus().get(game.getCurrentPlayer());
				System.out.println(status + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		if(status.getNumberOfCardsToPay()==0) {
        			gameService.robIsland(game, islandId, status);
        			gameService.nextTurn(game);
					if(game.getCurrentRound()==game.getMaxRounds()+1 && game.getCards().isEmpty()) {
						return "redirect:/games/{code}/endGame";
					}else return "redirect:/games/{code}/board";
        			//return "redirect:../../board";
        		}else {
        			model.addAttribute("game", game);
        			model.addAttribute("status", status);
        			gameService.utilAttributes(game, model);
        			return "games/payCard";
        		}
			}else {
				return "error";
			}
		}else {
			throw new GameControllerException("Partida no encontrada!");
		}	
	}
	
	@GetMapping(value = "games/{code}/robIsland/{islandId}/payCard/{cardId}")
	public String processPayCardForm(@PathVariable("code") String code,@PathVariable("islandId") Integer islandId,
			@PathVariable("cardId") Integer cardId, ModelMap model) throws GameControllerException {
		Optional<Card> cardopt = cardService.findCardById(cardId);
		if(cardopt.isPresent()) {
			Card card = cardopt.get();
			Game game = gameService.findGameByCode(code).get();
			Status status = game.getStatus().get(game.getCurrentPlayer());
			if(statusService.cardInHand(status,card)) {
				statusService.deleteCardFromHand(game, card);
				if(status.getNumberOfCardsToPay()>=1) {
					return "redirect:";
				}else {
					gameService.robIsland(game, islandId, status);
					//4 líneas de pasar turno
					gameService.nextTurn(game);
					if(game.getCurrentRound()==game.getMaxRounds()+1 && game.getCards().isEmpty()) {
						return "redirect:/games/{code}/endGame";
					}else return "redirect:/games/{code}/board";
					//return "redirect:/games/{code}/board";
				}
			}else {
				throw new GameControllerException("No posees esa carta. Elige otra");
			}			
		}else {
			throw new GameControllerException("Carta no encontrada.");
		}
	}
    
//    //Pasar turno
//    @GetMapping(value = "/games/{code}/turn")
//    public String nextTurn(
//    		@PathVariable("code") String code, ModelMap model) throws GameControllerException{
//    	Optional<Game> optGame = gameService.findGameByCode(code);
//    	if(optGame.isPresent()) {
//    		Game game = optGame.get();
//    		if(gameService.loggedPlayerCheckTurn(game)) {
//    				if(game.getFinishedTurn()==1) {
//    					gameService.nextTurn(game);
//    					if(game.getCurrentRound()==game.getMaxRounds()+1 && game.getCards().isEmpty()) {
//    						return "redirect:/games/{code}/endGame";
//    					}else return "redirect:/games/{code}/board";
//        			}else {
//        				throw new GameControllerException("No has acabado el turno.");
//            		} 
//    				
//    			}else {
//        			return "error";
//        		}    				       			
//    	}else {
//    		throw new GameControllerException("Partida no encontrada");
//        }	
//    }
    
    @GetMapping(value = "/games/{code}/endGame")
    public String finishGame(
    		@PathVariable("code") String code, ModelMap model) throws GameControllerException{
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedUserBelongsToGame(game)) {
    				if(game.getCurrentRound()==game.getMaxRounds()+1 && game.getCards().isEmpty()) {
    					gameService.endGame(game);
    					List<Status> orderedStatuses = gameService.orderStatusByScore(game.getStatus());
    					List<Status> winners = statusService.findWinnerStatusByGame(game.getId()).get();
    					model.put("game", game);
    					model.put("number", winners.size());
    					model.put("ranking", orderedStatuses);
    					model.put("winners", winners);
        				return "games/scoreBoard";
        			}else {
        				throw new GameControllerException("Aún no se puede terminar la partida");
            		} 
    				
    			}else {
    				throw new GameControllerException("No perteneces a la partida");
        		}    				       			
    	}else {
    		throw new GameControllerException("Partida no encontrada");
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
