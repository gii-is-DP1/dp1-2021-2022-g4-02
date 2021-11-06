package org.springframework.samples.petclinic.user;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.game.Game;
import org.springframework.samples.petclinic.model.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthoritiesController {

	
	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "authorities/editUser";
	
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private GameService gameService;
	
	@GetMapping(value = "/users")
	public String usersList(ModelMap modelMap) {
		String vista = "authorities/usersList";
		Iterable<User> users = authoritiesService.userFindAll();
		modelMap.addAttribute("users", users);
		return vista;
	}
	/* Listado de partidas jugadas */
	@GetMapping(value = "/games/finished")
	public String finishedGamesList(ModelMap modelMap) {
		String vista = "games/gamesList";
		List<Game> games = gameService.findFinishedGames();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	/* Listado de partidas en juego */
	@GetMapping(value = "/games/unfinished")
	public String unfinishedGamesList(ModelMap modelMap) {
		String vista = "games/gamesList";
		List<Game> games = gameService.findUnfinishedGames();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	/* Creación de un nuevo user */
	
	
	//Controlador a la vista del form para la creación del usuario
	@GetMapping(value = "authorities/users/new")
	public String initCreationForm( Map<String, Object> model ) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM ;
	}
	
	
	@PostMapping(value = "authorities/users/new")
	public String processCreationForm(@Valid User user , BindingResult result) {		
		if (result.hasErrors()) {
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    
                    	
                    this.authoritiesService.saveUser(user);
                    
                    return "redirect:/";
		}
	}

	
}
