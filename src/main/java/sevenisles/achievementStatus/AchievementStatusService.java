package sevenisles.achievementStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.achievement.Achievement;
import sevenisles.achievement.AchievementService;
import sevenisles.achievement.AchievementType;
import sevenisles.player.Player;
import sevenisles.statistics.Statistics;
import sevenisles.statistics.StatisticsService;

@Service
public class AchievementStatusService {
	
	private AchievementStatusRepository achievementStatusRepo;
	private StatisticsService statisticsService;
	private AchievementService achievementService;
	
	@Autowired
	public AchievementStatusService(AchievementStatusRepository achievementStatusRepo, StatisticsService statisticsService, AchievementService achievementService) {
		this.achievementStatusRepo=achievementStatusRepo;
		this.statisticsService=statisticsService;
		this.achievementService=achievementService;
	}
	
	@Transactional(readOnly=true)
	public AchievementStatus findAchievementStatusByStatsAndAchievement(Statistics stats, Achievement achievement) {
		return achievementStatusRepo.getAchievementStatusOfStatisticsAndAchievement(stats, achievement);
	}
	
	@Transactional(readOnly=true)
	public List<AchievementStatus> findAchievementStatusByStats(Statistics stats) {
		return achievementStatusRepo.getAchievementStatusOfStatistics(stats);
	}
	
	@Transactional
	public void saveAchievementStatus(AchievementStatus as) throws DataAccessException{
		achievementStatusRepo.save(as);
	}
	
	@Transactional
	public void asignacionInicialDeLogros(Statistics stats) {
		List<Achievement> ac = (List<Achievement>) achievementService.achievementFindAll();
		List<AchievementStatus> ls = new ArrayList<AchievementStatus>();
		for(Achievement a:ac) {
			AchievementStatus as = new AchievementStatus();
			as.setAchieved(false);
			as.setAchievement(a);
			as.setStatistics(stats);
			saveAchievementStatus(as);
			ls.add(as);
		}
		stats.setAchievementsStatus(ls);
		statisticsService.saveStatistic(stats);
	}
	
	@Transactional
	public void setAchievements(Player p) {
		playedGamesAchievement(p);
		chaliceAchievement(p);
		rubiAchievement(p);
		diamondAchievement(p);
		necklaceAchievement(p);
		mapAchievement(p);
		crownAchievement(p);
		gunAchievement(p);
		swordAchievement(p);
		rumAchievement(p);
	}
	
	@Transactional
	public void playedGamesAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer gamesPlayed = stats.getGamesPlayed();
			if(gamesPlayed >= 1) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_1);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);		
			}
			if(gamesPlayed >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	
	@Transactional
	public void wonGamesAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer wonGames = stats.getGamesWon();
			if(wonGames >= 1) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_1);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(wonGames >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void scoreAchievement(Player p, Integer score) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			if(score >= 60) {													 
				Achievement a = achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_60);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(score >= 40) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_40);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void chaliceAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer chalices = stats.getChaliceCount();
			if(chalices >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(chalices >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void rubiAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer rubies = stats.getRubyCount();
			if(rubies >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(rubies >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void diamondAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer diamonds = stats.getDiamondCount();
			if(diamonds >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(diamonds >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void necklaceAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer necklaces = stats.getNecklaceCount();
			if(necklaces >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(necklaces >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void mapAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer maps = stats.getMapCount();
			if(maps >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(maps >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void crownAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer crowns = stats.getCrownCount();
			if(crowns >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(crowns >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void gunAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer guns = stats.getGunCount();
			if(guns >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(guns >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void swordAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer swords = stats.getSwordCount();
			if(swords >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(swords >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}
	
	@Transactional
	public void rumAchievement(Player p) {
		Optional<Statistics> opt = statisticsService.getStatsByPlayer(p.getId());
		if(opt.isPresent()) {
			Statistics stats = opt.get();
			Integer rums = stats.getRumCount();
			if(rums >= 5) {
				Achievement a = achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_5);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
				
			}
			if(rums >= 10) {
				Achievement a = achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_10);
				AchievementStatus as = findAchievementStatusByStatsAndAchievement(stats, a);
				as.setAchieved(true);
				saveAchievementStatus(as);
			}
		}
	}

}
