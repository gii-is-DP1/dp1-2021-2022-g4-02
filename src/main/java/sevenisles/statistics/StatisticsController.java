package sevenisles.statistics;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sevenisles.game.Game;
import sevenisles.game.exceptions.GameControllerException;

@Controller
public class StatisticsController {
	
	private static final String VIEW_RANKING = "statistics/ranking";
	
	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping(value = "/statistics")
	public String statisticsList(ModelMap modelMap) {
		String vista = "statistics/statisticsList";
		Iterable<Statistics> statistics = statisticsService.statisticsFindAll();
		modelMap.addAttribute("statistics", statistics);
		return vista;
	}
	
	
	@GetMapping(value = "/statistics/ranking")
	public String statisticsRankingList(ModelMap modelMap) {
		List<Statistics> statistics = statisticsService.getRanking();
		modelMap.addAttribute("statistics", statistics);
		return VIEW_RANKING;
	}
	
	@GetMapping(value = "/statistics/{statisticsId}")
	public String statisticsListById(ModelMap modelMap, @PathVariable("statisticsId") int statisticsId){
		String vista = "statistics/statisticsDetails";
		Optional<Statistics> st = statisticsService.findStatisticsById(statisticsId);
		if(st.isPresent()) {
			modelMap.addAttribute("statistics", st);
			return vista;
		}else {
			throw new GameControllerException("Estadísticas no encontradas");
		}
	}

}
