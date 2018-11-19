package ui;


import constants.Images;
import constants.Numbers;
import controller.GameManager;
import controller.MonsterSpawner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.Main;
import model.Tower;

public class GameScene extends Scene {
	private ButtonManager buttonManager;
	private Timeline gameTick;
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root.getChildren().add(canvas);
		buttonManager = new ButtonManager(root);
		
		
		gameTick = new Timeline();
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e -> {
			if (!GameManager.getInstance().isPaused())
				GameManager.getInstance().update();
			GameManager.getInstance().render(canvas.getGraphicsContext2D());					
		});
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
//		gameTick.play();

		setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			TowerMenu.handleClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.G) {
				GameManager.getInstance().requestNextWave();			
			} else if (e.getCode() == KeyCode.DIGIT1) {
				GameManager.getInstance().setTowerChoice(0);
			} else if (e.getCode() == KeyCode.DIGIT2) {
				GameManager.getInstance().setTowerChoice(1);
			} else if (e.getCode() == KeyCode.DIGIT3) {
				GameManager.getInstance().setTowerChoice(2);
			} else if (e.getCode() == KeyCode.DIGIT4) {
				GameManager.getInstance().setTowerChoice(3);
			} else if (e.getCode() == KeyCode.S) {
				GameManager.getInstance().sellTower();
			} else if (e.getCode() == KeyCode.D) {
				GameManager.getInstance().upgradeTower();
			}
			e.consume(); // prevent 'ding' sound 
		});
	}
	
	public void onLeave() {
		gameTick.pause();
	}
	
	public void onJoin() {
		gameTick.play();
		buttonManager.onGameResume();
	}

	public ButtonManager getButtonManager() {
		return buttonManager;
	}

}
