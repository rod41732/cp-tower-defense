package model.tower;


import javax.management.RuntimeErrorException;

import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.Bomb;
import util.GameUtil;
import util.cpp;

public class BombTower extends Tower {

	private static final double BASE_ATTACK = 12;
	private static final double BASE_COOLDOWN = 1250;
	private static final double BASE_RANGE = 3.5;
	private static final Image DEFAULT_IMAGE = Images.bombTower;
	
	private static final double RADIUS = 2;
	
	public BombTower(Image image ,double cellX, double cellY, double attack, double cooldown, double range) {
		super(image, cellX, cellY, attack, cooldown, range);
	}
	
	public BombTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, BASE_ATTACK, BASE_COOLDOWN, BASE_RANGE);
	}

	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
//		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
//				getX(), getY(), currentTarget.getX(), currentTarget.getY(), v);
		rotateTo(currentTarget);
		System.out.println(rotation);
		GameManager.getInstance().getBullets().add(new 
				Bomb(Images.bomb, x, y, v.first*9, v.second*9, range, attack, RADIUS));
		
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Bomb Tower";
	}
	
	@Override
	public String description() {
		return super.description() + String.format("Explosion radius: %.2f\n", RADIUS);
	}
	
}
