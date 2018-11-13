package model;

import constants.Numbers;
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
	
	
	public Entity(Image image, double x, double y, double w, double h) {
		this.image = image;
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
	}	

	
	public double distanceTo(Entity e) {
		return GameUtil.distance(x, y, e.x, e.y);
	}
	public double distanceTo(double rx, double ry) {
		return GameUtil.distance(x, y, rx, ry);
	}
	public double getRenderX() {
		return (x)*Numbers.TILE_SIZE; 
	}
	public double getRenderY() {
		return (y)*Numbers.TILE_SIZE;
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
	
	
}
