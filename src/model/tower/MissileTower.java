package model.tower;


import java.util.Comparator;
import java.util.PriorityQueue;

import constants.Images;
import constants.Sounds;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.HomingMissle;
import model.projectile.Missile;
import util.GameUtil;
import util.cpp;

public class MissileTower extends Tower {

	private static final Image DEFAULT_IMAGE = Images.bombTower;	
	private double splashRadius;
	private PriorityQueue<Monster> targets;
	private int maxTargets = 2;
	
	private class MonsterDistanceComparator implements Comparator<Monster> {
		public Tower thisTower;
		
		public MonsterDistanceComparator(Tower thisTower) {
			this.thisTower = thisTower;
		}
		@Override
		public int compare(Monster o1, Monster o2) {
			return -Double.compare(o1.distanceTo(thisTower), o2.distanceTo(thisTower)); // we want max heap instead of min heap
		}
		
	}
	
	public MissileTower(double cellX, double cellY) {
		super("Missile", DEFAULT_IMAGE, cellX, cellY);
		this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", level);
		this.targets = new PriorityQueue<>(new MonsterDistanceComparator(this));
	}

	
	@Override
	public void tryTarget(Monster m) {
		targets.add(m);				
		if (targets.size() > maxTargets) {
			targets.remove();
		}
	}
	
	@Override
	public boolean upgrade() throws FullyUpgradedException {
		if (super.upgrade()) {
			this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", level);
			this.maxTargets++;
		}
		return true;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		Sounds.missileLaunch.play();
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		System.out.println("my target is" + currentTarget);
		for (Monster m: targets) {
			GameManager.getInstance().addProjectile(new 
					HomingMissle(x, y, v.first*9, v.second*9, range, attack, splashRadius, m));			
		}
		currentCooldown = attackCooldown;
	}
}
