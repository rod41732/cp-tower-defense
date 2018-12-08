package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.particle.Blood;
import util.GameUtil;
import util.cpp.pff;

public abstract class Soldier extends GroundMonster {

	private static final double DEFAULT_SIZE = 0.2;
	protected double level = 0;
	
	public Soldier(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Soldier", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		for (int i=0; i<20; i++) {
			pff nv =  GameUtil.rotateVector(-vx, -vy, (Math.random()-0.5)*30);
			double mult = GameUtil.distance(vx, vy, 0, 0)/20;
			nv.first *= mult;
			nv.second *= mult;
			GameManager.getInstance().addParticle(new Blood(Color.RED, x, y, nv.first, nv.second, 500));
		}
	}
	public double getLevel() {
		return level;
	}

}
