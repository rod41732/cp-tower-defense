package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SoldierBoss extends Soldier {

	private static final Image DEFAULT_IMAGE = Images.heavySoldier;
	private static final double DEFAULT_HEALTH = 50;
	private static final double DEFAULT_ARMOR = 4;
	private static final double DEFAULT_MS  = 0.6;
	private static final int DEFAULT_MONEY = 5;
	
	public SoldierBoss(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public SoldierBoss(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));	
		this.level = modifier;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Images.aura[0], getRenderX(), getRenderY(), 64, 64);
		super.render(gc);
	}
	
	@Override
	protected void preUpdate() {
		if (Math.random()*60<0.8) {
			GameManager.getInstance().addMonster(new SoldierHeavy(x, y, level));
		}
	}
	

}
