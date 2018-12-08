package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Particle;
import model.particle.Explosion;

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
		Particle part = new Particle(Images.spark, x+(Math.random()-0.5)*0.2, y+(Math.random()-0.5)*0.2, vx, vy, 500);
		part.setzIndex(5);
		GameManager.getInstance().addParticle(part);
	
		return super.takeDamage(damage);
	}
	
}
