package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import util.cpp.pff;

public class SplittingMonster extends GroundMonster {

	public SplittingMonster(String name, Image image, double x, double y, double size, double health, double armor,
			double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}	
	
	public void onDeath() {
		pff pos = getPosition();
		for (int i=0; i<5; i++)
			GameManager.getInstance().addMonsterDefault(new GroundMonster("Moose", Images.moose,
					pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, 0.3, 30, 3, 1.5, 3));
	}
}
