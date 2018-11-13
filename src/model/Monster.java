package model;

import controller.GameManager;
import javafx.scene.image.Image;
import util.GameUtil;
import util.cpp;

public abstract class Monster extends Entity {
	
	
	protected double health; 
	protected double armor;
	protected double moveSpeed;
	protected double vx;
	protected double vy;
	protected String name;
	// AI
	
	public Monster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed) {
		super(image, x, y, size);
		this.health = health;
		this.armor = armor;
		this.name = name;
		this.moveSpeed = moveSpeed;
	}
	
	public void move() {
		x += vx;
		y += vy;
	}
	
	public boolean isTargetableBy(Tower t) {
		return true;
	}
	
	
	// return false is damage is negated
	public boolean takeDamage(double damage) {
		damage -= armor;
		if (damage <= 0) return false;
		System.out.printf("%s took %.2f damage\n", name, damage);
		health -= damage;
		return true;
	}
	
	public boolean takePureDamage(double damage) {
		if (damage <= 0) return false;
		System.out.printf("%s took %.2f damage\n", name, damage);
		health -= damage;
		return true;
	}
	
	public boolean isDead() {
		return Double.compare(health, 0) <= 0;
	}
	
	public void forceKill() {
		health = -1;
	}
	
	public String toString() {
		return String.format("%s at %s HP:%.2f Armor:%.2f MS:%.2f",
				name, getPosition(), health, armor, moveSpeed);
	}
	
}