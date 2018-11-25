package model.monster;

import controller.game.GameManager;
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

	@Override
	protected void updateVelocity() {
		cpp.pii targetTile = GameManager.getInstance().getEndTilePos();
		cpp.pff v_hat = GameUtil.unitVector(x, y, targetTile.first+0.5, targetTile.second+0.5);
		vx = v_hat.first * moveSpeed;
		vy = v_hat.second * moveSpeed;
	}
	
	@Override
	public boolean isAffectedByGround() {
		return false;
	}
	
	@Override
	public boolean isAffectedByAir() {
		return true;
	}
	
	@Override
	public boolean isAffectedBy(Tile t) {
		return t.affectsAir();
	}
	
	@Override
	public String toString() {
		return super.toString() + "- Flying";
	}
	
	
}
