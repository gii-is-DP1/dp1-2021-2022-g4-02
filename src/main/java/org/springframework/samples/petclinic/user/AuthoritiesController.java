package org.springframework.samples.petclinic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.card.Card;
import org.springframework.samples.petclinic.model.card.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthoritiesController {

	
	//private static final String VIEWS_CARDS_CREATE_OR_UPDATE_FORM = "authorities/editCard";
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@GetMapping(value = "/users")
	public String cardsList(ModelMap modelMap) {
		String vista = "authorities/usersList";
		Iterable<User> users = authoritiesService.userFindAll();
		modelMap.addAttribute("users", users);
		return vista;
	}
	
	
	
}
