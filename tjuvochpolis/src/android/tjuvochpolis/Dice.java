package android.tjuvochpolis;

import java.util.Random;

import android.util.Log;

public class Dice {
	
	private static Dice diceObject;
	private Random mGenerator;
	// Note that the constructor is private
	private Dice() {
		// Optional Code
		 
	}
	public static Dice getDice() {
		if (diceObject == null) {
			diceObject = new Dice();
			diceObject.mGenerator = new Random();
		}
		return diceObject;
	}
	
	public int rollDice()
	{	
		int roll = mGenerator.nextInt(6) + 1;
		return roll;
	}

	
}
