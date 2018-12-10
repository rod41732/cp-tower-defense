package model.monster;

import buff.ArmorBuff;
import constants.Images;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import model.particle.Corpse;

public class TankBoss extends Tank {

	private static final Image DEFAULT_IMAGE = Images.heavyTank;
	private static final double DEFAULT_HEALTH = 500;
	private static final double DEFAULT_ARMOR = 30;
	private static final double DEFAULT_MS  = 0.6;
	private static final int DEFAULT_MONEY = 5;
	
	public TankBoss(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public TankBoss(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	protected void preUpdate() {
		for (Monster ms: GameManager.getInstance().getMonsters()) {
			if (distanceTo(ms) < 2) {
				ms.addBuff(new ArmorBuff(300, 0.5));
			}
		}
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		Particle part = new Corpse(Images.heavyTankDead, x, y, 0, 0);
		part.setRotation(rotation);
		GameManager.getInstance().addParticle(part);
	}
	
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.drawImage(Images.aura[0], getRenderX(), getRenderY(), w, h);
	}
}
