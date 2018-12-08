package model.tower;


import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import model.Tower;
import model.projectile.Missile;
import util.GameUtil;
import util.cpp;

public class BombTower extends Tower {
	
	public static String TOWER_NAME = "Bomb Tower";
	
	private static final Image DEFAULT_IMAGE = Images.bombTower;
	
	public BombTower(double cellX, double cellY) {
		super("Bomb", DEFAULT_IMAGE, cellX, cellY);
		this.level = 1;
		this.targetFlag = 1;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		for (Monster ms: GameManager.getInstance().getMonsters()) {
			if (isInRange(ms)) {
				ms.takeDamage(attack);
			}
		}
		GameManager.getInstance().addParticle(new Particle(Images.boom, x, y, 0, 0, 1200));
		currentCooldown = attackCooldown;
	}
	
}
