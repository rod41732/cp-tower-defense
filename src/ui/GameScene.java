package ui;


import constants.Numbers;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameScene extends Scene {
	private GameButton buttonManager;
	private Timeline gameTick;
	private StackPane root;
	
	public GameScene() {
		super(new StackPane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (StackPane) getRoot();
//		root.setLayoutY(16); // HAXX
		GridPane main = new GridPane();
		main.setHgap(0);
		main.setVgap(0);
//		main
		HBox topbar = new HBox();
		StackPane gameArea = new StackPane();
		VBox menus = new VBox();
		HBox bottomBar = new HBox();
		bottomBar.setPrefHeight(64);
		topbar.setPrefHeight(64);
		main.add(topbar, 0, 0, 1, 1);
		main.add(gameArea, 0, 1, 1, 1);
		main.add(menus, 1, 1, 1, 2);
		main.add(bottomBar, 0, 2, 1, 1);

		
		Canvas other = new Canvas(Numbers.COLUMNS*Numbers.TILE_SIZE, Numbers.ROWS*Numbers.TILE_SIZE);
		Canvas tiles = new Canvas(Numbers.COLUMNS*Numbers.TILE_SIZE, Numbers.ROWS*Numbers.TILE_SIZE);
		Canvas overlay = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gameArea.getChildren().addAll(tiles, other);
		
		TilePane towerChoices = new TilePane();
		towerChoices.setPrefRows(3);
		towerChoices.setPrefColumns(3);
		menus.getChildren().add(towerChoices);
		
		buttonManager = new GameButton();
		buttonManager.addControlButton(bottomBar);
		buttonManager.addTowerButtons(towerChoices);
		GameUI.mountPanel(menus);
		buttonManager.addUpgradeButton(menus); //
		buttonManager.addMenuButtons(root);
		GameUI.addinfo(topbar);
		topbar.setAlignment(Pos.CENTER_LEFT);
		
		
		
		root.getChildren().add(main);
		root.getChildren().add(overlay);
		
		overlay.setMouseTransparent(true);
		tiles.setMouseTransparent(true);
		GameManager.getInstance().setGC(other.getGraphicsContext2D(), tiles.getGraphicsContext2D(), overlay.getGraphicsContext2D());	
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			boolean pause = nw.booleanValue();
			if (pause) {
				overlay.setMouseTransparent(false);
			}
			else {
				overlay.setMouseTransparent(true);
			}
			
		});
		
		other.setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e);
		});
		
		other.setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		fadeOut();
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			boolean inGame = nw.booleanValue();
			if (inGame) {
				fadeIn();
			}
			else {
				fadeOut();
			}
		});
		
		setOnKeyPressed(e -> {
			GameManager.getInstance().handleKeyPress(e);
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
