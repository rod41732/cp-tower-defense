package ui;


import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.GameManager;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.Main;

public class MainMenuScene extends Scene {
	
	private Timeline menuTick;
	private Pane root;
	private boolean showOverlay = false;
	
	
	public MainMenuScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		
		// make some butons
		Pane menus = new Pane();
		Label title = new Label("CP Tower Defense");
		title.setFont(Font.font("Consolas", 72));
		title.setLayoutX(700);
		title.setLayoutY(300);
		Button newGame = ButtonMaker.make(700, 200, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "New Game");
		Button settings = ButtonMaker.make(700, 275, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "Settings");
		Button start = ButtonMaker.make(700, 350, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "Resume");
		
		KeyFrame titleAnimX = new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(title.scaleXProperty(), 1.5)
				); 
		KeyFrame titleAnimY = new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(title.scaleYProperty(), 1.5)
				); 
		
		menuTick = new Timeline();
		menuTick.setAutoReverse(true); // so animation is reverse
		menuTick.getKeyFrames().addAll(titleAnimX, titleAnimY);
		menuTick.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),e->{
			start.setDisable(!GameManager.getInstance().isInitialized());
		}));
		menuTick.setCycleCount(Timeline.INDEFINITE);
		menuTick.play();
		
		// tickle the superManager;
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
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			boolean inGame = nw.booleanValue();
			if (!inGame) menuTick.play();
			else menuTick.pause();
		});
		newGame.setOnAction(e -> {
			System.out.println("new game");

			GameManager.getInstance().newGame();
			GameManager.getInstance().initialize();
			Main.setScene(Main.getGameScene());
			SuperManager.getInstance().onResumeGame();
			menuTick.pause();
		});
		
		
		start.setOnAction(e -> {
		
			SuperManager.getInstance().getIsInGameProp().set(true);
			
			GameManager.getInstance().initialize();
			Main.setScene(Main.getGameScene());
			menuTick.pause();
		});
		
		settings.setOnAction(e -> {
			Main.setScene(Main.getSettings());
			menuTick.pause(); // to make it playable, it should be resumable from other clas
		});
	
		// TODO: change back
//		menus.getChildren().addAll(title, start, settings, newGame);
		menus.getChildren().addAll(title, start, settings);
		menus.setMinWidth(Numbers.WIN_WIDTH);
		root.getChildren().addAll(menus);
		
		// ---------------------------
		Pane overlay = new Pane();
		Canvas test = new Canvas(1600, 900);
		overlay.getChildren().add(test);
		Button back = ButtonMaker.make(0, 0, Images.buttonUpgrade, Images.buttonUpgradePressed, Other.normalButtonFont, "Back");
		overlay.getChildren().add(back);
		
		overlay.setMouseTransparent(false);
		overlay.setLayoutY(900);
		
		test.getGraphicsContext2D().setFill(Color.color(1, 1,0, 0.5));
		test.getGraphicsContext2D().fillRect(0, 0, 1600, 900);
		Timeline show = new Timeline(new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(overlay.layoutYProperty(), 0)
		));
		Timeline hide = new Timeline(new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(overlay.layoutYProperty(), 900)
		));
		test.setOnMouseClicked(e -> {
			System.out.println("yoinked");
			e.consume();
		});
		back.setOnAction(e -> {
			hide.play();
			showOverlay = false;
		}); 
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.M) {
				if (!showOverlay) {
					show.play();
					System.out.println("play");
//					toggle.setRate(1);
				}
				else {
					hide.play();
				}
				showOverlay = !showOverlay;
			}
		});
		overlay.getChildren().add(newGame);
		root.getChildren().add(overlay);
		// ------------------------
	}
	
	public void resume() {
		menuTick.play();
	}
}
