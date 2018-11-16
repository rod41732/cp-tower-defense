package model.tower;


import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.FireProjectile;
import util.GameUtil;
import util.cpp;

public class FireTower extends Tower {

	// TODO : more fields
	private static final double BASE_FIRE_ATTACK = 10;
	private static final double BASE_COOLDOWN = 750;
	private static final double BASE_RANGE = 3.5;
	private static final double FIRE_RADIUS = 0.7;
	private static final Image DEFAULT_IMAGE = Images.fireTower;
	
	private double fireDamage;
	
	public FireTower(Image image ,double cellX, double cellY, double fireAttack, double cooldown, double range) {
		super(image, cellX, cellY, fireAttack, cooldown, range);
	}
	
	public FireTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, BASE_FIRE_ATTACK, BASE_COOLDOWN, BASE_RANGE);
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
//		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
//				getX(), getY(), currentTarget.getX(), currentTarget.getY(), v);
		GameManager.getInstance().getBullets().add(new 
				FireProjectile(Images.bullet3, x, y, v.first*9, v.second*9, range, attack, FIRE_RADIUS));
		currentCooldown = attackCooldown;
	}
	@Override 
	public String toString() {
		return "FireTower";
	}
	
	@Override 
	public String description() {
		return super.description() + String.format("Fire radius: %.2f\n", FIRE_RADIUS);
	}
	
}
