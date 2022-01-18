package sevenisles.achievement;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sevenisles.player.Player;
import sevenisles.statistics.Statistics;
import sevenisles.statistics.StatisticsRepository;

@Service
public class AchievementService {
	
	private AchievementRepository achievementRepo;
	private StatisticsRepository statisticsRepo;
	
	@Autowired
	public AchievementService(AchievementRepository achievementRepo, StatisticsRepository statisticsRepo) {
		this.achievementRepo = achievementRepo;
		this.statisticsRepo = statisticsRepo;
	}
	
	@Transactional(readOnly = true)
	public Integer achievementCount() {
		return (int) achievementRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Achievement> achievementFindAll() {
		return achievementRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Achievement> findAchievementById(int id) throws IllegalArgumentException { 
		return achievementRepo.findById(id);
	}
	
	@Transactional
	public void saveAchievement(Achievement achievementToUpdate) throws DataAccessException {
		achievementRepo.save(achievementToUpdate);
	}
	
	@Transactional
	public void deleteAchievement(Achievement achievementToDelete) throws DataAccessException {
		achievementRepo.delete(achievementToDelete);
	}
	
	@Transactional
	public void setAchievements(Player p) {
		PlayedGamesAchievement(p);
		ChaliceAchievement(p);
		RubiAchievement(p);
		DiamondAchievement(p);
		NecklaceAchievement(p);
		MapAchievement(p);
		CrownAchievement(p);
		GunAchievement(p);
		SwordAchievement(p);
		RumAchievement(p);
		ChaliceAchievement(p);
	}
	
	@Transactional
	public void PlayedGamesAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer GamesPlayed = stats.getGamesPlayed();
		if(GamesPlayed == 1) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PARTIDAS_JUGADAS_1);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(GamesPlayed == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PARTIDAS_JUGADAS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	//Hay que mirar que no tenga los logros ya en las condiciones
	
	@Transactional
	public void WonGamesAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer WonGames = stats.getGamesWon();
		if(WonGames == 1 /*&& achievementRepo.checkAchievement(AchievementType.PARTIDAS_GANADAS_1, p)*/) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PARTIDAS_GANADAS_1);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(WonGames == 5 /*&& achievementRepo.checkAchievement(AchievementType.PARTIDAS_GANADAS_5, p)*/) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PARTIDAS_GANADAS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void ScoreAchievement(Player p, Integer score) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		if(score >= 40) {													 
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PUNTOS_CONSEGUIDOS_40);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(score >= 60) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PUNTOS_CONSEGUIDOS_60);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void ChaliceAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer chalices = stats.getChaliceCount();
		if(chalices == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.CALICES_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(chalices == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.CALICES_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void RubiAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer rubies = stats.getRubyCount();
		if(rubies == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.RUBIES_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(rubies == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.RUBIES_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void DiamondAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer diamonds = stats.getDiamondCount();
		if(diamonds == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.DIAMANTES_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(diamonds == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.DIAMANTES_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void NecklaceAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer necklaces = stats.getNecklaceCount();
		if(necklaces == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.COLLARES_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(necklaces == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.COLLARES_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void MapAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer maps = stats.getMapCount();
		if(maps == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.MAPAS_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(maps == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.MAPAS_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void CrownAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer crowns = stats.getCrownCount();
		if(crowns == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.CORONAS_CONSEGUIDAS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(crowns == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.CORONAS_CONSEGUIDAS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void GunAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer guns = stats.getGunCount();
		if(guns == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PISTOLAS_CONSEGUIDAS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(guns == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.PISTOLAS_CONSEGUIDAS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void SwordAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer swords = stats.getSwordCount();
		if(swords == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.ESPADAS_CONSEGUIDAS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(swords == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.ESPADAS_CONSEGUIDAS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
	
	@Transactional
	public void RumAchievement(Player p) {
		Statistics stats = statisticsRepo.getStatsByPlayer(p.getId());
		Integer rums = stats.getRumCount();
		if(rums == 5) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.RONES_CONSEGUIDOS_5);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
			
		}else if(rums == 10) {
			Achievement achieve =  new Achievement();
			achieve.setAchieved(true);
			achieve.setAchievementType(AchievementType.RONES_CONSEGUIDOS_10);
			List<Achievement> achievements = stats.getAchievement();
			achievements.add(achieve);
			achievementRepo.save(achieve);
			stats.setAchievement(achievements);
		}
		statisticsRepo.save(stats);
	}
  
}
