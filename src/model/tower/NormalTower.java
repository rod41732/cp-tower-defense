package model.tower;


import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.cpp;

public class NormalTower extends Tower {

	// TODO : more fields
	
	
	protected double attack;

	
	public NormalTower(Image image ,double cellX, double cellY, double attack, double cooldown, double range) {
		super(image, cellX, cellY, cooldown, range);
		this.attack = attack;
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (cooldown > 0) {
			cooldown -= 16; // 1 tick = 16 ms
			return ;
		}
		if (target == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, target);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), target.getX(), target.getY(), v);
		

		GameManager.getInstance().getBullets().add(new 
				NormalProjectile(Images.bullet1,x, y, v.first*15, v.second*15, range, 10));
		
		cooldown = attackCooldown;
		clearTarget();
	}
	
	public String toString() {
		return String.format("Normal Tower R=%.1f A=%.1f C=%.1f", range, attack, cooldown);
	}
	
}
