package model.particle;

import constants.Images;
import constants.Numbers;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Particle;

public class Blood extends Particle {


	public Blood(double x, double y, double vx, double vy) {
		super(Images.blood, x, y, vx, vy, 500);
	}

}
