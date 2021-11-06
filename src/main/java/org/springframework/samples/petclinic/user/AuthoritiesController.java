package org.springframework.samples.petclinic.user;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.card.Card;
import org.springframework.samples.petclinic.model.card.CardService;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.user.exceptions.DuplicatedUserNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthoritiesController {

	
	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "authorities/editUser";
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@GetMapping(value = "/users")
	public String usersList(ModelMap modelMap) {
		String vista = "authorities/usersList";
		Iterable<User> users = authoritiesService.userFindAll();
		modelMap.addAttribute("users", users);
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
                    
                    return "redirect:/";
		}
	}

	/* Edición de un usuario */
	@GetMapping(value = "admin/{username}/edit")
	public String initUpdateForm(@PathVariable("username") String username, Model model ) {
		
		User user = this.authoritiesService.findUserById(username);
		model.addAttribute(user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "admin/{username}/edit")
	public String processUpdateUserForm(@Valid User user, BindingResult result,
			@PathVariable("username") String username) {
		if(result.hasErrors()) {
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
		}else {
			user.setUsername(username);
			this.authoritiesService.saveUser(user);
			return "redirect:/authorities/usersList";
		}
		
	}
	
}
