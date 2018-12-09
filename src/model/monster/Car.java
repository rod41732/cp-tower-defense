package model.monster;

import java.lang.reflect.InvocationTargetException;

import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import model.particle.Corpse;
import model.particle.Explosion;
import util.cpp.pff;

public abstract class Car extends SplittingMonster {

	protected double level = 0;
	public static double DEFAULT_SIZE = 0.5;
	private Class<? extends Monster> childConstructor;
	
	public Car(Image image, double x, double y, double health, double armor, double moveSpeed,
			int money, Class<? extends Monster> childConstructor) {
		super("Car", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
		this.childConstructor = childConstructor;
	}
	
	@Override
	public void onDeath() {
		Sounds.carExplosion.play();
		GameManager.getInstance().addParticle(new Explosion(Images.deathExplosion, x, y, 0, 0)); // common death anim for cars
		Particle part = new Corpse(Images.normalCarDead, x, y, 0, 0);
		part.setRotation(rotation);
		GameManager.getInstance().addParticle(part);
		pff pos = getPosition();
		try {
			childConstructor.getConstructor(double.class, double.class, double.class)
			.newInstance(pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, level);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("Splitting Monster: No constructor double double double exist for " + childConstructor.getSimpleName());
		}
	}
	
	@Override
	public boolean takeDamage(double damage) {
		Particle part = new Particle(Images.spark, x+(Math.random()-0.5)*0.2, y+(Math.random()-0.5)*0.2, vx, vy, 1);
		part.setzIndex(5);
		GameManager.getInstance().addParticle(part);
		return super.takeDamage(damage);
	}
	
	
	
}
