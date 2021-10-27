package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
	
	private static final String VIEWS_CARDS_CREATE_OR_UPDATE_FORM = "cards/createOrUpdateCardForm";
	
	@Autowired
	private CardService cardService;
	
	@GetMapping()
	public String listadoCartas(ModelMap modelMap) {
		String vista = "cards/listadoCartas";
		Iterable<Card> cards = cardService.cardFindAll();
		modelMap.addAttribute("cards", cards);
		return vista;
	}
	
	
	@GetMapping(value = "/cards/{cardId}/edit")
	public String initUpdateForm(@PathVariable("cardId") int cardId, ModelMap model) {
		Card card = this.cardService.findCardById(cardId);
		model.put("card", card);
		return VIEWS_CARDS_CREATE_OR_UPDATE_FORM;
	}

    /**
     *
     * @param card
     * @param result
     * @param cardId
     * @param model
     * @return
     */
        @PostMapping(value = "/cards/{cardId}/edit")
	public String processUpdateForm(@Valid Card card, BindingResult result, @PathVariable("cardId") int cardId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("card", card);
			return VIEWS_CARDS_CREATE_OR_UPDATE_FORM;
		}
		else {
                        Card cardToUpdate=this.cardService.findCardById(cardId);
			BeanUtils.copyProperties(card, cardToUpdate, "cardId","card_type");                                                                                  
                    try {                    
                        this.cardService.saveCard(cardToUpdate);                    
                    } catch (DuplicateKeyException ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_CARDS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/cards/{cardId}";
		}
	}
}
