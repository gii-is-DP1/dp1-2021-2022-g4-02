package sevenisles.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;

@Controller
public class AuthoritiesController {

	
	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "authorities/editUser";
	private static final String VIEWS_AUTH_CREATE_OR_UPDATE_FORM = "authorities/editAuth";
	
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	
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
			Player player = new Player();
			player.setUser(user);
			this.playerService.savePlayer(player);
			
			Authorities auth = new Authorities();
	        auth.setAuthority("player");
	        auth.setUser(user);
	        authoritiesService.saveAuthorities(auth);                  
            return "redirect:/admin/users";
		}
	}

	/* Edición de un usuario */
	@GetMapping(value = "admin/{id}/edit")
	public String initUpdateForm(@PathVariable("id") Integer id, Model model ) {	
		Optional<User> user = this.authoritiesService.findUserById(id);
		if(user.isPresent()) {
			model.addAttribute(user.get());
			model.addAttribute("message", "Usuario encontrado!");
		}else {
			model.addAttribute("message", "Usuario no encontrado!");
		}
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "admin/{id}/edit")
	public String processUpdateUserForm(@Valid User user, BindingResult result,
			@PathVariable("id") Integer id, ModelMap model) {
		if(result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}else {
			Optional<User> userToUpdate = this.authoritiesService.findUserById(id);
			if(userToUpdate.isPresent()) {
				BeanUtils.copyProperties(user, userToUpdate.get(),"id");
				this.authoritiesService.saveUser(userToUpdate.get());
				model.addAttribute("message", "Usuario encontrado!");
			}else {
				model.addAttribute("message", "Usuario no encontrado!");
			}
			
			return "redirect:/admin/users";
		}
		
	}
	
	//Borrado de usuario
	@GetMapping(value = "admin/users/{id}/delete")
	public String processDeleteUser(@PathVariable("id") Integer id, ModelMap model) {
		Optional<User> user =  authoritiesService.findUserById(id);
		if(user.isPresent()) {
			if(!(User.getCurrentUser().equals(user.get().getUsername()))) {
				authoritiesService.deleteUser(user.get());
				model.addAttribute("message", "Permisos encontrados!");
			}
			else {
				model.addAttribute("message", "No te puedes borrar a ti mismo");
			}
		}else {
			model.addAttribute("message", "Usuario no encontrado!");
		}
		
		 return "redirect:/admin/users";
	}
	
	//Creación de permisos
	@GetMapping(value = "admin/authorities/{user}/new")
	public String initAuthCreationForm(@PathVariable("user") Integer id, Map<String, Object> model ) {
		Authorities auth = new Authorities();
		model.put("authorities", auth);
		return VIEWS_AUTH_CREATE_OR_UPDATE_FORM ;
	}
	
	@PostMapping(value = "admin/authorities/{user}/new")
	public String processAuthCreationForm(@PathVariable("user") Integer id,@Valid Authorities auth , BindingResult result,Map<String, Object> model) {		
		if (result.hasErrors()) {
			return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
		}
		else {     	
			Optional<User> user = authoritiesService.findUserById(id);
			if(user.isPresent()) {
				auth.setUser(user.get());
				this.authoritiesService.saveAuthorities(auth); 
			}else {
				model.put("message", "Usuario no encontrado");
				return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
			}              
            return "redirect:/admin/users";
		}
	}

	/* Edición de permisos */
	@GetMapping(value = "admin/authorities/{user}/edit")
	public String initAuthUpdateForm(@PathVariable("user") Integer id, Model model ) {	
		Optional<Authorities> authorities = this.authoritiesService.findAuthByUser(id);
		if(authorities.isPresent()) {
			model.addAttribute(authorities.get());
			model.addAttribute("message", "Permisos encontrados!");
		}else {
			model.addAttribute("message", "Permisos no encontrados!");
		}
		
		
		return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "admin/authorities/{user}/edit")
	public String processAuthUpdateUserForm(@Valid Authorities authorities, BindingResult result,
			@PathVariable("user") Integer id, ModelMap model) {
		if(result.hasErrors()) {
			model.put("authorities", authorities);
			return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
		}else {
			Optional<Authorities> authToUpdate = this.authoritiesService.findAuthByUser(id);
			if(authToUpdate.isPresent()) {
				BeanUtils.copyProperties(authorities, authToUpdate.get(),"id");
				this.authoritiesService.saveAuthorities(authToUpdate.get());
				model.addAttribute("message", "Permisos encontrados!");
			}else {
				model.addAttribute("message", "Permisos no encontrados!");
			}
			
			return "redirect:/admin/users";
		}		
	}
	
	//Borrado de permisos
		@GetMapping(value = "admin/authorities/{id}/delete")
		public String processDeleteAuth(@PathVariable("id") Integer id, ModelMap model) {
			Optional<Authorities> auth =  authoritiesService.findAuthByUser(id);
			if(auth.isPresent()) {
				Optional<User> user = authoritiesService.findUserById(id);
				if(user.isPresent()) {
					if(!(User.getCurrentUser().equals(user.get().getUsername()))) {
						user.get().setAuthorities(null);
						authoritiesService.saveUser(user.get());
						authoritiesService.deleteAuth(auth.get());
						model.addAttribute("message", "Permisos encontrados!");
					}

					else {
						model.addAttribute("message", "No te puedes borrar tus permisos");
					}
				}else {
					model.addAttribute("message", "Usuario no encontrado!");
				}
				
			}else {
				model.addAttribute("message", "Permisos no encontrados!");
			}
			
			 return "redirect:/admin/users";
		}
	
	
	
}
