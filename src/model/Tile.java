package model;

import javafx.scene.image.Image;

public class Tile extends Entity{
	
	private double speedModifier;
	private boolean isPlaceable;
	
	public Tile(Image image, double x, double y) {
		super(image, x, y, 1);
	}
	
}

