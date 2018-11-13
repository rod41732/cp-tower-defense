package model;

import javafx.scene.image.Image;
import util.GameUtil;

public class Projectile extends Entity implements IExpirable {
	
	protected double vx, vy;
	protected double damage;
	protected double maxRange = 3;
	protected double nowDist = 0;
	protected boolean isExpired = false;
	
	public Projectile(Image image, double x, double y, double vx, double vy, double damage) {
		super(image, x, y, 0.1); // default size ?
		this.vx = vx;
		this.vy = vy;
		this.damage = damage;
	}
	
	public void move() {
		x += vx/60;
		y += vy/60;
		nowDist += GameUtil.distance(0, 0, vx/60, vy/60);
	}
	
	public boolean collideWith(Monster m) {
		if (isExpired) return false;
//		System.out.println(distanceTo(m) + "proj " + this + " vs mon " + m + "dist = ");
		if (isCollideWith(m)) {
			m.takeDamage(damage);
			isExpired = true;
			return true;
		}
		return false;
	}

	public boolean isExpired() {
		return isExpired || Double.compare(nowDist, maxRange) > 0;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
}
