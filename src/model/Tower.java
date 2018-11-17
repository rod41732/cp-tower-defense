package model;


import java.util.ArrayList;

import buff.Buff;
import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.projectile.Bomb;
import util.GameUtil;
import util.Render;
import util.cpp;

public class Tower extends Tile {

	// TODO : more fields
	
	private static final String TOWER_NAME = "Default tower";
	
	protected double attackCooldown = 1000;
	protected double attack;
	protected int price = 20;
	protected double range; 

	protected double currentCooldown; 
	protected double attackSpeedMultiplier;
	protected Monster currentTarget;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	private double minDist;
	
	public Tower(Image img, double cellX, double cellY,
			double attack, double attackCooldown, double range) {
		super(img, cellX, cellY);
		this.attack = attack;
		this.attackCooldown = attackCooldown;
		this.range = range;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		Render.drawRotatedImage(gc, image, rotation, getRenderX(), getRenderY());
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
		Render.drawRotatedImage(gc, image, rotation, getRenderX(), getRenderY());
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
	
	
	public void upgrade() {
		range += 0.5;
	}	
	
	// important metadat 
	@Override 
	public boolean isPlaceable() {
		return false;
	}
	
	@Override
	public boolean isWalkable() {
		return false;
	}
	
	
	// methods related to firing
	public void onTick() {
		preUpdate(); // tower can do anything here
		updateBuff();
		updateCooldown();
		if (shouldAcquireTarget()) {
			acquireTarget();
			fire();			
		}
		clearTarget();
	}
	
	public void preUpdate() {
	}
	
	// TODO: change this and change texture
	@Override
	public void rotateTo(Entity e) {
		super.rotateTo(e);
		rotation += 90; // fix bad alignment in pic
	}
	
	
	private void updateBuff() {
		attackSpeedMultiplier = 1;
		for (Buff b: buffs) {
			b.applyTo(this);
		}
	}
	
	private void updateCooldown() {
		currentCooldown -= 1000./60 * attackSpeedMultiplier;
	}
	
	private boolean shouldAcquireTarget() {
		return Double.compare(currentCooldown, 0) <= 0;
	}
	
	private void acquireTarget() {
		for (Monster m: GameManager.getInstance().getMonsters())
			tryTarget(m);
	}
	
	public boolean isInRange(Monster m) {
		return Double.compare(distanceTo(m), range) < 0;
	}
	
	public void tryTarget(Monster m) {
		// TODO
		if ((currentTarget == null || Double.compare(distanceTo(m), minDist) < 0) && isInRange(m)){
			if (m.isDead()) return ;
			currentTarget = m;
			minDist = distanceTo(m);
		}
	}
	
	
	// by the way, some tower doesn't need target to fire (spammer)
	public void fire() {
		if (currentTarget == null) {
			return;
		}
		rotateTo(currentTarget);
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		GameManager.getInstance().getBullets().add(new 
				Bomb(Images.normalBullet,x, y, v.first*9, v.second*9, range, 1, 2));
		currentCooldown = attackCooldown; // some tower like gatling  cannon might not update like this
	}
	
	public void clearTarget() {
		currentTarget = null;
		minDist = 0;
	}
	
	// getters setters 
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
	
	
	public int getValue() {
		return price;
	}

	public void setValue(int value) {
		this.price = value;
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
	
	
	
	public String toString() {
		return TOWER_NAME + " at " + getPosition();
	}
	
	public String description() {
		return String.format("Attack = %.2f\nRange = %.2f tile\nCooldown = %.2f ms\n",
				attack, range, attackCooldown);
	}
	
}
