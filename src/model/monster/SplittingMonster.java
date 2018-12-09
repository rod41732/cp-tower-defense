package model.monster;

import javafx.scene.image.Image;

public abstract class SplittingMonster extends GroundMonster {

	public SplittingMonster(String name, Image image, double x, double y, double size, double health, double armor,
			double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}	
	
	public abstract void onDeath();
}
