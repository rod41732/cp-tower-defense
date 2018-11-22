package model.tower;


import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.NormalProjectile;
import model.projectile.SplittingProjectile;
import util.GameUtil;
import util.cpp;

public class NormalTower extends Tower {

	// TODO : more fields
	private static final double BASE_ATTACK = 7;
	private static final double BASE_COOLDOWN = 500;
	private static final double BASE_RANGE = 4.5;
	private static final Image DEFAULT_IMAGE = Images.normalTower;
	private static final int BASE_PRICE = 7;
		
	public NormalTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, BASE_ATTACK, BASE_COOLDOWN, BASE_RANGE);
		this.price = BASE_PRICE;
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
//		GameManager.getInstance().getProjectiles().add(new 
//				NormalProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, 10));
		GameManager.getInstance().addProjectile(new 
				SplittingProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, 10, 
						Math.min(distanceTo(currentTarget)*0.6, distanceTo(currentTarget)-currentTarget.getSize()-size)));
		
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Normal Tower";
	}
}
