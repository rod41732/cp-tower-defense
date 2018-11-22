package model.tower;


import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.FireProjectile;
import util.GameUtil;
import util.cpp;

public class FireTower extends Tower {

	// TODO : more fields like fire duration etc
	private static final double BASE_ATTACK = 5;
	private static final double BASE_COOLDOWN = 750;
	private static final double BASE_RANGE = 3.5;
	private static final double FIRE_RADIUS = 0.7;
	private static final Image DEFAULT_IMAGE = Images.fireTower;
	private static final double BASE_FIRE_DAMAGE = 10;
	private static final int BASE_PRICE = 35;
	
	
	private double fireDamage;
	
	public FireTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, BASE_ATTACK, BASE_COOLDOWN, BASE_RANGE);
		this.fireDamage = BASE_FIRE_DAMAGE;
		this.price = BASE_PRICE;
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				FireProjectile(x, y, v.first*9, v.second*9, range, attack, FIRE_RADIUS, fireDamage));
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
