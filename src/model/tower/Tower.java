package model.tower;

import javafx.scene.image.Image;
import model.StationaryObject;

public class Tower extends StationaryObject {

	// TODO : more fields
	private double rotation;
	private double cooldown; // in ms ?
	private double range; 
	
	public Tower(Image img, int cellX, int cellY) {
		super(img, cellX, cellY, true);
	}
	
	public void aim() {
		// TODO
	}
	public void fire() {
		// TODO
	}
}
