package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class StationaryObject {
	private Image image; 
	private boolean isObstacle;
	private boolean isBlockingTower;
	private int cellX, cellY;
	
	public StationaryObject(Image image, int cellX, int cellY, boolean isObstacle) {
		this.image = image;
		this.isObstacle = isObstacle;
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
	public void render(GraphicsContext gc, double x, double y) {
		gc.drawImage(image, x, y);
	}

	public boolean isObstacle() {
		return isObstacle;
	}

	public boolean isBlockingTower() {
		return isBlockingTower;
	}

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}
	
	
}
