package org.springframework.samples.petclinic.model.lobby;

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
public class LobbyController {
	
	private static final String VIEWS_LOBBIES_CREATE_OR_UPDATE_FORM = "lobbies/editLobby";
	
	@Autowired
	private LobbyService lobbyService;
	
	@GetMapping(value = "/lobbies")
	public String lobbiesList(ModelMap modelMap) {
		String vista = "lobbies/lobbiesList";
		Iterable<Lobby> lobbies = lobbyService.lobbyFindAll();
		modelMap.addAttribute("lobbies", lobbies);
		return vista;
	}
	
	@GetMapping(value = "/lobbies/{lobbyId}")
	public String lobbiesListById(ModelMap modelMap, @PathVariable("lobbyId") int lobbyId){
		String vista = "lobbies/lobbyDetails";
		Lobby lobby = lobbyService.findLobbyById(lobbyId);
		modelMap.addAttribute("lobby", lobby);
		return vista;
	}
	
    @GetMapping(value = "/lobbies/{lobbyId}/edit")
    public String initUpdateLobbyForm(@PathVariable("lobbyId") int lobbyId, Model model) {
    	Lobby lobby = this.lobbyService.findLobbyById(lobbyId);
    	model.addAttribute(lobby);
    	return VIEWS_LOBBIES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/lobbies/{lobbyId}/edit")
    public String processUpdateLobbyForm(@Valid Lobby lobby, BindingResult result,
    		@PathVariable("lobbyId") int lobbyId) {
    	if (result.hasErrors()) {
    		return VIEWS_LOBBIES_CREATE_OR_UPDATE_FORM;
    	}
    	else {
    		lobby.setId(lobbyId);
    		this.lobbyService.saveLobby(lobby);
    		return "redirect:/lobbies/{lobbyId}";
    	}
    }
    
}
