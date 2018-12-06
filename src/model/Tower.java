package model;


import java.util.ArrayList;

import buff.Buff;
import constants.Numbers;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ui.game.Renderer;
import util.GameUtil;

public abstract class Tower extends Tile {

	// TODO : more fields
	
	private static String TOWER_NAME = "Default tower";
	
	protected double attackCooldown = 1000;
	protected double baseAttack;
	protected double baseRange;
	protected int price;
	protected int level;
	protected int targetFlag;
	
	protected double attack;
	protected double range; 
	protected double currentCooldown; 
	protected double attackSpeedMultiplier;
	protected double rangeMultiplier;
	protected double attackMultiplier;
	protected Monster currentTarget;
	protected ArrayList<Buff> buffs = new ArrayList<>();
	protected double minDist;
	
	public Tower(Image img, double cellX, double cellY,
			double attack, double attackCooldown, double range) {
		super(img, cellX, cellY, false, false);
		this.rotation = 90;
		this.baseAttack = attack; 
		this.attackCooldown = attackCooldown;
		this.baseRange = this.range = range;  // set like this to show range on constructed  
		this.level = 1;
		this.targetFlag = 3;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		if (this == GameManager.getInstance().getSelectedTile()) {
			renderRadius(gc);
		}	
	}

	public void render(GraphicsContext gc, boolean showRadius) {
		super.render(gc);
		if (showRadius) {
			renderRadius(gc);
		}	
	}
	
	
	public void renderRadius(GraphicsContext gc) {
		int tz = Numbers.TILE_SIZE;
		double multiplier = 0.3+0.4*GameUtil.transparencyCycle(Renderer.getInstance().getRenderTick(), 60);
		gc.setLineWidth(3);
		gc.setStroke(new Color(1, 0, 1, 0.8));
		gc.strokeOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);			
		gc.setFill(new Color(1, 0, 1, 0.3*multiplier));
		gc.fillOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);
	}
	
	
	public void upgrade() throws FullyUpgradedException {
		if (level == 5) throw new FullyUpgradedException();
		level += 1;
		range += 0.5;
	}	
	
	@Override 
	public boolean isPlaceable() {
		return false;
	}
	
	@Override
	public boolean isWalkable() {
		return false;
	}
	
	
	@Override
	public boolean isSelectable() {
		return true;
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
	
	private void updateBuff() {
		attackSpeedMultiplier = 1;
		attackMultiplier = 1;
		rangeMultiplier = 1;
		for (Buff b: buffs) {
			b.applyTo(this);
		}
		this.attack = baseAttack*attackMultiplier;
		this.range = baseRange*rangeMultiplier;
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
		return distanceTo(m) < range && (m.getTargetFlag() & this.targetFlag) != 0 ;
	}
	
	public void tryTarget(Monster m) {
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
	
	
	
	public double getAttackCooldown() {
		return attackCooldown;
	}

	public double getAttack() {
		return baseAttack;
	}

	public double getRange() {
		return baseRange;
	}
	
	public boolean canUpgrade() {
		return level < 5;
	}
	
	public abstract double getUpgradedAttackCooldown();

	public abstract double  getUpgradedAttack();

	public abstract double getUpgradedRange();

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	
	public void addAttackSpeedMultiplier(double attackSpeedMultiplier) {
		this.attackSpeedMultiplier += attackSpeedMultiplier;
	}
	public void addRangeMultiplier(double rangeMultiplier) {
		this.rangeMultiplier += rangeMultiplier;
	}
	public void addAttackMultiplier(double attackMultiplier) {
		this.attackMultiplier += attackMultiplier;
	}

	
	
	public abstract int getUpgradePrice();
	
	public String getDescription() {
		return "4Tower";
	}
	
	public String getUpgradedDescription() {
		return "4Town";
	}
	
	public String toString() {
		return TOWER_NAME;
	}
	
	public String description() {
		return String.format("Attack = %.2f\nRange = %.2f tile\nCooldown = %.2f ms\n",
				attack, range, attackCooldown);
	}
	
}
