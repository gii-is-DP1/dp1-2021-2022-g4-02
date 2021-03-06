package sevenisles.statistics;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import sevenisles.achievementStatus.AchievementStatus;
import sevenisles.achievementStatus.AchievementStatusService;
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;

@Controller
public class StatisticsController {
	
	private static final String VIEW_RANKING = "statistics/ranking";
	
	
	private StatisticsService statisticsService;
	private PlayerService playerService;
	private AchievementStatusService achievementStatusService;
	
	@Autowired
	public StatisticsController(StatisticsService statisticsService, PlayerService playerService, AchievementStatusService achievementStatusService) {
		this.statisticsService=statisticsService;
		this.playerService=playerService;
		this.achievementStatusService=achievementStatusService;
	}
	
	
	@GetMapping(value = "/statistics/ranking")
	public String statisticsRankingList(ModelMap modelMap) {
		List<Statistics> statistics = statisticsService.getRanking();
		modelMap.addAttribute("statistics", statistics);
		return VIEW_RANKING;
	}
	
	@GetMapping(value = "/statistics")
	public String statisticsDetails(ModelMap modelMap) throws GameControllerException{
		String vista = "statistics/statisticsDetails";
		Optional<Player> player = playerService.findCurrentPlayer();
		if(player.isPresent()) {
			Statistics st = statisticsService.getStatsByPlayer(player.get().getId()).get();
			List<AchievementStatus> ls = achievementStatusService.findAchievementStatusByStats(st);
			modelMap.addAttribute("statistics", st);
			modelMap.addAttribute("achievements", ls);
			return vista;
		}else {
			throw new GameControllerException("Necesitas estar logueado para ver tus estadísticas.");
			
		}
		
	}

}
