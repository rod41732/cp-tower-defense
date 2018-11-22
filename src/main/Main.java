package main;
//package main;

import constants.Numbers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GameScene;
import ui.MainMenuScene;
import ui.Settings;

public class Main extends Application {

	
	private static Stage stage;
	// TODO: Change
	private static MainMenuScene mainMenu;
	private static GameScene gameScene;
	private static Settings settings;
	@Override
	public void start(Stage primaryStage) {		
		mainMenu = new MainMenuScene();
		gameScene = new GameScene();
		settings = new Settings();

		stage = primaryStage;
		
		stage.setScene(mainMenu);
		stage.setWidth(Numbers.WIN_WIDTH);
		stage.setHeight(Numbers.WIN_HEIGHT);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}

	public static MainMenuScene getMainMenu() {
		return mainMenu;
	}

	public static GameScene getGameScene() {
		return gameScene;
	}

	public static Settings getSettings() {
		return settings;
	}
	
	
	
	
	
}
