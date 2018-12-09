package model.tower;


import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import model.Tower;

public class BombTower extends Tower {
	
	public static String TOWER_NAME = "Bomb Tower";
	
	public BombTower(double cellX, double cellY) {
		super("Bomb", cellX, cellY);
		this.level = 1;
		this.targetFlag = 1;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		Sounds.bigExplosion.play();
		for (Monster ms: GameManager.getInstance().getMonsters()) {
			if (isInRange(ms)) {
				ms.takeDamage(attack);
			}
		}
		GameManager.getInstance().addParticle(new Particle(Images.boom, x, y, 0, 0, 1200.0));
		currentCooldown = attackCooldown;
	}
	
}
