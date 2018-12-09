package model.tower;


import buff.AttackSpeedBuff;
import buff.DamageBuff;
import buff.RangeBuff;
import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Tower;

public class BuffTower extends Tower {

	
	
	public BuffTower(double cellX, double cellY) {
		super("Buff", cellX, cellY);
	}
	
	public void fire() {
		for (Tower twr: GameManager.getInstance().getTowers()) {
			if (distanceTo(twr) < range && twr != this) {
				twr.addBuff(new AttackSpeedBuff(200, 2));
				if (level >= 3) {
					twr.addBuff(new RangeBuff(200, 0.5));
				}
				if (level >= 5) {
					twr.addBuff(new DamageBuff(200, 2));
				}
			}
		}
		currentCooldown = attackCooldown;
	}
	
}
