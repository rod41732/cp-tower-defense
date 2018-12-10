package model.projectile;

import constants.Images;
import javafx.scene.image.Image;
import model.Monster;

public class GroundProjectile extends NormalProjectile{
	
	public GroundProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.groundBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.targetFlag = 1;
	}
	
}
