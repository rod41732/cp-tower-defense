package model.particle;

import constants.Images;
import model.Particle;

public class Blood extends Particle {


	public Blood(double x, double y, double vx, double vy) {
		super(Images.blood, x, y, vx, vy, 3);
	}

}
