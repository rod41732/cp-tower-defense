package model;

import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.GameUtil;
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
	protected double size;
	protected double rotation = 0;
	
	public Entity(Image image, double x, double y, double size) {
		this.image = image;
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.x = x;
		this.y = y;
		this.size = size;
	}	

	public cpp.pff getPosition(){
		return new cpp.pff(x, y);
	}
	
	public double distanceTo(Entity e) {
		return GameUtil.distance(x, y, e.x, e.y);
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
		gc.drawImage(image, getRenderX(), getRenderY());
	}
	
	public void rotateTo(Entity e) {
		rotation = Math.toDegrees(Math.atan2(e.getY()-y, e.getX()-x));
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String toString() {
		return String.format("Entity at (%.2f, %.2f)", x, y);
	}
}
