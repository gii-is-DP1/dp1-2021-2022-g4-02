package sevenisles.util;

import java.util.List;
import java.util.Map;

import sevenisles.card.Card;
import sevenisles.status.Status;

public interface ScoreCount {
	
	public Map<String, List<Card>> normalGameMode(Status status);
	public Map<String, List<Card>> secondaryGameMode(Status status);

}
