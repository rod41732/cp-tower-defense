package model.tower;


import constants.Images;
import controller.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.Bomb;
import util.GameUtil;
import util.cpp;

public class BombTower extends Tower {
	
	public static String TOWER_NAME = "Bomb Tower";

	private static final double[] ATTACK_VALUES = {15, 20, 25, 35, 50};
	private static final double[] COOLDOWN_VALUES = {1250, 1150, 1000, 950, 700};
	private static final double[] RANGE_VALUES = {3.5, 4, 4, 4.5, 4.5};
	private static final Image DEFAULT_IMAGE = Images.bombTower;
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	
	private static final double RADIUS = 2;
		
	public BombTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
		this.price = PRICE_VALUES[0];
		this.level = 1;
	}

	@Override
	public void upgrade() throws FullyUpgradedException {
		if (level == 5) {
			throw new FullyUpgradedException();
		}
		else {
			level += 1;
			this.attack = ATTACK_VALUES[level-1];
			this.attackCooldown = COOLDOWN_VALUES[level-1];
			this.range = RANGE_VALUES[level-1];
			this.price += PRICE_VALUES[level-1];
		}
	}
	
	@Override
	public int getUpgradePrice() {
		return level == 5 ? -1 : PRICE_VALUES[level];
	}
	

	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
//		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
//				getX(), getY(), currentTarget.getX(), currentTarget.getY(), v);
		rotateTo(currentTarget);
		System.out.println(rotation);
		GameManager.getInstance().addProjectile(new 
				Bomb(Images.bomb, x, y, v.first*9, v.second*9, range, attack, RADIUS));
		
		currentCooldown = attackCooldown;
	}
	
	
	@Override
	public String description() {
		return super.description() + String.format("Explosion radius: %.2f\n", RADIUS);
	}
	
}
