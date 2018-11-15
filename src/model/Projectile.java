package model;

import javafx.scene.image.Image;
import util.GameUtil;

public abstract class Projectile extends Entity implements IExpirable {
	
	// constant
	protected double vx, vy;
	protected double maxDistance = 3;
	protected double maxAge = 5000; // in millisec
	
	// state
	protected double distance = 0;
	protected double age = 0;
	protected boolean isExpired = false;
	
	public Projectile(Image image, double x, double y,
			double vx, double vy, double maxRange) {
		super(image, x, y, 0.3); // default size ?
		this.vx = vx;
		this.vy = vy;
		this.maxDistance = maxRange;
	}
	
	
	public void move() {
		x += vx/60;
		y += vy/60;
		distance += GameUtil.distance(0, 0, vx/60, vy/60);
		age += 1000./60;
	}
	
	public boolean shouldCollide(Monster m) {
		return !isExpired() && isCollideWith(m) && !m.isDead();
	}
	
	// return true if projectile "isExpired" after colliding (usually true) but not for piercing shot
	public abstract boolean collideWith(Monster m);
	
	public boolean isExpired() {
		return isExpired || Double.compare(distance, maxDistance) > 0;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
}
