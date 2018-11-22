package ui;


import constants.Numbers;
import controller.ButtonManager;
import controller.GameManager;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;

public class GameScene extends Scene {
	private ButtonManager buttonManager;
	private Timeline gameTick;
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas other = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Canvas tiles = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Canvas overlay = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		
		
		root.getChildren().add(tiles); // TILE
		root.getChildren().add(other); // Other
		buttonManager = new ButtonManager(root);
		root.getChildren().add(overlay);
		overlay.setMouseTransparent(true);
		tiles.setMouseTransparent(true);
		buttonManager.addMenuButtons(root);
		
		gameTick = new Timeline();
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e -> {
			if (!SuperManager.getInstance().getIsGamePausedProp().get())
				GameManager.getInstance().update();
			GameManager.getInstance().render(other.getGraphicsContext2D(), tiles.getGraphicsContext2D(), overlay.getGraphicsContext2D());					
		});
		
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			boolean pause = nw.booleanValue();
			if (pause) overlay.setMouseTransparent(false);
			else overlay.setMouseTransparent(true);
			
		});
		
		
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);

		setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			System.out.println("ig change");
			boolean inGame = nw.booleanValue();
			if (inGame) gameTick.play();
			else gameTick.pause();
		});
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.G) {
				GameManager.getInstance().requestNextWave();			
			} else if (e.getCode() == KeyCode.DIGIT1) {
				GameManager.getInstance().setSelectedTile(null);
				IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
				prop.set(prop.get() == 0 ? -1 : 0);
			} else if (e.getCode() == KeyCode.DIGIT2) {
				GameManager.getInstance().setSelectedTile(null);
				IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
				prop.set(prop.get() == 1 ? -1 : 1);
			} else if (e.getCode() == KeyCode.DIGIT3) {
				GameManager.getInstance().setSelectedTile(null);
				IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
				prop.set(prop.get() == 2 ? -1 : 2);
			} else if (e.getCode() == KeyCode.DIGIT4) {
				GameManager.getInstance().setSelectedTile(null);
				IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
				prop.set(prop.get() == 3 ? -1 : 3);
			} else if (e.getCode() == KeyCode.S) {
				GameManager.getInstance().sellTower();
			} else if (e.getCode() == KeyCode.D) {
				GameManager.getInstance().upgradeTower();
			} else if (e.getCode() == KeyCode.Z) {
				GameManager.getInstance().addMoney(1000);
			}
			e.consume(); // prevent 'ding' sound 
		});
	}

	public ButtonManager getButtonManager() {
		return buttonManager;
	}

}
