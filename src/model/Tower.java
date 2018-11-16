package model;


import java.util.ArrayList;

import buff.Buff;
import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Entity;
import model.monster.GroundMonster;
import model.projectile.Bomb;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.Render;
import util.cpp;

public class Tower extends Tile {

	// TODO : more fields
	
	
	protected double rotation = 0;
	protected double attack;
	protected double attackCooldown = 1000;
	protected double cooldown; // in ms
	protected double range; 
	protected int value = 20;
	protected double attackSpeedMultiplier;
	protected Monster target;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	private double minDist;
	
	public Tower(Image img, double cellX, double cellY,
			double attack, double attackCooldown, double range) {
		super(img, cellX, cellY);
		this.attack = attack;
		this.attackCooldown = attackCooldown;
		this.range = range;
	}
	
	public void clearTarget() {
		target = null;
		minDist = 0;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		Render.drawRotatedImage(gc, image, rotation-180, getRenderX(), getRenderY());
		if ((GameManager.getInstance().getSelectedTile() != null && distanceTo(GameManager.getInstance().getSelectedTile()) < 0.1)) {
			double tz = Numbers.TILE_SIZE;
			double t = GameManager.getInstance().getRenderTickCount()%120;
			double multiplier = t/60.;
			if (multiplier > 1) multiplier = 2-multiplier;
			multiplier = 0.3+0.7*multiplier;
			gc.setLineWidth(3);
			gc.setStroke(new Color(1, 0, 1, 0.8));
			gc.strokeOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);			
			gc.setFill(new Color(1, 0, 1, 0.4*multiplier));
			gc.fillOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);
		}	
	}

	public void render(GraphicsContext gc, boolean showRadius) {
		Render.drawRotatedImage(gc, image, rotation-180, getRenderX(), getRenderY());
		if (showRadius) {
			double tz = Numbers.TILE_SIZE;
			double t = GameManager.getInstance().getRenderTickCount()%120;
			double multiplier = t/60.;
			if (multiplier > 1) multiplier = 2-multiplier;
			multiplier = 0.3+0.7*multiplier;
			gc.setLineWidth(3);
			gc.setStroke(new Color(1, 0, 1, 0.8));
			gc.strokeOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);			
			gc.setFill(new Color(1, 0, 1, 0.4*multiplier));
			gc.fillOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);
		}	
	}
	
	
	public boolean isInRange(Monster m) {
		return Double.compare(distanceTo(m), range) < 0;
	}
	
	// change target to monster m if it's closer than current monster
	public boolean shouldAcquireTarget() {
		return Double.compare(cooldown, 0) <= 0;
	}
	
	public void tryTarget(Monster m) {
		// TODO
		if ((target == null || Double.compare(distanceTo(m), minDist) < 0) && isInRange(m)){
			if (m.isDead()) return ;
			rotateTo(m);
			target = m;
			minDist = distanceTo(m);
		}
	}
	
	// acquire nearest target
	public void acquireTarget() {
		if (!shouldAcquireTarget()) return ;
		for (Monster m: GameManager.getInstance().getMonsters())
			tryTarget(m);
	}
	
	public void rotateTo(Monster m) {
		double angle = Math.toDegrees(Math.atan2(m.getY()-y, m.getX()-x));
		rotation = angle-90;
	}
	
	public void upgrade() {
		range += 0.5;
	}
	

	
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void reduceCooldown() {
		attackSpeedMultiplier = 1;
		for (int i=buffs.size()-1; i>=0; i--) {
			Buff b = buffs.get(i);
			b.applyTo(this);
			b.age();
			if (b.isExpired()) {
				System.out.println(b + "is expired");
				buffs.remove(i);
			}
		}
		cooldown -= 1000/60*attackSpeedMultiplier;
	}
	
	
	
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}

	public void addBuff(Buff b) {
		for (int i=0; i<buffs.size(); i++) {
			if (b.getClass() == buffs.get(i).getClass())
				buffs.remove(i);
		}
		buffs.add(b);
	}

	public double getAttackSpeedMultiplier() {
		return attackSpeedMultiplier;
	}

	public void setAttackSpeedMultiplier(double attackSpeedMultiplier) {
		this.attackSpeedMultiplier = attackSpeedMultiplier;
	}

	public void addAttackSpeedMultiplier(double attackSpeedMultiplier) {
		this.attackSpeedMultiplier += attackSpeedMultiplier;
	}

	public void fire() {
		System.out.println("Current cd" + cooldown);
		if (target == null) return;
		if (cooldown > 0) return;
		
		cpp.pff v = GameUtil.unitVector(this, target);
//		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
//				getX(), getY(), target.getX(), target.getY(), v);
		

		GameManager.getInstance().getBullets().add(new 
				Bomb(Images.bullet1,x, y, v.first*9, v.second*9, range, 1, 2));
		
		cooldown = attackCooldown;
		clearTarget();
	}
	
	public String toString() {
		return "Tower";
	}
	
	public String description() {
		return String.format("Attack = %.2f\nRange = %.2f tile\nCooldown = %.2f ms\n",
				attack, range, attackCooldown);
	}
	
}
