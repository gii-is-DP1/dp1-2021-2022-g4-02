package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
//@RequestMapping("/cards")
public class CardController {
	
	private static final String VIEWS_CARDS_CREATE_OR_UPDATE_FORM = "cards/editCard";
	
	@Autowired
	private CardService cardService;
	
	@GetMapping(value = "/cards")
	public String listadoCartas(ModelMap modelMap) {
		String vista = "cards/listadoCartas";
		Iterable<Card> cards = cardService.cardFindAll();
		modelMap.addAttribute("cards", cards);
		return vista;
	}
	
	@GetMapping(value = "/cards/{cardId}")
	public String listadoCartaPorId(ModelMap modelMap, @PathVariable("cardId") int cardId){
		String vista = "cards/cartaDetails";
		Card card = cardService.findCardById(cardId);
		modelMap.addAttribute("card", card);
		return vista;
	}
	
    @GetMapping(value = "/cards/{cardId}/edit")
    public String initUpdateOwnerForm(@PathVariable("cardId") int cardId, Model model) {
    	Card card = this.cardService.findCardById(cardId);
    	model.addAttribute(card);
    	return VIEWS_CARDS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/cards/{cardId}/edit")
    public String processUpdateOwnerForm(@Valid Card card, BindingResult result,
    		@PathVariable("cardId") int cardId) {
    	if (result.hasErrors()) {
    		return VIEWS_CARDS_CREATE_OR_UPDATE_FORM;
    	}
    	else {
    		card.setId(cardId);
    		this.cardService.saveCard(card);
    		return "redirect:/cards/{cardId}";
    	}
    }
    
}
