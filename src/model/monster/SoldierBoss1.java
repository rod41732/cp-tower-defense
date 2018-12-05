package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SoldierBoss1 extends GroundMonster {

	private static final Image DEFAULT_IMAGE = Images.heavySoldier;
	private static final double DEFAULT_HEALTH = 50;
	private static final double DEFAULT_ARMOR = 4;
	private static final double DEFAULT_MS  = 0.6;
	private static final int DEFAULT_MONEY = 5;
	private static final double DEFAULT_SIZE = 0.2;
	private double level = 0;
	
	
	public SoldierBoss1(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Heavy Soldier", DEFAULT_IMAGE, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public SoldierBoss1(double x, double y, double modifier) {
		super("Heavy Soldier", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));	
		this.level = modifier;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Images.aura[0], getRenderX(), getRenderY(), 128, 128);
		super.render(gc);
	}
	
	@Override
	protected void preUpdate() {
		if (Math.random()*60<0.8) {
			GameManager.getInstance().addMonster(new HeavySoldier(x, y, level));
		}
	}
	

}
