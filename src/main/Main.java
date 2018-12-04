package main;
//package main;

import constants.Numbers;
import controller.game.MonsterSpawnerThread;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GameScene;
import ui.MainMenuScene;

public class Main extends Application {

	
	private static Stage stage;
	// TODO: Change
	private static MainMenuScene mainMenu;
	private static GameScene gameScene;
	@Override
	public void start(Stage primaryStage) {		
		mainMenu = new MainMenuScene();
		gameScene = new GameScene();

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
	
	@Override
	public void stop() throws Exception {
		MonsterSpawnerThread.getInstance().onGameReset();
		MonsterSpawnerThread.getInstance().interrupt();
		super.stop();
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
	
	
}
