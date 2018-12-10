package model.monster;

import constants.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CarBoss extends Car {

	private static final Image DEFAULT_IMAGE = Images.heavyCar;
	private static final double DEFAULT_HEALTH = 800;
	private static final double DEFAULT_ARMOR = 45;
	private static final double DEFAULT_MS  = 1.3;
	private static final int DEFAULT_MONEY = 100;
	
	public CarBoss(double x, double y, double health, double armor, double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money, CarHeavy.class);
	}
	
	public CarBoss(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2),
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Images.aura[0], getRenderX(), getRenderY(), w, h);
		super.render(gc);
	}
}
