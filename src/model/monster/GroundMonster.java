package model.monster;

import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import util.GameUtil;
import util.cpp;

public class GroundMonster extends Monster {

	private cpp.pff targetTile = null;
	public GroundMonster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}

	public void findPath() {
		System.out.println(toString() + "is finding path");
		cpp.pii[][] path = GameManager.getInstance().getPath();
		cpp.pii cur = getPosition().toI();
		int gridX = cur.first;
		int gridY = cur.second;
		System.out.printf("%s is at %s Tile %s => Tile %s\n", name, getPosition(),
				getPosition().toI(), path[gridX][gridY]);
		if (path[gridX][gridY] != null) {
			targetTile = path[gridX][gridY].toF();
			targetTile.first += 0.5;
			targetTile.second += 0.5;			
		}
		else targetTile = null;
		
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
