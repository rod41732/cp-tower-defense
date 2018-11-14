package model;

import javafx.scene.image.Image;

public class Particle extends Entity implements IExpirable {

	protected double maxAge;
	protected double vx, vy;
	protected Image[] frames;
	
	protected double age;
	protected int frameCount;
	protected int nFrames;

	public Particle(Image[] images, double x, double y, double vx, double vy, double maxAge) {
		super(images[0], x, y, 0.0);
		this.frames = images;
		this.age = 0;
		this.nFrames = images.length;
		this.frameCount = 0;
		this.maxAge = maxAge;
	}
	
	private void move() {
		x += vx/60;
		y += vy/60;
	}
	
	public void update() {
		// TODO change these const
		age += 16;
		frameCount += 1;
		image = frames[(frameCount/4)%nFrames];
		move();
	}
	
	@Override
	public boolean isExpired() {
		return age > maxAge;
	}

}
