package main;
//package main;

import constants.Numbers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GameScene;
import ui.MainMenu;
import ui.Settings;

public class Main extends Application {

	
	private static Stage stage;
	// TODO: Change
	public static MainMenu mainMenu = new MainMenu();
	public static GameScene gameScene = new GameScene();
	public static Settings settings = new Settings();
	
	@Override
	public void start(Stage primaryStage) {
		
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

	public static MainMenu getMainMenu() {
		return mainMenu;
	}

	public static GameScene getGameScene() {
		return gameScene;
	}

	public static Settings getSettings() {
		return settings;
	}
	
	
	
	
	
}
