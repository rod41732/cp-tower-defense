package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class NormalTank extends GroundMonster {

	private static final Image DEFAULT_IMAGE = Images.normalTank;
	private static final double DEFAULT_HEALTH = 250;
	private static final double DEFAULT_ARMOR = 0;
	private static final double DEFAULT_MS  = 1.3;
	private static final int DEFAULT_MONEY = 3;
	private static final double DEFAULT_SIZE = 0.2;
	
	
	
	public NormalTank(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Normal Tank", DEFAULT_IMAGE, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public NormalTank(double x, double y, double modifier) {
		super("Normal Tank", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5*modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		
	}
	

}
