package org.springframework.samples.petclinic.model.statistics;

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
public class StatisticsController {
	
	private static final String VIEWS_STATISTICS_CREATE_OR_UPDATE_FORM = "statistics/editStatistics";

	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping(value = "/statistics")
	public String statisticsList(ModelMap modelMap) {
		String vista = "statistics/statisticsList";
		Iterable<Statistics> statistics = statisticsService.statisticsFindAll();
		modelMap.addAttribute("statistics", statistics);
		return vista;
	}
	
	@GetMapping(value = "/statistics/{statisticsId}")
	public String cardsListById(ModelMap modelMap, @PathVariable("cardId") int statisticsId){
		String vista = "statistics/statisticsDetails";
		Statistics statistics = statisticsService.findStatisticsById(statisticsId);
		modelMap.addAttribute("statistics", statistics);
		return vista;
	}
	
    @GetMapping(value = "/statistics/{statisticsId}/edit")
    public String initUpdateStatisticsForm(@PathVariable("statisticsId") int statisticsId, Model model) {
    	Statistics statistics = this.statisticsService.findStatisticsById(statisticsId);
    	model.addAttribute(statistics);
    	return VIEWS_STATISTICS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/statistics/{statisticsId}/edit")
    public String processUpdateStatisticsForm(@Valid Statistics statistics, BindingResult result,
    		@PathVariable("statisticsId") int statisticsId) {
    	if (result.hasErrors()) {
    		return VIEWS_STATISTICS_CREATE_OR_UPDATE_FORM;
    	}
    	else {
    		statistics.setId(statisticsId);
    		this.statisticsService.saveStatistic(statistics);
    		return "redirect:/cards/{cardId}";
    	}
    }

}
