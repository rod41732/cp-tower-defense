package model;

import javafx.scene.image.Image;

public class Tile extends Entity{
	
	private double speedModifier;
	public Tile(Image image, double x, double y) {
		super(image, x, y, 1);
	}
	
	public boolean isPlaceable() {
		return true;
	}
	
	public boolean isWalkable() {
		return true;
	}
	
}

