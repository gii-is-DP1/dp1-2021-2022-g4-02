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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_USER_EDIT_FORM = "users/editUserForm";
	
	private UserService userService;
	
	//Get detalles de usuario
	@GetMapping(value = "/profile")
	public String userDetails(ModelMap modelMap){
		String vista = "users/userDetails";
		System.out.println(User.getCurrentUser());
		Optional<User> user = userService.findCurrentUser();
		System.out.println(user.get().getUsername()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		return VIEWS_USER_EDIT_FORM;
	}
	
	@PostMapping(value = "profile/edit")
	public String processUpdateUserForm(@Valid User user, BindingResult result,
			 ModelMap model) {
		if(result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USER_EDIT_FORM;
		}else {
			User userToUpdate = this.userService.findCurrentUser().get();
			BeanUtils.copyProperties(user, userToUpdate);
			this.userService.saveUser(userToUpdate);
			return "redirect:/profile";
		}
		
	}





}
