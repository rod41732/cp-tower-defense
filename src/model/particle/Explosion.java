package model.particle;

import constants.Images;
import javafx.scene.image.Image;
import model.Particle;

public class Explosion extends Particle {

	public Explosion(Image[] image,double x, double y, double vx, double vy) {
		super(image, x, y, vx, vy, 1000);
		this.zIndex = 3;
	}
	
	public Explosion(Image image,double x, double y, double vx, double vy) {
		super(image, x, y, vx, vy, 1000);
		this.zIndex = 3;
	}
	

	
}
