package ui;


import constants.Numbers;
import controller.GameManager;
import controller.SuperManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameScene extends Scene {
	private GameButton buttonManager;
	private Timeline gameTick;
	private Pane root;
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		Canvas other = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Canvas tiles = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Canvas overlay = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		other.setLayoutX(Numbers.LEFT_OFFSET);
		other.setLayoutY(Numbers.TOP_OFFSET);
		tiles.setLayoutX(Numbers.LEFT_OFFSET);
		tiles.setLayoutY(Numbers.TOP_OFFSET);
		
		root.getChildren().add(tiles); // TILE
		root.getChildren().add(other); // Other
		buttonManager = new GameButton(root);
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
			if (pause) {
				overlay.setMouseTransparent(false);
			}
			else {
				overlay.setMouseTransparent(true);
			}
			
		});
		
		other.setOnMouseClicked(e -> {System.out.println("c1");});
		
		
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);

		setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		root.setOpacity(0);
		root.setScaleX(1.2);
		root.setScaleY(1.2);
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			System.out.println("ig change");
			boolean inGame = nw.booleanValue();
			if (inGame) {
				fadeIn();
				gameTick.play();
			}
			else {
				fadeOut();
				gameTick.pause();
			}
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

	public GameButton getButtonManager() {
		return buttonManager;
	}
	public void fadeIn() {
		new Timeline(
			new KeyFrame(Duration.seconds(0.3),
				new KeyValue(root.opacityProperty(), 1, Interpolator.EASE_BOTH),
				new KeyValue(root.scaleXProperty(),  1, Interpolator.EASE_BOTH),
				new KeyValue(root.scaleYProperty(), 1, Interpolator.EASE_BOTH))).play();
	}
	
	public void fadeOut() {
		new Timeline(
				new KeyFrame(Duration.seconds(0.5),
					new KeyValue(root.opacityProperty(), 0, Interpolator.EASE_BOTH),
					new KeyValue(root.scaleXProperty(),  1.2, Interpolator.EASE_BOTH),
					new KeyValue(root.scaleYProperty(), 1.2, Interpolator.EASE_BOTH))).play();
	}

}
