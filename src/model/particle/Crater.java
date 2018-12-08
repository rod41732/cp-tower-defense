package model.particle;

import constants.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.FadingParticle;

public class Crater  extends FadingParticle{

	private static Image DEFAULT_IMAGE = Images.crater;
	
	public Crater(double x, double y, double maxAge) {
		super(DEFAULT_IMAGE, x, y, 0, 0, maxAge);
		this.zIndex = 1.1;
	}
	
	
	@Override
	public void render(GraphicsContext gc) {
		double frac = age/maxAge;
		
		gc.save();
		gc.setGlobalAlpha(frac > 0.6 ? (1-frac)*2.5 : 1);
		gc.drawImage(image, getRenderX(), getRenderY());
		gc.restore();
		
	}

}
