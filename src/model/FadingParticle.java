package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;

public class FadingParticle extends Particle {

	

	public FadingParticle(Image[] images, double x, double y, double vx, double vy, double maxAge) {
		super(images, x, y, vx, vy, maxAge);	}

	public FadingParticle(Image image, double x, double y, double vx, double vy, double maxAge) {
		super(image, x, y, vx, vy, maxAge);
	}
	
	public double transparency(){
		double frac = age/maxAge;
		frac = frac - Math.floor(frac/0.4)*0.4;
		frac *= 5;
		return frac > 1 ? 2-frac: frac;
	}
	
	
	@Override
	public void render(GraphicsContext gc) {
		gc.setGlobalAlpha(transparency());
		gc.setGlobalBlendMode(BlendMode.SCREEN);
		super.render(gc);
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setGlobalAlpha(1);
	}

	
}