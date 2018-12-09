package model.monster;

import java.lang.reflect.InvocationTargetException;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import util.cpp.pff;

public class HeavyCar extends Car {

	private static final Image DEFAULT_IMAGE = Images.armoredCar;
	private static final double DEFAULT_HEALTH = 500;
	private static final double DEFAULT_ARMOR = 20;
	private static final double DEFAULT_MS  = 0.5;
	private static final int DEFAULT_MONEY = 15;
	
	
	
	public HeavyCar(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super(DEFAULT_IMAGE , x, y, health, armor, moveSpeed, money, HeavySoldier.class);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public HeavyCar(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2),
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		pff pos = getPosition();
		for (int i=0; i<5; i++)
			GameManager.getInstance().addMonster(new HeavySoldier(
					pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, level));
	}
	

}
