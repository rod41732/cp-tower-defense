package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import util.cpp.pff;

public class SplittingMonster extends GroundMonster {

	public SplittingMonster(String name, Image image, double x, double y, double size, double health, double armor,
			double moveSpeed, int money) {
		super(name, image, x, y, size, health, armor, moveSpeed, money);
	}	
	
	public void onDeath() {
		pff pos = getPosition();
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e ->  {
			GameManager.getInstance().addMonster(new GroundMonster("Moose", Images.moose,
					pos.first+(Math.random()-0.5)*0.2, pos.second+(Math.random()-0.5)*0.2, 0.3, 30, 3, 1.5, 3));
		}));
		tl.setCycleCount(5);
		tl.play();
	}
	
}
