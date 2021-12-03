package sevenisles.game;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.islandStatus.IslandStatusService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.User;
import sevenisles.util.ManualLogin;


@Controller
public class GameController {
	
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private IslandStatusService islandStatusService;
	
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
	
	@GetMapping(value = "/games/startedGame")
	public String startedGame(ModelMap modelMap) {
		String vista = "games/startedGame";
		Optional<List<Status>> opt = statusService.findStatusOfPlayer(playerService.findCurrentPlayer().get().getId());
		if(opt.isPresent()) {
			List<Status> statuses = opt.get();
			Optional<Status> status = statuses.stream().filter(s->s.getScore()==null).findFirst();
			if(status.isPresent()) {
				modelMap.addAttribute("game", status.get().getGame());
				return vista;
			}else{
				modelMap.addAttribute("message", "No tienes ninguna partida empezada.");
				return "error";
			}
		}else{
			modelMap.addAttribute("message", "No has jugado ninguna partida.");
			return "error";
		}
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
	
	//Tablero
	@GetMapping(value = "/games/{code}/board")
	public String gameBoardByCode(ModelMap modelMap, @PathVariable("code") String code){
		String vista = "games/board";
		String vistaError = "error";
		Optional<Game> game = gameService.findGameByCode(code);
		if(game.isPresent()) {
			if(gameService.loggedUserBelongsToGame(game.get())) {
				modelMap.addAttribute("game", game.get());
				gameService.utilAttributes(game.get(), modelMap);
				return vista;
    		}else {
    			modelMap.addAttribute("message", "No perteneces a esta partida.");
    			return vistaError;
    		}
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
    			gameService.createGame(game, player);
	        	return "redirect:/games/" + game.getCode();
    		}else{
				modelMap.put("message", "Ya estás dentro de una partida.");
				return "error";
    		}
    	}else {
    		modelMap.addAttribute("message", "Necesitas estar logueado como jugador para crear una partida.");
			return "error";
    	}

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
    					gameService.enterGame(game, player);
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
    		if(gameService.loggedUserBelongsToGame(game)) {
    			if(statusService.isReadyToStart(game.getId())) {
        			gameService.startGame(game);
            		return "redirect:/games/{code}/board";
        		}else {
        			model.put("message", "Necesitas al menos 2 jugadores para empezar.");
        			return "error";
        		}
    		}else {
    			model.put("message", "No perteneces a esta partida.");
    			return "error";
    		}   		
    	}else {
    		model.put("message", "Partida no encontrada");
			return "error";
    	}
    }
    
    //Lanzar dado
    @GetMapping(value = "/games/{code}/dice")
    public String playerThrowDice(
    		@PathVariable("code") String code, ModelMap model){
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedUserBelongsToGame(game)) {
    			Integer pn = game.getCurrentPlayer();
    			Status status = game.getStatus().get(pn);
    			if(status.getPlayer().getId()==playerService.findCurrentPlayer().get().getId()) {
    				if(status.getDiceNumber()==null) {
        				gameService.playerThrowDice(game);	
                		return "redirect:/games/{code}/board";
        			}else {
            			model.put("message", "Ya has tirado el dado este turno.");
            			return "error";
            		}
    			}else {
        			model.put("message", "No es tu turno.");
        			return "error";
        		}    				       		
    		}else {
    			model.put("message", "No perteneces a esta partida.");
    			return "error";
    		}	
    	}else {
        	model.put("message", "Partida no encontrada");
    		return "error";
        }	
    }
    
    //Saquear isla
    @GetMapping(value = "/games/{code}/robIsland/{islandId}")
    public String robIsland(
    		@PathVariable("code") String code, @PathVariable("islandId") Integer islandId, ModelMap model){
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedUserBelongsToGame(game)) {
    			Integer pn = game.getCurrentPlayer();
    			Status status = game.getStatus().get(pn);
    			if(status.getPlayer().getId()==playerService.findCurrentPlayer().get().getId()) {
    				if(game.getFinishedTurn()==0) {
    					if(status.getDiceNumber()!=null) {
    						Optional<IslandStatus> opt = islandStatusService.findIslandStatusByGameAndIsland(game.getId(), islandId);
        					if(status.getChosenIsland()==null || status.getChosenIsland()==islandId) {
        						if(opt.isPresent()) {
                					IslandStatus is = opt.get();
                					if(is.getCard()!=null) {
                						if(status.getNumberOfCardsToPay()==null) {
                							Integer cardsNumber = status.getCards().size();
                    						Integer difference = Math.abs(status.getDiceNumber()-islandId);
                    						if(difference<=cardsNumber) {
                    							status.setChosenIsland(islandId);
                        						status.setNumberOfCardsToPay(difference);
                        						statusService.saveStatus(status);
                    							return "redirect:/games/{code}/robIsland/{islandId}/payCard";
                    						}else {
                    							model.put("message", "No tienes suficientes cartas para pagar el saqueo de esta isla");
                        	        			return "error";
                    						}
                						}else {
                							return "redirect:/games/{code}/robIsland/{islandId}/payCard";
                						}
                								
                					}else {
                						model.put("message", "No puedes saquear una isla vacía");
                	        			return "error";
                					}			
                				}else {
                        			model.put("message", "No hay isla.");
                        			return "error";
                        		}
        					}else {
            					model.put("message", "Ya has elegido saquear la isla " + status.getChosenIsland() + ". No puedes cambiarla.");
                    			return "error";
            				}
    					}else {
        					model.put("message", "Primero tienes que tirar el dado.");
                			return "error";
        				}
	
    				}else {
    					model.put("message", "Ya has saqueado una isla.");
            			return "error";
    				}	
    			}else {
        			model.put("message", "No es tu turno.");
        			return "error";
        		}    				       		
    		}else {
    			model.put("message", "No perteneces a esta partida.");
    			return "error";
    		}	
    	}else {
        	model.put("message", "Partida no encontrada");
    		return "error";
        }	
    }
    
    //Pagar carta
	@GetMapping(value = "games/{code}/robIsland/{islandId}/payCard")
	public String initPayCardForm(@PathVariable("code") String code,@PathVariable("islandId") Integer islandId,
			ModelMap model) throws GameControllerException{	
		Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
			if(gameService.cond(game)){
				Status status = game.getStatus().get(game.getCurrentPlayer());
        		if(status.getCardsToPay()==0) {
        			gameService.robIsland(game, islandId, status);
        			return "redirect:../../board";
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
			model.addAttribute("message", "Partida no encontrada!");
			return "error";
		}	
	}
	
	@GetMapping(value = "games/{code}/robIsland/{islandId}/payCard/{cardId}")
	public String processPayCardForm(@PathVariable("code") String code,@PathVariable("islandId") Integer islandId,
			@PathVariable("cardId") Integer cardId, ModelMap model) {
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
					IslandStatus is = islandStatusService.findIslandStatusByGameAndIsland(game.getId(), islandId).get();
					gameService.robIsland(game, is, status);
					game.setFinishedTurn(1);
					gameService.saveGame(game);
					status.setNumberOfCardsToPay(null);
					statusService.saveStatus(status);
					return "redirect:/games/{code}/board";
				}
			}else {
				model.put("message", "No posees esa carta. Elige otra");
				return "error";
			}
			
		}else {
			model.put("message", "Carta no encontrada.");
			return "error";
		}
		
	
	}
    
    //Pasar turno
    @GetMapping(value = "/games/{code}/turn")
    public String nextTurn(
    		@PathVariable("code") String code, ModelMap model){
    	Optional<Game> optGame = gameService.findGameByCode(code);
    	if(optGame.isPresent()) {
    		Game game = optGame.get();
    		if(gameService.loggedUserBelongsToGame(game)) {
    			Integer pn = game.getCurrentPlayer();
    			Status status = game.getStatus().get(pn);
    			if(status.getPlayer().getId()==playerService.findCurrentPlayer().get().getId()) {
    				if(game.getFinishedTurn()==1) {
    					gameService.nextTurn(game);
        				return "redirect:/games/{code}/board";
        			}else {
            			model.put("message", "No has acabado el turno.");
            			return "error";
            		} 
    				
    			}else {
        			model.put("message", "No es tu turno.");
        			return "error";
        		}    				       		
    		}else {
    			model.put("message", "No perteneces a esta partida.");
    			return "error";
    		}	
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
