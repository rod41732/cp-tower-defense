package model;

import controller.GameManager;
import javafx.scene.image.Image;
import util.GameUtil;
import util.cpp;

public class Monster extends Entity {
	
	
	private double health; 
	private double armor;
	private double moveSpeed = 0.5;
	
	// AI
	private cpp.pii targetTile = null;
	private double targetDist = 0;
	
	public Monster(Image image, double x, double y, double w, double h, double health, double armor) {
		super(image, x, y, w, h);
		this.health = health;
		this.armor = armor;
	}
	
	public void clearPath() {
		targetTile = null;
		targetDist = 999;
	}

	public void findPath() {
		clearPath();
//		System.out.println("--------------------------");
		for (cpp.pii tile: GameManager.getInstance().getPath()) {
//			System.out.println("considering ???");
			double distNow = GameUtil.distance(x, y, 10, 10);
			double distFuture = GameUtil.distance(tile.first, tile.second, 10, 10);
			double distToTile = GameUtil.distance(tile.first, tile.second, x, y);
			if (Double.compare(distNow, distFuture) < 0) {
//				System.out.printf("didn't move to %s because it's farther than now %.2f %.2f", tile,
//						cellX, cellY);
				continue;
			}
			
			
			if (Double.compare(distToTile, 0.1) < 0) {
//				System.out.printf("didn't move to %s because it's pretty close to %.2f %.2f", tile,
//						cellX, cellY);
				continue; // is pretty close to target tile, should move to next
			}
			if (targetTile == null || Double.compare(distToTile, targetDist) < 0 ) {
				System.out.println("Ok now targeting" + tile);
				targetDist = distToTile;
				targetTile = tile;
			}
		}
	}
	
	public void move() {
		findPath();
		if (targetTile == null) return; 
		cpp.pff v = GameUtil.unitVector(x, y, targetTile.first, targetTile.second);
		this.x += v.first * moveSpeed/60;
		this.y += v.second* moveSpeed/60;
	}
	
	public void takeDamage(double damage) {
		damage -= armor;
		if (damage < 0) return ;
		health -= damage;
	}
	
	public boolean isDead() {
		return Double.compare(health, 0) <= 0;
	}
	
	public void forceKill() {
		health = 0;
	}
	
	public double getHealth() {
		return health;
	}

	public double getArmor() {
		return armor;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	public String toString() {
		return String.format("Moster has %.2f HP", health);
	}
	
	
}
