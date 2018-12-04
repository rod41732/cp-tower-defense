package model;

import constants.Images;
import constants.Numbers;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Blood extends Particle {

	private Color color;
	private double radius;
	private boolean isOnGround; // no more spawning
	
	public Blood(Color color, double x, double y, double vx, double vy, double maxAge, double radius) {
		super(Images.attackIcon, x, y, vx, vy, maxAge);
		this.radius = radius;
		this.isOnGround = false;
	}
	public Blood(Color color, double x, double y, double vx, double vy, double maxAge) {
		this(color, x, y, vx, vy, maxAge, 2);
	}

	
	@Override
	public void onTick() {
		if (!isOnGround) {
			vy += 0.03;
			vx *= 0.97;			
		}
		super.onTick();
		if (age > 0.9*maxAge && !isOnGround) {
			forceExpire();
			Blood b = new Blood(Color.RED, x, y, 0, 0, 2500, 8);
			b.isOnGround  = true;
			GameManager.getInstance().addParticle(b);
		}
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.save();
		gc.setFill(color);
		gc.fillRect(x*Numbers.TILE_SIZE, y*Numbers.TILE_SIZE, radius, radius);
		gc.restore();
	}
}
