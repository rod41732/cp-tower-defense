package model;

import javafx.scene.image.Image;
import util.GameUtil;

public abstract class Projectile extends Entity implements IExpirable {
	
	// constant
	protected double vx, vy;
	protected double maxDistance = 3;
	protected double maxAge = 5000; // in millisec
	protected int targetFlag;
	// state
	protected double distance = 0;
	protected double age = 0;
	protected boolean isExpired = false;
	
	
	public Projectile(Image image, double x, double y,
			double vx, double vy, double maxDistance) {
		super(image, x, y, 3, 0.3); // default size ?
		this.vx = vx;
		this.vy = vy;
		this.maxDistance = maxDistance;
		this.targetFlag = 3;
	}
	
	public boolean shouldCollide(Monster m) {
		return !isExpired() && isCollideWith(m) && !m.isDead() && (m.getTargetFlag() & this.targetFlag) != 0;
	}
	
	// return true if projectile "isExpired" after colliding (usually true) but not for piercing shot
	public abstract boolean collideWith(Monster m);
	
	
	public void onTick() {
		preUpdate();
		move();
		age();
	}
	
	public void move() {
		x += vx/60;
		y += vy/60;
		this.rotation = Math.toDegrees(Math.atan2(vy, vx));
		distance += GameUtil.distance(0, 0, vx/60, vy/60);
	}
	
	public void preUpdate() {
		
	}
	
	private void age() {
		age += 1000./60;
	}
	
	public boolean isExpired() {
		return isExpired || distance >= maxDistance || age >= maxAge;
	}

	public void forceExpire() {
		isExpired = true;
	}
}
