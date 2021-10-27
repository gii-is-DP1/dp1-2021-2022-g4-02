package org.springframework.samples.petclinic.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@GetMapping()
	public String listadoCartas(ModelMap modelMap) {
		String vista = "cards/listadoCartas";
		Iterable<Card> cards = cardService.cardFindAll();
		modelMap.addAttribute("cards", cards);
		return vista;
	}
}
