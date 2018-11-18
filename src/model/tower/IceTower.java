package model.tower;


import buff.MoveSpeedBuff;
import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.IceProjectile;
import util.GameUtil;
import util.cpp;

public class IceTower extends Tower {

	private static final double BASE_ATTACK = 3;
	private static final double BASE_COOLDOWN = 750;
	private static final double BASE_RANGE = 3.5;
	private static final Image DEFAULT_IMAGE = Images.iceTower;
	
	public IceTower(Image image, double cellX, double cellY, double attack, double cooldown, double range) {
		super(image, cellX, cellY, attack, cooldown, range);
	}
	
	public IceTower(double cellX, double cellY, double attack, double cooldown, double range) {
		super(DEFAULT_IMAGE, cellX, cellY, attack, cooldown, range);
	}
	
	public IceTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, BASE_ATTACK, BASE_COOLDOWN, BASE_RANGE);
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
		if (currentTarget == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), currentTarget.getX(), currentTarget.getY(), v);
		
		rotateTo(currentTarget);
		GameManager.getInstance().getProjectiles().add(new 
				IceProjectile(x, y, v.first*15, v.second*15, range, attack));
		
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Ice Tower";
	}
}
