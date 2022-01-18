package sevenisles.achievement;

public enum AchievementType {
	
	PARTIDAS_JUGADAS_1("¡Has jugado tu primera partida!"),
	PARTIDAS_JUGADAS_10("Has jugado 10 partidas en total."),
	PARTIDAS_JUGADAS_30("Has jugado 30 partidas en total."),
	PARTIDAS_JUGADAS_50("Has jugado 50 partidas en total."),
	
	PARTIDAS_GANADAS_1("¡Has ganado tu primera partida!"),
	PARTIDAS_GANADAS_10("Has ganado 10 partidas en total."),
	PARTIDAS_GANADAS_30("Has ganado 30 partidas en total."),
	PARTIDAS_GANADAS_50("Has ganado 50 partidas en total."),
	
	PUNTOS_CONSEGUIDOS_20("Has conseguido 20 puntos o más en una partida."),
	PUNTOS_CONSEGUIDOS_40("Has conseguido 40 puntos o más en una partida."),
	PUNTOS_CONSEGUIDOS_60("Has conseguido 60 puntos o más en una partida."),
	
	CALICES_CONSEGUIDOS_10("Has conseguido un total de 10 cálices."),
	CALICES_CONSEGUIDOS_20("Has conseguido un total de 20 cálices."),
	CALICES_CONSEGUIDOS_30("Has conseguido un total de 30 cálices."),
	
	RUBIES_CONSEGUIDOS_10("Has conseguido un total de 10 rubíes."),
	RUBIES_CONSEGUIDOS_20("Has conseguido un total de 20 rubíes."),
	RUBIES_CONSEGUIDOS_30("Has conseguido un total de 30 rubíes."),
	
	DIAMANTES_CONSEGUIDOS_10("Has conseguido un total de 10 diamantes."),
	DIAMANTES_CONSEGUIDOS_20("Has conseguido un total de 20 diamantes."),
	DIAMANTES_CONSEGUIDOS_30("Has conseguido un total de 30 diamantes."),
	
	COLLARES_CONSEGUIDOS_10("Has conseguido un total de 10 collares."),
	COLLARES_CONSEGUIDOS_20("Has conseguido un total de 20 collares."),
	COLLARES_CONSEGUIDOS_30("Has conseguido un total de 30 collares."),
	
	MAPAS_CONSEGUIDOS_10("Has conseguido un total de 10 mapas."),
	MAPAS_CONSEGUIDOS_20("Has conseguido un total de 20 mapas."),
	MAPAS_CONSEGUIDOS_30("Has conseguido un total de 30 mapas."),
	
	CORONAS_CONSEGUIDAS_10("Has conseguido un total de 10 coronas."),
	CORONAS_CONSEGUIDAS_20("Has conseguido un total de 20 coronas."),
	CORONAS_CONSEGUIDAS_30("Has conseguido un total de 30 coronas."),
	
	PISTOLAS_CONSEGUIDAS_10("Has conseguido un total de 10 pistolas."),
	PISTOLAS_CONSEGUIDAS_20("Has conseguido un total de 20 pistolas."),
	PISTOLAS_CONSEGUIDAS_30("Has conseguido un total de 30 pistolas."),
	
	ESPADAS_CONSEGUIDAS_10("Has conseguido un total de 10 espadas."),
	ESPADAS_CONSEGUIDAS_20("Has conseguido un total de 20 espadas."),
	ESPADAS_CONSEGUIDAS_30("Has conseguido un total de 30 espadas."),
	
	RONES_CONSEGUIDOS_10("Has conseguido un total de 10 rones."),
	RONES_CONSEGUIDOS_20("Has conseguido un total de 20 rones."),
	RONES_CONSEGUIDOS_30("Has conseguido un total de 30 rones."),
	
	;
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	private AchievementType(String description) {
		this.description = description;
	}
}
