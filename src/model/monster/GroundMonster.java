package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Tile;
import util.GameUtil;
import util.cpp;

public class GroundMonster extends Monster {

	public GroundMonster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}

	@Override
	public void updateVelocity() {
		cpp.pii[][] path = GameManager.getInstance().getPath();
		cpp.pii cur = getPosition().toI();
		
		int gridX = cur.first;
		int gridY = cur.second;
		

		// find target tile
		cpp.pff targetPos;
		try {
			if (path[gridX][gridY] != null) {
				targetPos = path[gridX][gridY].toF();
				cpp.pff v_hat = GameUtil.unitVector(x, y, targetPos.first+0.5, targetPos.second+0.5);
				vx = v_hat.first * moveSpeed;
				vy = v_hat.second * moveSpeed;
			}
			else {
				vx = vy = 0; 
				return ;
			}			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	@Override
	public boolean isAffectedBy(Tile t) {
		return t.affectsGround();
	}
	
	@Override
	public boolean isAffectedByGround() {
		return true;
	}
	@Override
	public boolean isAffectedByAir() {
		return true;
	}
}
