package model;

import javafx.scene.image.Image;

public class Monster {
	private double health; 
	private double armor;
	private Image image;
	private double cellX, cellY;
	
	public Monster(Image image, double cellX, double cellY, double health, double armor) {
		this.image = image;
		this.health = health;
		this.armor = armor;
		this.cellX = cellX;
		this.cellY = cellY;
	}

	public void takeDamage(double damage) {
		damage -= armor;
		if (damage < 0) return ;
		health -= damage;
	}
	
	public boolean isDead() {
		return health < 0;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public double getCellX() {
		return cellX;
	}

	public void setCellX(double cellX) {
		this.cellX = cellX;
	}

	public double getCellY() {
		return cellY;
	}

	public void setCellY(double cellY) {
		this.cellY = cellY;
	}
	
	public String toString() {
		return String.format("Moster has %.2f HP", health);
	}
	
	
}
