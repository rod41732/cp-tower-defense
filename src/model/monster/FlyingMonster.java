package model.monster;

import buff.Buff;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Tile;
import util.GameUtil;
import util.cpp;

public class FlyingMonster extends Monster {
	
	public FlyingMonster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}

	
	public void move() {
		GameManager gi =GameManager.getInstance();
		cpp.pii targetTile = new cpp.pii(gi.getEndCol(), gi.getEndRow());
		cpp.pff v_hat = GameUtil.unitVector(x, y, targetTile.first, targetTile.second);
		damageTakenMultiplier = 1;
		moveSpeedMultiplier = 1;
		vx = v_hat.first * moveSpeed/60;
		vy = v_hat.second * moveSpeed/60; 
		for (int i=buffs.size()-1; i>=0; i--) {
			Buff b = buffs.get(i);
			b.applyTo(this);
			b.age();
			if (b.isExpired()) {
				System.out.println(b + "is expired at" + System.currentTimeMillis());
				buffs.remove(i);
			}
		}
		super.move();
	}
	
	public boolean isAffectedBy(Tile t) {
		return t.affectsAir();
	}
	
	public String toString() {
		return super.toString() + "- Flying";
	}
	
	
}
