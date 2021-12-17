package sevenisles.islandStatus.exceptions;

public class IslandStatusNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IslandStatusNotFoundException() {
		super("Estado no encontrado");
	}
	
}
