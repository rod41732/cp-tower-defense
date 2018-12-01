package model;

import javafx.scene.image.Image;

public class Tile extends Entity{
	
	private double speedModifier;
	private boolean isWalkable, isPlaceable;
	public Tile(Image image, double x, double y, boolean isWalkable, boolean isPlaceable) {
		super(image, x, y, 1, 1);
		this.isWalkable = isWalkable;
		this.isPlaceable = isPlaceable;
	}
	
	public boolean isPlaceable() {
		return isPlaceable;
	}
	
	public boolean isWalkable() {
		return isWalkable;
	}
	
	public boolean isSelectable() {
		return false;
	}
	
	public boolean affectsAir() {
		return false;
	}
	
	public boolean affectsGround() {
		return true;
	}

	public double getSpeedModifier() {
		return speedModifier;
	}
	
}

