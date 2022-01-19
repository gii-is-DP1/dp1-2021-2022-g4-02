package sevenisles.statistics;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.card.Card;
import sevenisles.card.CardType;
import sevenisles.game.Game;
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.player.Player;
import sevenisles.status.Status;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@Transactional(readOnly = true)
	public Integer statisticsCount() {
		return (int) statisticsRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Statistics> statisticsFindAll() {
		return statisticsRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Statistics> findStatisticsById(int id) throws GameControllerException { 
		return statisticsRepository.findById(id);
	}
	
	@Transactional
	public void saveStatistic(Statistics statisticToUpdate) throws DataAccessException {
		statisticsRepository.save(statisticToUpdate);
	}
	
	@Transactional
    public Optional<Statistics> getStatsByPlayer(Integer playerId) {
        return statisticsRepository.getStatsByPlayer(playerId);
    }
	
	@Transactional
	public void setStatistics(Status s, Game game) {
		
		Integer score = s.getScore();
		Player player =  s.getPlayer();
		
		Statistics playerStatistics =  findStatisticsById(player.getId()).get();
		playerStatistics.setGamesPlayed(playerStatistics.getGamesPlayed()+1);
		playerStatistics.setTotalScore(playerStatistics.getTotalScore()+score);
		Integer numGamesPlayed = playerStatistics.getGamesPlayed();
		Integer totalScore = playerStatistics.getTotalScore();
		Double averageScore = (double) (totalScore/numGamesPlayed);
		playerStatistics.setAverageScore(averageScore);
		
		List<Card> cards = s.getCards();
		
		for(int i=0; i<cards.size(); i++) {
			CardType ct = cards.get(i).getCardType();
			if(ct.equals(CardType.DOBLON)) {
				playerStatistics.setDoubloonCount(playerStatistics.getDoubloonCount()+1);
			}else if(ct.equals(CardType.CALIZ)){
				playerStatistics.setChaliceCount(playerStatistics.getChaliceCount()+1);
			}else if(ct.equals(CardType.DIAMANTE)){
				playerStatistics.setDiamondCount(playerStatistics.getDiamondCount()+1);
			}else if(ct.equals(CardType.RUBI)){
				playerStatistics.setRubyCount(playerStatistics.getRubyCount()+1);
			}else if(ct.equals(CardType.RON)){
				playerStatistics.setRumCount(playerStatistics.getRumCount()+1);
			}else if(ct.equals(CardType.PISTOLA)){
				playerStatistics.setGunCount(playerStatistics.getGunCount()+1);
			}else if(ct.equals(CardType.COLLAR)){
				playerStatistics.setNecklaceCount(playerStatistics.getNecklaceCount()+1);
			}else if(ct.equals(CardType.ESPADA)){
				playerStatistics.setSwordCount(playerStatistics.getSwordCount()+1);
			}else if(ct.equals(CardType.MAPA)){
				playerStatistics.setMapCount(playerStatistics.getMapCount()+1);
			}else {
				playerStatistics.setCrownCount(playerStatistics.getCrownCount()+1);
			}
		}
		
		long gameTime = Duration.between(game.getStartHour(), game.getEndHour()).toMinutes();
		playerStatistics.setTotalTime(playerStatistics.getTotalTime()+gameTime);
		long averageTime = playerStatistics.getTotalTime()/playerStatistics.getGamesPlayed();
		playerStatistics.setAverageTime(averageTime);
		
		saveStatistic(playerStatistics);
		System.out.println("++++++++++++++++"+playerStatistics.getGamesPlayed());
	}
	
	@Transactional
	public List<Statistics> getRanking(){
		return statisticsRepository.getRanking();
	}
}
