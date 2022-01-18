package sevenisles.achievement;

public enum AchievementType {
	
	PARTIDAS_JUGADAS_1("¡Has jugado tu primera partida!"),
	PARTIDAS_JUGADAS_5("Has jugado 5 partidas en total."),
	
	PARTIDAS_GANADAS_1("¡Has ganado tu primera partida!"),
	PARTIDAS_GANADAS_5("Has ganado 5 partidas en total."),
	
	PUNTOS_CONSEGUIDOS_40("Has conseguido 40 puntos o más en una partida."),
	PUNTOS_CONSEGUIDOS_60("Has conseguido 60 puntos o más en una partida."),
	
	CALICES_CONSEGUIDOS_5("Has conseguido un total de 5 cálices."),
	CALICES_CONSEGUIDOS_10("Has conseguido un total de 10 cálices."),
	
	RUBIES_CONSEGUIDOS_5("Has conseguido un total de 5 rubíes."),
	RUBIES_CONSEGUIDOS_10("Has conseguido un total de 10 rubíes."),
	
	DIAMANTES_CONSEGUIDOS_5("Has conseguido un total de 5 diamantes."),
	DIAMANTES_CONSEGUIDOS_10("Has conseguido un total de 10 diamantes."),
	
	COLLARES_CONSEGUIDOS_5("Has conseguido un total de 5 collares."),
	COLLARES_CONSEGUIDOS_10("Has conseguido un total de 10 collares."),
	
	MAPAS_CONSEGUIDOS_5("Has conseguido un total de 5 mapas."),
	MAPAS_CONSEGUIDOS_10("Has conseguido un total de 10 mapas."),
	
	CORONAS_CONSEGUIDAS_5("Has conseguido un total de 5 coronas."),
	CORONAS_CONSEGUIDAS_10("Has conseguido un total de 10 coronas."),
	
	PISTOLAS_CONSEGUIDAS_5("Has conseguido un total de 5 pistolas."),
	PISTOLAS_CONSEGUIDAS_10("Has conseguido un total de 10 pistolas."),
	
	ESPADAS_CONSEGUIDAS_5("Has conseguido un total de 5 espadas."),
	ESPADAS_CONSEGUIDAS_10("Has conseguido un total de 10 espadas."),
	
	RONES_CONSEGUIDOS_5("Has conseguido un total de 5 rones."),
	RONES_CONSEGUIDOS_10("Has conseguido un total de 10 rones."),
	
	;
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	private AchievementType(String description) {
		this.description = description;
	}
}
