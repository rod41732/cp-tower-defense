package model.projectile;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import util.GameUtil;
import util.cpp;

public class SplittingProjectile extends NormalProjectile {
	
	protected double splitDistance;
	
	
	public SplittingProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double splitDistance) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.splitDistance = splitDistance;
		this.damage = damage;
		this.targetFlag = 3;
	}

	@Override
	public void preUpdate() {
		
		if (distance >= splitDistance) {
			cpp.pff v1 = GameUtil.rotateVector(vx, vy, 20);
			cpp.pff v2 = GameUtil.rotateVector(vx, vy, -20);
			forceExpire();
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, vx, vy,maxDistance-splitDistance, damage));
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, v1.first, v1.second, maxDistance-splitDistance, damage));
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, v2.first, v2.second, maxDistance-splitDistance, damage));
		}
	}
	

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			preUpdate();
		}
		return isExpired();
	}
	
	
	
	
}
