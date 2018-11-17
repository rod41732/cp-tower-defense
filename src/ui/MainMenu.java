package ui;


import constants.Numbers;
import controller.GameManager;
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

public class MainMenu extends Scene {
	
	private Timeline menuTick;
	private Pane root;
	
	public MainMenu() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		
		// make some butons
		VBox menus = new VBox(20);
		Label title = new Label("CP Tower Defense");
		title.setFont(Font.font("Consolas", 72));
		Button settings = new Button("Settings");
		settings.setFont(Font.font("Tahoma", 40));
		Button start = new Button("Play");
		start.setFont(Font.font("Tahoma", 40));
		
		KeyFrame titleAnimX = new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(title.scaleXProperty(), 1.5)
				); 
		KeyFrame titleAnimY = new KeyFrame(Duration.seconds(0.5), 
				new KeyValue(title.scaleYProperty(), 1.5)
				); 
		
		menuTick = new Timeline();
		menuTick.setAutoReverse(true); // so animation is reverse
		menuTick.getKeyFrames().addAll(titleAnimX, titleAnimY);
		menuTick.setCycleCount(Timeline.INDEFINITE);
		menuTick.play();
		
		start.setOnAction(e -> {
			Main.setScene(Main.gameScene);
			menuTick.pause();
			GameManager.getInstance().setRunning(true);
		});
		
		settings.setOnAction(e -> {
			Main.setScene(Main.settings);
			menuTick.pause(); // to make it playable, it should be resumable from other clas
		});
	
		menus.getChildren().addAll(title, start, settings);
		menus.setMinWidth(Numbers.WIN_WIDTH);
		root.getChildren().addAll(menus);
	}
	
	public void resume() {
		menuTick.play();
	}
}
