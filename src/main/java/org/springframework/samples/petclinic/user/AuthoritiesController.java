package org.springframework.samples.petclinic.user;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.game.Game;
import org.springframework.samples.petclinic.model.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthoritiesController {

	
	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "authorities/editUser";
	private static final String VIEWS_AUTH_CREATE_OR_UPDATE_FORM = "authorities/editAuth";
	
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private GameService gameService;
	
	@GetMapping(value = "admin/users")
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
	@GetMapping(value = "admin/users/new")
	public String initCreationForm( Map<String, Object> model ) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM ;
	}
	
	
	@PostMapping(value = "admin/users/new")
	public String processCreationForm(@Valid User user , BindingResult result) {		
		if (result.hasErrors()) {
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}
		else {     	
                    this.authoritiesService.saveUser(user);                  
                    return "redirect:/admin/users";
		}
	}

	/* Edición de un usuario */
	@GetMapping(value = "admin/{id}/edit")
	public String initUpdateForm(@PathVariable("id") Integer id, Model model ) {	
		User user = this.authoritiesService.findUserById(id);
		model.addAttribute(user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "admin/{id}/edit")
	public String processUpdateUserForm(@Valid User user, BindingResult result,
			@PathVariable("id") Integer id, ModelMap model) {
		if(result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}else {
			User userToUpdate = this.authoritiesService.findUserById(id);
			BeanUtils.copyProperties(user, userToUpdate);
			this.authoritiesService.saveUser(userToUpdate);
			return "redirect:/admin/users";
		}
		
	}
	
	//Borrar usuario
	@DeleteMapping(value = "admin/users/{id}/delete")
	public String processDelete(@PathVariable("id") Integer id) {
		authoritiesService.deleteUser(id);
		return "redirect:/admin/users";
	}
	
	//Creación de permisos
	@GetMapping(value = "admin/authorities/new")
	public String initAuthCreationForm( Map<String, Object> model ) {
		Authorities auth = new Authorities();
		model.put("authorities", auth);
		return VIEWS_AUTH_CREATE_OR_UPDATE_FORM ;
	}
	
	@PostMapping(value = "admin/authorities/new")
	public String processAuthCreationForm(@Valid Authorities auth , BindingResult result) {		
		if (result.hasErrors()) {
			return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
		}
		else {     	
                    this.authoritiesService.saveAuthorities(auth);                  
                    return "redirect:/admin/users";
		}
	}

	/* Edición de permisos */
	@GetMapping(value = "admin/authorities/{user}/edit")
	public String initAuthUpdateForm(@PathVariable("user") Integer id, Model model ) {		
		Authorities authorities = this.authoritiesService.findAuthByUser(id);
		model.addAttribute(authorities);
		return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "admin/authorities/{user}/edit")
	public String processAuthUpdateUserForm(@Valid Authorities authorities, BindingResult result,
			@PathVariable("user") Integer id, ModelMap model) {
		if(result.hasErrors()) {
			model.put("authorities", authorities);
			return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
		}else {
			Authorities authToUpdate = this.authoritiesService.findAuthByUser(id);
			BeanUtils.copyProperties(authorities, authToUpdate);
			this.authoritiesService.saveAuthorities(authToUpdate);
			return "redirect:/admin/users";
		}
		
	}
	
	
	
}
