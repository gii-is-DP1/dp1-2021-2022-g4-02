package sevenisles.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameControllerException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7118629069757540929L;
	
	public GameControllerException(String message) {
		
		super(message);
		
	}

}
