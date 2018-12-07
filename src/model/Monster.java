package model;

import java.util.ArrayList;

import buff.Buff;
import buff.DamageTakenDebuff;
import buff.MoveSpeedBuff;
import buff.SlowDebuff;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.particle.Blood;
import util.GameUtil;
import util.cpp.pff;

public abstract class Monster extends Entity implements IBuffable, Cloneable {
	
	
	protected double maxHealth;
	protected double health;
	protected double armor;
	protected double moveSpeed;
	protected double vx;
	protected double vy;
	protected String name;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	protected WritableImage coldImage;
	protected int targetFlag;
	
	protected double moveSpeedMultiplier;
	protected double damageTakenMultiplier;
	
	
	
	protected int money;
	// AI
	
	public Monster(String name, Image image, double x, double y,
			double size, double maxHealth, double armor, double moveSpeed, int money) {
		super(image, x, y, 2, size);
		this.maxHealth = this.health = maxHealth;
		this.armor = armor;
		this.name = name;
		this.moveSpeed = moveSpeed;
		this.money = money;
		this.targetFlag = 3;
		coldImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
		PixelReader reader = image.getPixelReader();
		PixelWriter writer = coldImage.getPixelWriter();
		int w = (int)image.getWidth(), h = (int)image.getHeight();
		for (int i=0; i<w; i++)
			for (int j=0; j<h; j++) {
				int argb = reader.getArgb(i, j);
				argb |= 0xff; 
				writer.setArgb(i, j, argb);
			}
	}
	
	
	public void render(GraphicsContext gc) {
		if (hasBuff(SlowDebuff.ID)) { //
			Image old = this.image;
			this.image = coldImage;
			super.render(gc);
			this.image = old;
		}
		else {
			super.render(gc);					
		}
		if (!this.hasBuff(DamageTakenDebuff.ID)) {
			gc.setFill(Color.color(0, 1, 0));
			gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*40, 10);
			gc.setFill(Color.color(1, 0, 0));
			gc.fillRect(getRenderX()+health/maxHealth*40, getRenderY()-10, 40-health/maxHealth*40, 20);			
		}
		else {
			gc.setFill(Color.color(1, 1, 0));
			gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*40, 10);
			gc.setFill(Color.color(0, 0, 1));
			gc.fillRect(getRenderX()+health/maxHealth*40, getRenderY()-10, 40-health/maxHealth*40, 20);
		}
	}
	
	public void onTick() {
		preUpdate();
		updateBuff();
		updateVelocity();
//		collideWithTile();
		move(); // update v without considering 
	}
	
	public void move() {
		setRotation(Math.toDegrees(Math.atan2(vy, vx)));
		x += vx*Math.max(moveSpeedMultiplier, 0.1)/60;
		y += vy*Math.max(moveSpeedMultiplier, 0.1)/60;
	}
	
	protected void preUpdate() {
		
	}
	
	public void onDeath() {
		
	}
	
	protected abstract void updateVelocity();
	
	public void updateBuff() {
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
	
	public boolean hasBuff(int id) {
		for (Buff b: buffs)
			if (b.getId() == id) 
				return true;
		return false;
	}
	
	
	
	public int getTargetFlag() {
		return targetFlag;
	}


	public Monster clone(){
		try {
			Monster cloned = (Monster)super.clone();
			cloned.buffs = new ArrayList<>();
			return cloned;
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new InternalError();
		}
	}
	
}
