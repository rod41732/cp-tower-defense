package model.monster;

import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
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
		vx = v_hat.first * moveSpeed/60;
		vy = v_hat.second * moveSpeed/60;
		super.move();
	}
	
	public String toString() {
		return super.toString() + "- Flying";
	}
	
	
}
