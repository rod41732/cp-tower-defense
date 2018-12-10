package model.particle;

import javafx.scene.image.Image;
import model.Particle;

public class Explosion extends Particle {

	public Explosion(Image[] image,double x, double y, double vx, double vy) {
		super(image, x, y, vx, vy, 3);
		this.zIndex = 3;
	}
	
}
