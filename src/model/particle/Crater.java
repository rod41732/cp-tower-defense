package model.particle;

import constants.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Crater  extends FadingParticle{

	private static Image DEFAULT_IMAGE = Images.crater;
	
	public Crater(double x, double y, double maxAge) {
		super(DEFAULT_IMAGE, x, y, 0, 0, maxAge);
		this.zIndex = 1.1;
	}
	
	@Override
	public double transparency() {
		double frac = age/maxAge;
		return frac > 0.6 ? (1-frac)*2.5 : 1;
	}

}
