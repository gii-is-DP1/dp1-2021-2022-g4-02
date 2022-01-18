/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sevenisles.user;

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
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.statistics.Statistics;
import sevenisles.statistics.StatisticsService;
import sevenisles.util.ManualLogin;


/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_USER_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";
	private static final String VIEWS_USER_DETAILS = "users/userDetails";
//	private static final String VIEWS_ERROR = "error";
	
	private PlayerService playerService;
	
	private UserService userService;
	
	private AuthoritiesService authoritiesService;
	
	private StatisticsService statisticsService;
	
	
	
	@Autowired
	public UserController(PlayerService playerService, UserService userService, AuthoritiesService authoritiesService, StatisticsService statisticsService) {
		this.playerService = playerService;
		this.userService = userService;
		this.authoritiesService  = authoritiesService;
		this.statisticsService = statisticsService;
	}
	
	//Get detalles de usuario
	@GetMapping(value = "/profile")
	public String userDetails(ModelMap modelMap){
		Optional<User> user = userService.findCurrentUser();
		modelMap.addAttribute("user", user.get());
		return VIEWS_USER_DETAILS;
	}
	
	/* Edición de un usuario */
	@GetMapping(value = "profile/edit")
	public String initUpdateForm(Model model ) {
		Optional<User> user = this.userService.findCurrentUser();
		model.addAttribute("user", user.get());
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "profile/edit")
	public String processUpdateUserForm(@Valid User user, BindingResult result,
			 ModelMap model) {
		if(result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}else {
			User userToUpdate = this.userService.findCurrentUser().get();
			BeanUtils.copyProperties(user, userToUpdate,"id");
			this.userService.saveUser(userToUpdate);
			ManualLogin.login(userToUpdate);
			return VIEWS_USER_DETAILS;
		}
		
	}

	@GetMapping(value = "/users/new")
	public String initCreationUserForm(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationUserForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}
		else {
			
			this.userService.saveUser(user);
			Player player = new Player();
			player.setUser(user);
			this.playerService.savePlayer(player);
			
			Statistics statistics = new Statistics();
			statistics.setPlayer(player);
			this.statisticsService.saveStatistic(statistics);
			
			Authorities auth = new Authorities();
	        auth.setAuthority("player");
	        auth.setUser(user);
	        authoritiesService.saveAuthorities(auth);
	        
	        
	        ManualLogin.login(user);
			
			return "redirect:/";
		}
	}




}
