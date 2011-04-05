package android.tjuvochpolis;

import java.util.Random;

import android.util.Log;

public class Dice {
	
	private int value;
	private static Dice diceObject;
	// Note that the constructor is private
	private Dice() {
		// Optional Code
		this.value = 0;
	}
	public static Dice getDice() {
		if (diceObject == null) {
			diceObject = new Dice();
		}
		return diceObject;
	}
	
	public int rollDice()
	{
		Random generator = new Random();
		int r = generator.nextInt(6) + 1;
		return r;
	}

	
}
