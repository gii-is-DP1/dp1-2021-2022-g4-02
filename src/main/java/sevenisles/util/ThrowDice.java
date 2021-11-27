package sevenisles.util;

import java.util.concurrent.ThreadLocalRandom;

public class ThrowDice {
		
	public static Integer throwDice(int range) {
		
		return ThreadLocalRandom.current().nextInt(1, 7);
	}
}
