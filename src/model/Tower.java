package model;


import java.util.ArrayList;

import buff.AttackSpeedBuff;
import buff.Buff;
import buff.DamageBuff;
import buff.RangeBuff;
import constants.Images;
import constants.Numbers;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ui.game.Renderer;
import util.GameUtil;

public abstract class Tower extends Tile {

	protected double attackCooldown;
	protected double baseAttack;
	protected double baseRange;
	protected int price;
	protected int level;
	protected int targetFlag;
	protected String typeName;
	
	protected double attack;
	protected double range; 
	protected double currentCooldown; 
	protected double attackSpeedMultiplier;
	protected double rangeMultiplier;
	protected double attackMultiplier;
	protected Monster currentTarget;
	protected ArrayList<Buff> buffs;
	protected double minDist;
	
	public Tower(String typeName, double cellX, double cellY) {
		super(Images.towerImages.get(typeName)[0], cellX, cellY, false, false);
		this.typeName = typeName;
		this.rotation = 90; // so it looks better
		this.level = 1;
		this.baseAttack = (Double)TowerStats.getData(typeName, "Attack", 1);
		this.baseRange = (Double)TowerStats.getData(typeName, "Range", 1);
		this.attackCooldown = (Double)TowerStats.getData(typeName, "Cooldown", 1);
		this.price = (int)TowerStats.getData(typeName, "Price", 1);
		this.targetFlag = (int) TowerStats.getData(typeName, "TargetFlag", 1);
		this.range = this.baseRange;
		this.buffs = new ArrayList<>();
		this.zIndex = 2;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Images.towerBase, getRenderX(), getRenderY());
		super.render(gc);
		renderBuff(gc);
		if (this == GameManager.getInstance().getSelectedTower()) {
			renderExtraElements(gc);
		}	
	}

	public void render(GraphicsContext gc, boolean showRadius) {
		super.render(gc);
		renderBuff(gc);
		if (showRadius){
			renderExtraElements(gc);
		}	
	}
	
	
	public void renderBuff(GraphicsContext gc) {
		if (hasBuff(RangeBuff.ID))
			gc.drawImage(Images.targetIcon, getRenderX(), getRenderY(), 16, 16);
		if (hasBuff(AttackSpeedBuff.ID))
			gc.drawImage(Images.cooldownIcon, getRenderX()+w-16, getRenderY(), 16, 16);
		if (hasBuff(DamageBuff.ID))
			gc.drawImage(Images.attackIcon, getRenderX(), getRenderY()+h-16, 16, 16);
	}
	public void renderExtraElements(GraphicsContext gc) {
		int tz = Numbers.TILE_SIZE;
		double multiplier = 0.3+0.4*GameUtil.transparencyCycle(Renderer.getInstance().getRenderTick(), 60);
		gc.drawImage(Images.towerFocus, getRenderX(), getRenderY());
		gc.setLineWidth(3);
		gc.setStroke(new Color(1, 0, 1, 0.8));
		gc.strokeOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);			
		gc.setFill(new Color(1, 0, 1, 0.3*multiplier));
		gc.fillOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);
	}
	
	
	public boolean upgrade() throws FullyUpgradedException {
		if (level == 5) {
			throw new FullyUpgradedException();
		}
		
		level += 1;
		this.image = Images.towerImages.get(typeName)[level-1];
		this.baseAttack = (double)TowerStats.getData(typeName, "Attack", level);
		this.baseRange = (double)TowerStats.getData(typeName, "Range", level);
		this.attackCooldown = (double)TowerStats.getData(typeName, "Cooldown", level);
		this.price += (int)TowerStats.getData(typeName, "Price", level);
		return true;
	}	

	
	@Override
	public boolean isSelectable() {
		return true;
	}
	
	// methods related to firing
	public void onTick() {
		updateBuff();
		updateCooldown();
		if (shouldAcquireTarget()) {
			acquireTarget();
			fire();			
		}
		clearTarget();
	}
	
	public void updateBuff() {
		attackSpeedMultiplier = 1;
		attackMultiplier = 1;
		rangeMultiplier = 1;
		for (int i=buffs.size()-1; i>=0; i--) {
			Buff b = buffs.get(i);
			b.age();
			b.applyTo(this);
			if (b.isExpired())
				buffs.remove(i);
		}
		this.attack = baseAttack*attackMultiplier;
		this.range = baseRange*rangeMultiplier;
	}
	
	private void updateCooldown() {
		currentCooldown -= 1000./60 * attackSpeedMultiplier;
	}
	
	private boolean shouldAcquireTarget() {
		return currentCooldown <= 0;
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
	
	public void fire() {
		if (currentTarget == null) {
			return;
		}
		rotateTo(currentTarget);
		currentCooldown = attackCooldown;
	}
	
	public void clearTarget() {
		currentTarget = null;
		minDist = 0;
	}
	


	public void addBuff(Buff b) {
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
	
	// getters 
	public double getAttackCooldown() {
		return attackCooldown;
	}

	public double getAttack() {
		return baseAttack;
	}

	public double getRange() {
		return baseRange;
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
	
	
	public String getDescription() {
		return (String) TowerStats.getData(typeName, "Description", level);
	}
	
	public String getUpgradedDescription() {
		return (String) TowerStats.getData(typeName, "Description", level+1);
	}
	
	public double getUpgradedAttackCooldown() {
		return (double) TowerStats.getData(typeName, "Cooldown", level+1);
	}

	public double getUpgradedAttack() {
		return (double) TowerStats.getData(typeName, "Attack", level+1);
	}

	public double getUpgradedRange() {
		return (double) TowerStats.getData(typeName, "Range", level+1);
	}
	
	public int getUpgradePrice() {
		return (int) TowerStats.getData(typeName, "Price", level+1);
	}
	
	public boolean canUpgrade() {
		return level < 5;
	}
	
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}
	
	public int getPrice() {
		return this.price;
	}
	

	public void setPrice(int price) {
		this.price = price;
	}



}
