package ui;


import constants.Numbers;
import controller.GameManager;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.Main;

public class MainMenuScene extends Scene {
	
	private Timeline menuTick;
	private Pane root;
	
	public MainMenuScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		
		// make some butons
		VBox menus = new VBox(20);
		Label title = new Label("CP Tower Defense");
		title.setFont(Font.font("Consolas", 72));
		Button settings = new Button("Settings");
		settings.setFont(Font.font("Tahoma", 40));
		Button start = new Button("Resume ?");
		start.setFont(Font.font("Tahoma", 40));
		
		Button newGame = new Button("new game!!");
		newGame.setFont(Font.font("Tahoma", 40));
		
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
	
		menus.getChildren().addAll(title, start, settings, newGame);
		menus.setMinWidth(Numbers.WIN_WIDTH);
		root.getChildren().addAll(menus);
	}
	
	public void resume() {
		menuTick.play();
	}
}
