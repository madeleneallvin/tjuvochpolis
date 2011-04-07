package android.tjuvochpolis;

import java.util.Random;

public class Dice {
	
	private static Dice diceObject;
	private Random mGenerator;
	// Note that the constructor is private
	private Dice() {
		// Optional Code
		 mGenerator = new Random();
	}
	public static Dice getDice() {
		if (diceObject == null) {
			diceObject = new Dice();
		}
		return diceObject;
	}
	
	public int rollDice()
	{
		return mGenerator.nextInt(6) + 1;
	}

	
}
