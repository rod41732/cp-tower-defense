package model;

import javafx.scene.image.Image;

public class Particle extends Entity implements IExpirable {

	private static final int TICKS_PER_FRAME = 3;
	
	protected double maxAge;
	protected double vx, vy;
	protected Image[] frames;
	
	protected double age;
	protected int frameCount;
	protected int nFrames;
	
	protected boolean isExpired;
	
	public Particle(Image[] images, double x, double y, double vx, double vy, double maxAge) {
		super(images[0], x, y, 1.5, 0.0);
		this.frames = images;
		this.vx = vx;
		this.vy = vy;
		this.age = 0;
		this.nFrames = images.length;
		this.frameCount = 0;
		this.maxAge = maxAge;
	}
	
	public Particle(Image image, double x, double y, double vx, double vy, double maxAge) {
		this(new Image[]{image}, x, y, vx, vy, maxAge);
	}
	
	public void onTick() {
		// TODO change these const
		age();
		updateFrame();
		move();
	}
	
	private void age() {
		age += 1000./60;
		frameCount += 1;
	}
	
	protected void updateFrame() {
		this.image = frames[(frameCount/TICKS_PER_FRAME)%nFrames];
	}
	
	private void move() {
		x += vx/60;
		y += vy/60;
	}
	
	
	@Override
	public boolean isExpired() {
		return isExpired || age > maxAge ;
	}
	
	public void forceExpire() {
		isExpired = true;
	}

}
