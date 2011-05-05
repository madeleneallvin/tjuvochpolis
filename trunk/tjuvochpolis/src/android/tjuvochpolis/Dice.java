package android.tjuvochpolis;

import java.util.Random;

public class Dice {

	private static Dice diceObject;
	private Random mGenerator;

	private Dice() {

	}
	public static Dice getDice() {
		if (diceObject == null) {
			diceObject = new Dice();
			diceObject.mGenerator = new Random();
		}
		return diceObject;
	}

	public int rollDice(){
		int roll = mGenerator.nextInt(6) + 1;
		return roll;
	}

}
