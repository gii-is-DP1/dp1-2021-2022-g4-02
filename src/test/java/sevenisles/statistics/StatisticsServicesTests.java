package sevenisles.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.User;
import sevenisles.user.UserService;
import sevenisles.user.exceptions.DuplicatedUserNameException;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatisticsServicesTests {
	
	private StatisticsService statisticServices;
	private PlayerService  playerService;
	private UserService	userService;
	private CardService CardService;
	private StatusService statusService;
	private GameService gamesService;
	
	@Autowired
	public StatisticsServicesTests(StatisticsService statisticServices, PlayerService  playerService,
			UserService	userService, CardService CardService, StatusService statusService,
			GameService gamesService) {
		this.statisticServices = statisticServices;
		this.playerService = playerService;
		this.userService = userService;
		this.CardService = CardService;
		this.statusService = statusService;
		this.gamesService = gamesService;
	}
	
	Integer playerId;
	Statistics stats = new Statistics();
	Statistics statsp2 = new Statistics();
	Player player = new Player();
	Status newstatus = new Status();
	Status newtwostatus = new Status();
	Player newtwoplayer = new Player();
	Game newgame = new Game();
	
	@BeforeEach
	public void init() throws DataAccessException, DuplicatedUserNameException {
		List<Card> deck =(List<Card>) CardService.cardFindAll();
		
		User user = new User();
        user.setUsername("test");
        user.setFirstName("Perico");
        user.setLastName("El de los palotes");
        user.setPassword("prueba");
        
		
		player.setUser(user);
	    playerId=player.getId();
	    
	    
	    List<Status> statuspone = new ArrayList<>();
	    stats.setPlayer(player);
	    statsp2.setPlayer(newtwoplayer);
	    user.setPlayer(player);
	    userService.saveUser(user);
	    statisticServices.saveStatistic(stats);
	    statisticServices.saveStatistic(statsp2);
	    player.setStatistics(stats);
	    newtwoplayer.setStatistics(statsp2);
	    playerService.savePlayer(player);
	    playerService.savePlayer(newtwoplayer);
	    
	    Card card = CardService.findCardById(1).get();
	    Card cardcaliz = CardService.findCardById(28).get();
	    Card cardrubi = CardService.findCardById(31).get();
	    Card carddiamante = CardService.findCardById(34).get();
	    Card cardcollar = CardService.findCardById(40).get();
	    Card cardcorona = CardService.findCardById(48).get();
	    Card cardpistola = CardService.findCardById(53).get();
	    Card cardespada = CardService.findCardById(59).get();
	    Card cardmapa = CardService.findCardById(44).get();
	    Card cardron = CardService.findCardById(65).get();
	    List<Card> cardspl1 = new ArrayList<>();
	    cardspl1.add(card); cardspl1.add(cardcaliz);cardspl1.add(cardrubi);cardspl1.add(carddiamante);
	    cardspl1.add(cardcollar);cardspl1.add(cardcorona);cardspl1.add(cardpistola);cardspl1.add(cardespada);
	    cardspl1.add(cardron);cardspl1.add(cardmapa);
	    
	    Card cardpl2 = CardService.findCardById(2).get();
	    List<Card> cardspl2 = new ArrayList<>();
	    cardspl2.add(cardpl2);
	    newstatus.setPlayer(player);
	    newstatus.setCards(cardspl1);
	    newstatus.setScore(200);
	    
	    newtwostatus.setPlayer(newtwoplayer);
	    newtwostatus.setCards(cardspl2);
	    newtwostatus.setScore(10);
	    statusService.saveStatus(newstatus);
	    statuspone.add(newstatus);
	    player.setStatus(statuspone);
	    statusService.saveStatus(newtwostatus);
	    
	    List<Status> statusgame = new ArrayList<>();
	    statusgame.add(newstatus);
	    statusgame.add(newtwostatus);
	    newgame.setStatus(statusgame);
	    newgame.setCards(deck);
	    newgame.setStartHour(LocalTime.of(13,5, 10));
	    newgame.setGameMode(1);
	    newgame.setEndHour(LocalTime.of(15,20,40));
	    gamesService.saveGame(newgame);
	}
	
	@Test
	public void statisticsCountTest() {
		Integer countstatistics = statisticServices.statisticsCount();
		Integer countplayers = playerService.playerCount();
		assertEquals(countstatistics,countplayers);
	
	}
	
	@Test
	public void statisticsFindAllTest() {
		Integer countstatistics = statisticServices.statisticsCount();
		Iterator<Statistics> stats = statisticServices.statisticsFindAll().iterator();
		List<Statistics> lstats = StreamSupport.stream(Spliterators.spliteratorUnknownSize(stats,Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(countstatistics,lstats.size());
	}
	
	@Test
	public void findStatisticsByPlayerTest() {
		Integer playerId = player.getId();
		Optional<Statistics> stat = statisticServices.getStatsByPlayer(playerId);
		assertEquals(playerId,stat.get().getPlayer().getId());
	}
	
	@Test
	public void findStatisticsByIdTest() {
		Integer statid = stats.getId();
		Statistics stat = statisticServices.findStatisticsById(statid).get();
		assertEquals(stat.getId(),statid);
	}
	
	
	@Test
	public void setStatisticsTest() {
		
		//Obtenemos las estadísticas previas del jugador
		Integer playerId = player.getId();
		Statistics statsplonebef = statisticServices.getStatsByPlayer(playerId).get();
		Integer gamesPlayed = statsplonebef.getGamesPlayed();
		Integer ChaliceCount = statsplonebef.getChaliceCount();
		
		
		statisticServices.setStatistics(newstatus,newgame);
		
		Statistics statsploneaft = statisticServices.getStatsByPlayer(playerId).get();
		assertEquals(statsploneaft.getTotalTime(), Duration.between(newgame.getStartHour(), newgame.getEndHour()).toMinutes());
		assertEquals(statsploneaft.getChaliceCount(),ChaliceCount+1);
		assertEquals(gamesPlayed+1,statsploneaft.getGamesPlayed());
	}
	
	//Igual que el de arriba pero con tiempo de finalización pasando de las 00:00
	@Test
	public void setStatisticsTest2() {
		
		//Obtenemos las estadísticas previas del jugador
		Integer playerId = player.getId();
		Statistics statsplonebef = statisticServices.getStatsByPlayer(playerId).get();
		Integer gamesPlayed = statsplonebef.getGamesPlayed();
		Integer ChaliceCount = statsplonebef.getChaliceCount();
		newgame.setEndHour(LocalTime.of(2, 30,40));
		
		statisticServices.setStatistics(newstatus,newgame);
		
		Statistics statsploneaft = statisticServices.getStatsByPlayer(playerId).get();
		assertEquals(statsploneaft.getTotalTime(), Duration.between(newgame.getStartHour(), LocalTime.of(23, 59, 59)).toMinutes() + Duration.between(LocalTime.of(0,0,0), newgame.getEndHour()).toMinutes());
		assertEquals(statsploneaft.getChaliceCount(),ChaliceCount+1);
		assertEquals(gamesPlayed+1,statsploneaft.getGamesPlayed());
	}
	
	@Test
	public void getRankingTest() {
		List<Statistics> stats = statisticServices.getRanking();
		Statistics statfirstplayer = stats.get(0);
		Statistics statsecondfirstplayer = stats.get(1);
		assertTrue(statfirstplayer.getTotalScore()>=statsecondfirstplayer.getTotalTime());
	}
}
