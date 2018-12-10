package model;

import java.util.ArrayList;

import buff.ArmorBuff;
import buff.Buff;
import buff.DamageTakenDebuff;
import buff.SlowDebuff;
import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class Monster extends Entity implements Cloneable {
	
	
	protected double maxHealth;
	protected double health;
	protected double armor;
	protected double moveSpeed;
	protected double vx;
	protected double vy;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	protected WritableImage coldImage;
	protected int targetFlag;
	
	protected double moveSpeedMultiplier;
	protected double damageTakenMultiplier;
//	
//	private double rotation;
	
	
	protected int money;
	// AI
	
	public Monster(Image image, double x, double y,
			double size, double maxHealth, double armor, double moveSpeed, int money) {
		super(image, x, y, 2, size);
		this.maxHealth = this.health = maxHealth;
		this.armor = armor;
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
		// render health bar
		if (!this.hasBuff(DamageTakenDebuff.ID)) {
			gc.setFill(Color.color(0, 1, 0, 0.7));
			gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*40, 5);
			gc.setFill(Color.color(1, 0, 0, 0.7));
			gc.fillRect(getRenderX()+health/maxHealth*40, getRenderY()-10, 40-health/maxHealth*40, 5);			
		}
		else {
			gc.setFill(Color.color(1, 1, 0, 0.7));
			gc.fillRect(getRenderX(), getRenderY()-10, health/maxHealth*40, 5);
			gc.setFill(Color.color(0, 0, 1, 0.7));
			gc.fillRect(getRenderX()+health/maxHealth*40, getRenderY()-10, 40-health/maxHealth*40, 5);
		}
		// render other buff
		if (this.hasBuff(ArmorBuff.ID)) {
			gc.drawImage(Images.shield, x*Numbers.TILE_SIZE-16, y*Numbers.TILE_SIZE-16, 32, 32);
		}
	}
	
	public void onTick() {
		preUpdate();
		updateBuff();
		updateVelocity();
		move(); // update v without considering 
	}
	
	public void move() {
		double targetAngle = Math.toDegrees(Math.atan2(vy, vx));
		double diff = targetAngle-rotation;
		double mult2 = 1;
		if (diff >= 5 || diff <= -5) {
			rotation += 5*Math.signum(diff);
			mult2 = 0.3;
		}
		else {
			rotation = targetAngle;
		}
		x += mult2*vx*Math.max(moveSpeedMultiplier, 0.1)/60;
		y += mult2*vy*Math.max(moveSpeedMultiplier, 0.1)/60;			
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
	public void takeDamage(double damage) {
		damage *= damageTakenMultiplier;
		damage -= armor;
		if (damage <= 0) return ;
		health -= damage;
	}
	
	public void takePureDamage(double damage) {
		damage *= damageTakenMultiplier;
		if (damage <= 0) return;
		health -= damage;
	}
	
	public void forceKill() {
		money = 0; // no money on force kill
		health = -1;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void addMoveSpeedMultiplier(double moveSpeedMultiplier) {
		this.moveSpeedMultiplier += moveSpeedMultiplier;
	}


	public void addDamageTakenMultiplier(double damageTakenMultiplier) {
		this.damageTakenMultiplier += damageTakenMultiplier;
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
	
	
	public int getTargetFlag() {
		return targetFlag;
	}
	
	public int getMoney() {
		return money;
	}
	
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}
	
	public String toString() {
		return String.format("%s at %s HP:%.2f Armor:%.2f MS:%.2f",
				getClass().getSimpleName(), getPosition(), health, armor, moveSpeed);
	}
	
}
