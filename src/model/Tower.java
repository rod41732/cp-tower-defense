package model;

import javafx.scene.image.Image;
import util.GameUtil;

public class Tower extends Entity {

	// TODO : more fields
	
	
	private double rotation;
	private double attack;
	private double attackCooldown = 1000;
	private double cooldown; // in ms ?
	private double range; 
	
	
	private Monster target;
	private double targetDist;
	
	public Tower(Image img, double cellX, double cellY) {
		super(img, cellX, cellY, 1, 1);
	}
	
	public void clearTarget() {
		target = null;
		targetDist = 0;
	}
	
	// change target to monster m if it's closer than current monster
	public void tryTarget(Monster m) {
		// TODO
		clearTarget();
		System.out.println("tr");
		if (target == null || targetDist > GameUtil.distance(this, m)){
//			if (m.isDead()) return ; // don't shoot dead monster
			target = m;
			targetDist = GameUtil.distance(this, m);
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
		
	}
}
