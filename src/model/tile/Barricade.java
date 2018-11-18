package model.tile;

import javafx.scene.image.Image;
import model.Tile;

public class Barricade extends Tile {

	public Barricade(Image image, double x, double y) {
		super(image, x, y);
	}

	public boolean isPlaceable() {
		return false;
	}
	
	public boolean isWalkable() {
		return false;
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
	
	
}
