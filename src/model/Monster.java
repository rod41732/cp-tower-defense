package model;

import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.tower.BombTower;
import util.GameUtil;
import util.cpp;

public abstract class Monster extends Entity {
	
	
	protected double maxHealth;
	protected double health;
	protected double armor;
	protected double moveSpeed;
	protected double vx;
	protected double vy;
	protected String name;
	protected int money;
	// AI
	
	public Monster(String name, Image image, double x, double y,
			double size, double maxHealth, double armor, double moveSpeed, int money) {
		super(image, x, y, size);
		this.maxHealth = this.health = maxHealth;
		this.armor = armor;
		this.name = name;
		this.moveSpeed = moveSpeed;
		this.money = money;
	}
	
	public void move() {
		x += vx;
		y += vy;
	}
	
	public boolean isTargetableBy(BombTower t) {
		return true;
	}
	
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.setFill(Color.GREEN);
		gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*100, 3);
		gc.setFill(Color.RED);
		gc.fillRect(getRenderX()+health/maxHealth*100, getRenderY()-10, 100-health/maxHealth*100, 3);
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
	
	public int getMoney() {
		return money;
	}
	
	public String toString() {
		return String.format("%s at %s HP:%.2f Armor:%.2f MS:%.2f",
				name, getPosition(), health, armor, moveSpeed);
	}
	
}
