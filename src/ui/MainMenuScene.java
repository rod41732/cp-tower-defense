package ui;



import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ui.component.ButtonMaker;

public class MainMenuScene extends Scene {
	
	private Timeline menuTick;
	private Pane root;
	private MapSelectionMenu mapMenu;
	private boolean isMapMenuShown = false;
	
	private Timeline showMapMenu, hideMapMenu;
	
	public MainMenuScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		
		// make some butons
		Pane menus = new Pane();
		Label title = new Label("CP Tower Defense");
		Canvas menuBackground = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		GraphicsContext gc = menuBackground.getGraphicsContext2D();
		title.setFont(Font.font("Consolas", 72));
		title.setLayoutX(700);
		title.setLayoutY(300);
		Button resume = ButtonMaker.make(700, 350, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "Resume");
//		root.setStyle("-fx-background: red");
		menuTick = new Timeline(new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(title.scaleXProperty(), 1.5),
				new KeyValue(title.scaleYProperty(), 1.5)));
		
		gc.setFill(Color.BLACK);
		gc.setGlobalAlpha(0.8);
		menuTick.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),e->{
			resume.setDisable(!GameManager.getInstance().isInitialized() || SuperManager.getInstance().getGameStateProp().get() != 0);
			gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
			for (int i=0; i<5; i++) {
				gc.drawImage(Images.loading[i], 0, 0);
			}
		}));
		menuTick.setAutoReverse(true); 
		menuTick.setCycleCount(Timeline.INDEFINITE);
		menuTick.play();
		tickle();
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			boolean inGame = nw.booleanValue();
			if (!inGame) {
				System.out.println("enter menu");
				menuTick.play();
				fadeIn();
			}
			else {
				System.out.println("exit menu");
				menuTick.pause();
				fadeOut();
			}
		});
		
		Button newGame = ButtonMaker.make(700, 290, Images.buttonPause, Images.buttonPausePressed,
				Other.normalButtonFont, "new game ...");
		newGame.setOnAction(e -> {
			showMapSelect();
		});
		resume.setOnAction(e -> {
			SuperManager.getInstance().onResumeGame();
		});
		menus.getChildren().addAll(title, resume);
		mapMenu = new MapSelectionMenu();
		mapMenu.setLayoutY(Numbers.WIN_HEIGHT);
		showMapMenu = new Timeline(new KeyFrame(Duration.seconds(0.3), 
				new KeyValue(mapMenu.layoutYProperty(), 0)));		
		hideMapMenu = new Timeline(new KeyFrame(Duration.seconds(0.3), 
				new KeyValue(mapMenu.layoutYProperty(), Numbers.WIN_HEIGHT)));
				
		root.getChildren().addAll(menuBackground, menus, newGame, mapMenu);	
	}
	
	public void showMapSelect() {
		menuTick.pause();
		showMapMenu.play();
		isMapMenuShown = true;
	}
	
	public void hideMapSelect() {
		menuTick.play();
		hideMapMenu.play();
		isMapMenuShown = false;
	}
	
	public void tickle() {
		SuperManager.getInstance().getIsInGameProp().set(false);
		SuperManager.getInstance().getCanSellProp().set(false);
		SuperManager.getInstance().getCanUpgradeProp().set(false);
		SuperManager.getInstance().getIsGamePausedProp().set(false);
		SuperManager.getInstance().getnextWaveAvailableProp().set(false);
		SuperManager.getInstance().getIsInGameProp().set(true);
		SuperManager.getInstance().getCanSellProp().set(true);
		SuperManager.getInstance().getCanUpgradeProp().set(true);
		SuperManager.getInstance().getIsGamePausedProp().set(true);
		SuperManager.getInstance().getnextWaveAvailableProp().set(true);
		SuperManager.getInstance().getIsInGameProp().set(false);
		SuperManager.getInstance().getCanSellProp().set(false);
		SuperManager.getInstance().getCanUpgradeProp().set(false);
		SuperManager.getInstance().getIsGamePausedProp().set(false);
		SuperManager.getInstance().getnextWaveAvailableProp().set(false);
	}
	
	public void fadeIn() {
		new Timeline(
			new KeyFrame(Duration.seconds(0.5),
				new KeyValue(root.opacityProperty(), 1))).play();
	}
	
	public void fadeOut() {
		new Timeline(
				new KeyFrame(Duration.seconds(0.5),
					new KeyValue(root.opacityProperty(), 0))).play();	
	}
}




