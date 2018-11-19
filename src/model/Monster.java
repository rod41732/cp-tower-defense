package model;

import java.util.ArrayList;

import buff.Buff;
import buff.MoveSpeedBuff;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Monster extends Entity {
	
	
	protected double maxHealth;
	protected double health;
	protected double armor;
	protected double moveSpeed;
	protected double vx;
	protected double vy;
	protected String name;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	protected static Image coldImage = new Image("monster/bear_cold.png", 64, 64, true, true);
	
	
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
	
	public abstract boolean isAffectedBy(Tile t);
	public abstract boolean isAffectedByGround();
	public abstract boolean isAffectedByAir();
	
	public void render(GraphicsContext gc) {
		if (hasBuff(new MoveSpeedBuff(1,1))) {
			gc.drawImage(coldImage, getRenderX(), getRenderY());
		}
		else {
			super.render(gc);			
		}
		gc.setFill(Color.GREEN);
		gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*100, 3);
		gc.setFill(Color.RED);
		gc.fillRect(getRenderX()+health/maxHealth*100, getRenderY()-10, 100-health/maxHealth*100, 3);
	}
	
	public void onTick() {
		preUpdate();
		updateBuff();
		updateVelocity();
//		collideWithTile();
		move(); // update v without considering 
	}
	
	public void move() {
		x += vx*Math.max(moveSpeedMultiplier, 0.1)/60;
		y += vy*Math.max(moveSpeedMultiplier, 0.1)/60;
	}
	
	protected void preUpdate() {
		
	}
	
	public void onDeath() {
		
	}
	
	protected abstract void updateVelocity();
	
	protected void updateBuff() {
		moveSpeedMultiplier = 1;
		damageTakenMultiplier = 1;
		for (int i=buffs.size()-1; i>=0; i--) {
			Buff b = buffs.get(i);
			b.age();
			b.applyTo(this);
			if (b.isExpired())
				buffs.remove(i);
		}
	}
	
	// return false is damage is negated
	public boolean takeDamage(double damage) {
		damage *= damageTakenMultiplier;
		damage -= armor;
		if (damage <= 0) return false;
		health -= damage;
		return true;
	}
	
	public boolean takePureDamage(double damage) {
		damage *= damageTakenMultiplier;
		if (damage <= 0) return false;
		health -= damage;
		return true;
	}
	
	public void forceKill() {
		money = 0; // no money on force kill
		health = -1;
	}
	
	public boolean isDead() {
		return Double.compare(health, 0) <= 0;
	}
	
	public int getMoney() {
		return money;
	}
	
	public String toString() {
		return String.format("%s at %s HP:%.2f Armor:%.2f MS:%.2f",
				name, getPosition(), health, armor, moveSpeed);
	}

	public void addMoveSpeedMultiplier(double moveSpeedMultiplier) {
		this.moveSpeedMultiplier += moveSpeedMultiplier;
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
	
	public boolean hasBuff(Buff buffInstance) {
		for (Buff b: buffs)
			if (b.getClass() == buffInstance.getClass()) 
				return true;
		return false;
	}
}
