package model.particle;

import javafx.scene.image.Image;

public class Corpse extends FadingParticle {

	public Corpse(Image image, double x, double y, double vx, double vy) {
		super(image, x, y, vx, vy, 5000.0);
	}

	@Override
	public double transparency() {
		double frac = age/maxAge;
		if (frac < 0.8) {
			return 1.0;
		}
		return (1-frac)*5;
	}
}
