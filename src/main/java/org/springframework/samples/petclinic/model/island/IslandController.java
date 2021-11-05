package org.springframework.samples.petclinic.model.island;

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
public class IslandController {
	
	private static final String VIEWS_ISLANDS_CREATE_OR_UPDATE_FORM = "islands/editIsland";
	
	@Autowired
	private IslandService islandService;
	
	@GetMapping(value = "/islands")
	public String islandsList(ModelMap modelMap) {
		String vista = "islands/islandsList";
		Iterable<Island> islands = islandService.islandFindAll();
		modelMap.addAttribute("islands", islands);
		return vista;
	}
	
	@GetMapping(value = "/islands/{islandId}")
	public String islandsListById(ModelMap modelMap, @PathVariable("islandId") int islandId){
		String vista = "islands/islandDetails";
		Island island = islandService.findIslandById(islandId);
		modelMap.addAttribute("island", island);
		return vista;
	}
	
    @GetMapping(value = "/islands/{islandId}/edit")
    public String initUpdateIslandForm(@PathVariable("islandId") int islandId, Model model) {
    	Island island = this.islandService.findIslandById(islandId);
    	model.addAttribute(island);
    	return VIEWS_ISLANDS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/islands/{islandId}/edit")
    public String processUpdateIslandForm(@Valid Island island, BindingResult result,
    		@PathVariable("islandId") int islandId) {
    	if (result.hasErrors()) {
    		return VIEWS_ISLANDS_CREATE_OR_UPDATE_FORM;
    	}
    	else {
    		island.setId(islandId);
    		this.islandService.saveIsland(island);
    		return "redirect:/islands/{islandId}";
    	}
    }
    
}
