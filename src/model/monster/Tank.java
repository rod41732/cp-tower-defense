package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.FadingParticle;
import model.Particle;
import util.GameUtil;

public abstract class Tank extends GroundMonster {

	private static final double DEFAULT_SIZE = 0.7;
	protected double level = 0;
	private double moveDist = 0;
	
	public Tank(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Tank", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void move() {
		super.move();
		moveDist += GameUtil.distance(0, 0, vx/60, vy/60);
		if (moveDist > 0.08) {
			Particle part = new FadingParticle(Images.tankTrack, x, y, 0, 0, 2000);
			part.setRotation(rotation);
			GameManager.getInstance().addParticle(part);
		}
	}
	

}
