package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.particle.Blood;
import model.particle.Explosion;
import util.GameUtil;
import util.cpp.pff;

public abstract class Car extends SplittingMonster {

	protected double level = 0;
	public static double DEFAULT_SIZE = 0.5;
	
	public Car(Image image, double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super("Car", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		// TODO: add common death
		GameManager.getInstance().addParticle(new Explosion(x, y, 0, 0)); // common death anim for cars
	}
	
	@Override
	public void move() {
		// TODO add tire track
		super.move();
	}
	
	@Override
	public boolean takeDamage(double damage) {
		for (int i=0; i<20; i++) {
			pff nv =  GameUtil.rotateVector(-vx, -vy, (Math.random()-0.5)*30);
			double mult = 1.3/GameUtil.distance(vx, vy, 0, 0);
			nv.first *= mult;
			nv.second *= mult;
			GameManager.getInstance().addParticle(new Blood(Color.BLACK, x, y, nv.first, nv.second, 500));
		}
		return super.takeDamage(damage);
	}
	
}
