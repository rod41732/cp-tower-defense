package model;

import java.util.ArrayList;

import buff.Buff;
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
	protected ArrayList<Buff> buffs = new ArrayList<>();
	
	protected double moveSpeedMultiplier;
	protected double damageTakenMultiplier;
	
	
	
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
		x += vx*Math.max(moveSpeedMultiplier, 0.1);
		y += vy*Math.max(moveSpeedMultiplier, 0.1);
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
		damage *= damageTakenMultiplier;
		damage -= armor;
		if (damage <= 0) return false;
		System.out.printf("%s took %.2f damage\n", name, damage);
		health -= damage;
		return true;
	}
	
	public boolean takePureDamage(double damage) {
		damage *= damageTakenMultiplier;
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

	public double getMoveSpeedMultiplier() {
		return moveSpeedMultiplier;
	}

	public void setMoveSpeedMultiplier(double moveSpeedMultiplier) {
		this.moveSpeedMultiplier = moveSpeedMultiplier;
	}
	public void addMoveSpeedMultiplier(double moveSpeedMultiplier) {
		this.moveSpeedMultiplier += moveSpeedMultiplier;
	}

	public double getDamageTakenMultiplier() {
		return damageTakenMultiplier;
	}

	public void setDamageTakenMultiplier(double damageTakenMultiplier) {
		this.damageTakenMultiplier = damageTakenMultiplier;
	}

	public void addDamageTakenMultiplier(double damageTakenMultiplier) {
		this.damageTakenMultiplier += damageTakenMultiplier;
	}
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}
	
	public void addBuff(Buff b){
		
		for (int i=0; i<buffs.size(); i++) {
			if (b.getClass() == buffs.get(i).getClass())
				buffs.remove(i);
		}
		buffs.add(b);
	}
	
	
	
}
