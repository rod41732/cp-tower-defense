package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class ArmoredSoldier extends GroundMonster {

	private static final Image DEFAULT_IMAGE = Images.armoredSoldier;
	private static final double DEFAULT_HEALTH = 30;
	private static final double DEFAULT_ARMOR = 2;
	private static final double DEFAULT_MS  = 0.75;
	private static final int DEFAULT_MONEY = 5;
	private static final double DEFAULT_SIZE = 0.2;
	
	
	
	public ArmoredSoldier(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Armored Soldier", DEFAULT_IMAGE, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public ArmoredSoldier(double x, double y, double modifier) {
		super("Armored Soldier", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		
	}
	

}
