package sevenisles.game;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		//Game game = (Game) obj;
		
//		// full game
//		if(!game.isNotFull()) {
//			errors.reject("This game is already full.");
//		}

		// game ready to start
//		if(!game.isReadyToStart()) {
//			errors.reject("The game must have between 2 and 4 players to start.");
//		}
		
	}

	/**
	 * This Validator validates *just* Game instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.isAssignableFrom(clazz);
	}
}
