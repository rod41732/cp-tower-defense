package model;

import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.GameUtil;
import util.Render;
import util.cpp;


// Literally anything, Monster, Tower, Floor Trap, ...;
public class Entity {
	
	// image 
	protected Image image;
	protected double w;
	protected double h;
	
	// position CG 
	protected double x; 
	protected double y;
	protected double zIndex;
	protected double size;
	protected double rotation = 0;
	
	public Entity(Image image, double x, double y, double zIndex, double size) {
		this.image = image;
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.x = x;
		this.y = y;
		this.zIndex = zIndex;
		this.size = size;
	}	

	public cpp.pff getPosition(){
		return new cpp.pff(x, y);
	}
	
	public double distanceTo(Entity e) {
		return distanceTo(e.x, e.y);
	}
	
	public double distanceTo(double rx, double ry) {
		return GameUtil.distance(x, y, rx, ry);
	}
	
	public boolean isCollideWith(Entity e) {
		return Double.compare(distanceTo(e), e.size+size) < 0; 
	}
	
	public double getRenderX() {
		return (x)*Numbers.TILE_SIZE-w/2; 
	}
	public double getRenderY() {
		return (y)*Numbers.TILE_SIZE-h/2;
	}
	
	public void render(GraphicsContext gc) {
		if (Double.compare(rotation, 0) == 0) { // don't rotate at 0
			gc.drawImage(image, getRenderX(), getRenderY());
		}
		else {
			Render.drawRotatedImage(gc, image, rotation, getRenderX(), getRenderY());			
		}
	}
	
	public void rotateTo(Entity e) {
		rotation = Math.toDegrees(Math.atan2(e.getY()-y, e.getX()-x));
	}
	
	public double angleTo(Entity e) {
		return Math.toDegrees(Math.atan2(e.getY()-y, e.getX()-x));
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
	
	
	public double getzIndex() {
		return zIndex;
	}

	public void setzIndex(double zIndex) {
		this.zIndex = zIndex;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSize() {
		return size;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public String toString() {
		return String.format("%s [ x=%.2f, y=%.2f w=%.0f h=%.0f]", getClass().getSimpleName(), x, y, w, h);
	}
}
