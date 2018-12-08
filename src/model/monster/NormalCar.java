package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import util.cpp.pff;

public class NormalCar extends Car {

	private static final Image DEFAULT_IMAGE = Images.normalCar;
	private static final double DEFAULT_HEALTH = 100;
	private static final double DEFAULT_ARMOR = 0;
	private static final double DEFAULT_MS  = 1.3;
	private static final int DEFAULT_MONEY = 10;
	
	
	public NormalCar(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public NormalCar(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5*modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void onDeath() {
		super.onDeath(); 
		pff pos = getPosition();
		for (int i=0; i<5; i++)
			GameManager.getInstance().addMonster(new NormalSoldier(
					pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, level));
	}
	
	

}
