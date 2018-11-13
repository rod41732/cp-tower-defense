package model;

import controller.GameManager;
import javafx.scene.image.Image;
import util.GameUtil;
import util.cpp;

public class Monster extends Entity {
	
	
	private double health; 
	private double armor;
	private double moveSpeed = 1.5;
	
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
		cpp.pii[][] path = GameManager.getInstance().getPath();
		int gridX = (int)Math.round(x-0.02);
		int gridY = (int)Math.round(y-0.02);
		
		targetTile = path[gridX][gridY];
//		System.out.printf("currently at %s which is grid %s going to %s\n",
//				new cpp.pff(x, y), new cpp.pii(gridX, gridY), targetTile);
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
