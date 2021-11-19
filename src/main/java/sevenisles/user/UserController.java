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
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sevenisles.player.Player;
import sevenisles.player.PlayerService;


/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

//	private static final String VIEWS_USER_EDIT_FORM = "users/editUserForm";
	private static final String VIEWS_USER_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	//Get detalles de usuario
	@GetMapping(value = "/profile")
	public String userDetails(ModelMap modelMap){
		String vista = "users/userDetails";
		Optional<User> user = userService.findCurrentUser();
		if(user.isPresent()) modelMap.addAttribute("user", user.get());
		else modelMap.addAttribute("message", "No estás logueado!");
		return vista;
	}
	
	/* Edición de un usuario */
	@GetMapping(value = "profile/edit")
	public String initUpdateForm(Model model ) {
		Optional<User> user = this.userService.findCurrentUser();
		if(user.isPresent()) model.addAttribute(user.get());
		else model.addAttribute("message", "No estás logueado!");
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
			User user2 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user2.setUsername(userToUpdate.getUsername());

			return "redirect:/profile";
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
			
			Authorities auth = new Authorities();
	        auth.setAuthority("player");
	        auth.setUser(user);
	        authoritiesService.saveAuthorities(auth);
			
			return "redirect:/";
		}
	}




}
