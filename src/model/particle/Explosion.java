package model.particle;

import constants.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import model.Particle;

public class Explosion extends Particle {
	private static Image[] DEFAULT_IMAGE= Images.explosion; 
	private static double scale;

	public Explosion(double x, double y, double vx, double vy, double size) {
		super(DEFAULT_IMAGE, x, y, vx, vy, 1000);
		this.zIndex = 3;
	}
	
	
	public Explosion(double x, double y, double vx, double vy) {
		this(x, y, vx, vy, 1);
	}
	
	
	
}
