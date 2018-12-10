package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import util.GameUtil;
import util.cpp;

public abstract class FlyingMonster extends Monster {
	
	public FlyingMonster(String name, Image image, double x, double y,
			double size, double health, double armor, double moveSpeed, int money) {
		super(image, x, y, size, health, armor, moveSpeed, money);
		this.targetFlag = 2;
	}

	@Override
	protected void updateVelocity() {
		cpp.pii targetTile = GameManager.getInstance().getEndTilePos();
		cpp.pff v_hat = GameUtil.unitVector(x, y, targetTile.first+0.5, targetTile.second+0.5);
		vx = v_hat.first * moveSpeed;
		vy = v_hat.second * moveSpeed;
	}

	@Override
	public String toString() {
		return super.toString() + "- Flying";
	}
		
	
}
