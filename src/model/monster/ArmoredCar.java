package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import util.cpp.pff;

public class ArmoredCar extends SplittingMonster {

	private static final Image DEFAULT_IMAGE = Images.armoredCar;
	private static final double DEFAULT_HEALTH = 150;
	private static final double DEFAULT_ARMOR = 5;
	private static final double DEFAULT_MS  = 1.0;
	private static final int DEFAULT_MONEY = 12;
	private static final double DEFAULT_SIZE = 0.5;
	private double level = 0;
	
	
	public ArmoredCar(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super("Normal Car", DEFAULT_IMAGE , x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public ArmoredCar(double x, double y, double modifier) {
		super("Normal Car", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void onDeath() {
		pff pos = getPosition();
		for (int i=0; i<5; i++)
			GameManager.getInstance().addMonster(new ArmoredSoldier(
					pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, level));
	}
	
	

}
