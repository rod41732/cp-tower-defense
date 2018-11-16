package model.tower;


import buff.MoveSpeedBuff;
import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.IceProjectile;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.cpp;

public class IceTower extends Tower {

	// TODO : more fields
	
	
	public IceTower(Image image ,double cellX, double cellY, double attack, double cooldown, double range) {
		super(image, cellX, cellY, attack, cooldown, range);
	}
	
	@Override
	public void tryTarget(Monster m) {
		if (m.hasBuff(new MoveSpeedBuff(1, 1))) return ;
		super.tryTarget(m);
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (cooldown > 0) {
			reduceCooldown();
			return ;
		}
		if (target == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, target);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), target.getX(), target.getY(), v);
		

		GameManager.getInstance().getBullets().add(new 
				IceProjectile(x, y, v.first*15, v.second*15, range, attack));
		
		cooldown = attackCooldown;
		clearTarget();
	}
	
	public String toString() {
		return "Normal Tower";
	}
	
}
