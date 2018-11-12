package model;


import javafx.scene.image.Image;

public class Tower extends Entity {

	// TODO : more fields
	
	
	protected double rotation;
	protected double attack;
	protected double attackCooldown = 1000;
	protected double cooldown; // in ms ?
	protected double range; 
	
	
	protected Monster target;
	private double minDist;
	
	public Tower(Image img, double cellX, double cellY, double attack, double cooldown, double range) {
		super(img, cellX, cellY, 1, 1);
		this.attack = attack;
		this.cooldown = cooldown;
		this.range = range;
	}
	
	public void clearTarget() {
		target = null;
		minDist = 0;
	}
	
	public boolean isInRange(Monster m) {
		return Double.compare(this.distanceTo(m), range) < 0;
	}
	// change target to monster m if it's closer than current monster
	public void tryTarget(Monster m) {
		// TODO
		clearTarget();
		if ((target == null || Double.compare(distanceTo(m), minDist) < 0) && isInRange(m)){
			target = m;
			minDist = distanceTo(m);
		}
	}
	
	public void fire() {
		if (target == null) return;
		if (cooldown > 0) {
			cooldown -= 16; // 1 tick = 16 ms
			return ;
		}
		cooldown = attackCooldown;
		target.takeDamage(attack);
		System.out.println("Shot =>" + target.toString());
		// need to clear because it will keep firing corpse since tower isn't updated
	}
}
