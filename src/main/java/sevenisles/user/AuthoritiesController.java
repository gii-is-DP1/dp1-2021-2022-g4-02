package sevenisles.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sevenisles.achievementStatus.AchievementStatusService;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.statistics.Statistics;
import sevenisles.statistics.StatisticsService;
import sevenisles.util.ManualLogin;

@Controller
public class AuthoritiesController {

	
	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "authorities/editUser";
	private static final String VIEWS_AUTH_CREATE_OR_UPDATE_FORM = "authorities/editAuth";
	private static final String VIEWS_ERROR = "error";
	
	private AuthoritiesService authoritiesService;
	
	private UserService userService;
	
	private GameService gameService;
	
	private PlayerService playerService;
	
	private StatisticsService statisticsService;
	
	private AchievementStatusService achievementStatusService;
	
	@Autowired
	public AuthoritiesController(AuthoritiesService authoritiesService, UserService userService, GameService gameService, PlayerService playerService,
			StatisticsService statisticsService,AchievementStatusService achievementStatusService) {
		this.authoritiesService = authoritiesService;
		this.userService = userService;
		this.gameService = gameService;
		this.playerService = playerService;
		this.statisticsService=statisticsService;
		this.achievementStatusService=achievementStatusService;
	}
	
	@GetMapping(value = "admin/users")
	public String usersList(ModelMap modelMap) {
		String vista = "authorities/usersList";
		Iterable<User> users = userService.findAllOrderByUsername();
		modelMap.addAttribute("users", users);
		return vista;
	}
	
	
	@GetMapping(value = "admin/page/users")
	public String usersListPagination(@RequestParam Map<String,Object> page, ModelMap modelMap) {
		String vista = "authorities/usersList";
	
		if(page.get("page") != null) {
			int pageactual = Integer.valueOf(page.get("page").toString())-1;
			
			PageRequest request = PageRequest.of(pageactual,5);
			
			Page<User> users = userService.findByUsernamePageable(request);
			
			int pages = users.getTotalPages();
			
			List<Integer> npages = IntStream.rangeClosed(1,pages).boxed().collect(Collectors.toList());
			modelMap.addAttribute("paginas", npages);
			
			modelMap.addAttribute("users" , users.getContent());
			
		}else {
			return "redirect:/admin/page/users?page=1";
		}
		
		
		
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
	public String processCreationForm(@Valid User user , BindingResult result, Map<String, Object> model) {		
		if (result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}
		else {     	
			this.authoritiesService.insertdataAuditory(user,this.userService.findCurrentUser().get());
			this.userService.saveUser(user);
			Player player = new Player();
			player.setUser(user);
			this.playerService.savePlayer(player);
			
			Statistics statistics = new Statistics();
			statistics.setPlayer(player);
			this.statisticsService.saveStatistic(statistics);
			achievementStatusService.asignacionInicialDeLogros(statistics);
			
			
			Authorities auth = new Authorities();
	        auth.setAuthority("player");
	        auth.setUser(user);
	        authoritiesService.saveAuthorities(auth);                  
            return "redirect:/admin/page/users?page=1";
		}
	}

	/* Edición de un usuario */
	@GetMapping(value = "admin/{id}/edit")
	public String initUpdateForm(@PathVariable("id") Integer id, Model model ) {	
		Optional<User> user = this.userService.findUserById(id);
		if(user.isPresent()) {
			model.addAttribute(user.get());
			model.addAttribute("message", "Usuario encontrado!");
		}else {
			model.addAttribute("message", "Usuario no encontrado!");
			return VIEWS_ERROR;
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
			User userToUpdate = this.userService.findUserById(id).get();
			if(User.getCurrentUser().equals(userToUpdate.getUsername())) {
				BeanUtils.copyProperties(user, userToUpdate,"id");
				this.authoritiesService.editdataAuditory(userToUpdate,this.userService.findCurrentUser().get());
				this.userService.saveUser(userToUpdate);
				ManualLogin.login(userToUpdate);
				
			}else {
				BeanUtils.copyProperties(user, userToUpdate,"id");
				this.authoritiesService.editdataAuditory(userToUpdate,this.userService.findCurrentUser().get());
				this.userService.saveUser(userToUpdate);
			}
			return "redirect:/admin/page/users?page=1";
		}
		
	}
	
	//Borrado de usuario
	@GetMapping(value = "admin/users/{id}/delete")
	public String processDeleteUser(@PathVariable("id") Integer id, ModelMap model) {
		Optional<User> user =  userService.findUserById(id);
		if(user.isPresent()) {
			System.out.println(User.getCurrentUser());
			System.out.println(user.get().getUsername());
			if(!(User.getCurrentUser().equals(user.get().getUsername()))) {
				userService.deleteUser(user.get());
				model.addAttribute("message", "Permisos encontrados!");
			}
			else {
				model.addAttribute("message", "No te puedes borrar a ti mismo");
				return VIEWS_ERROR;
			}
		}else {
			model.addAttribute("message", "Usuario no encontrado!");
		}
		
		 return "redirect:/admin/page/users?page=1";
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
			Optional<User> user = userService.findUserById(id);
			if(user.isPresent()) {
				auth.setUser(user.get());
				this.authoritiesService.saveAuthorities(auth); 
				user.get().setAuthorities(auth);
				this.userService.saveUser(user.get());
			}else {
				model.put("message", "Usuario no encontrado");
				return VIEWS_AUTH_CREATE_OR_UPDATE_FORM;
			}              
            return "redirect:/admin/page/users?page=1";
		}
	}

	/* Edición de permisos */
	@GetMapping(value = "admin/authorities/{user}/edit")
	public String initAuthUpdateForm(@PathVariable("user") Integer id, Model model ) {	
		Optional<Authorities> authorities = this.authoritiesService.findAuthByUser(id);
		
		if(authorities.isPresent()) {
			if(!(User.getCurrentUser().equals(userService.findUserById(id).get().getUsername()))){
				model.addAttribute(authorities.get());
			}else {
				model.addAttribute("message", "No puedes editar tus permisos");
				return VIEWS_ERROR;
			}
		}else {
			model.addAttribute("message", "Permisos no encontrados!");
			return VIEWS_ERROR;
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
			Authorities authToUpdate = this.authoritiesService.findAuthByUser(id).get();			
			BeanUtils.copyProperties(authorities, authToUpdate,"id");
			this.authoritiesService.saveAuthorities(authToUpdate);
			model.addAttribute("message", "Permisos encontrados!");	
			
			return "redirect:/admin/page/users?page=1";
		}		
	}
	
	//Borrado de permisos
		@GetMapping(value = "admin/authorities/{id}/delete")
		public String processDeleteAuth(@PathVariable("id") Integer id, ModelMap model) {
			Optional<Authorities> auth =  authoritiesService.findAuthByUser(id);
			if(auth.isPresent()) {
				Optional<User> user = userService.findUserById(id);
				
					System.out.println("Username currentUser "+User.getCurrentUser());
					System.out.println("Username User "+user.get().getUsername());
					if(!(User.getCurrentUser().equals(user.get().getUsername()))) {
						user.get().setAuthorities(null);
						userService.saveUser(user.get());
						authoritiesService.deleteAuth(auth.get());
						model.addAttribute("message", "Permisos encontrados!");
					}

					else {
						model.addAttribute("message", "No te puedes borrar tus permisos");
						return VIEWS_ERROR;
					}
			}else {
				model.addAttribute("message", "Permisos no encontrados!");
				return VIEWS_ERROR;
			}
			
			 return "redirect:/admin/page/users?page=1";
		}
	
	
	
}
