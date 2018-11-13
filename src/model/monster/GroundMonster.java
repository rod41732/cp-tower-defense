package model.monster;

import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import util.GameUtil;
import util.cpp;

public class GroundMonster extends Monster {

	private cpp.pii targetTile = null;
	public GroundMonster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed) {
		super(name, image, x, y, size, health, armor, moveSpeed);
	}

	public void findPath() {
		cpp.pii[][] path = GameManager.getInstance().getPath();
		int gridX = (int)Math.round(x);
		int gridY = (int)Math.round(y);
		targetTile = path[gridX][gridY];
	}
	
	public void move() {
		findPath();
		if (targetTile == null) return; 
		cpp.pff v_hat = GameUtil.unitVector(x, y, targetTile.first, targetTile.second);
		vx = v_hat.first * moveSpeed/60;
		vy = v_hat.second * moveSpeed/60;
		super.move();
	}
		
}
