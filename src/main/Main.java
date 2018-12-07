package main;
//package main;

import constants.Images;
import constants.Maps;
import constants.Numbers;
import controller.game.MonsterSpawnerThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GameScene;
import ui.LoadingScene;
import ui.MainMenuScene;

public class Main extends Application {

	
	private static Stage stage;
	// TODO: Change
	private static MainMenuScene mainMenu;
	private static GameScene gameScene;
	private static LoadingScene loadingScene;
	@Override
	public void start(Stage primaryStage) {		
		loadingScene = new LoadingScene();
		
		new Thread(() ->  {
			loadAll();
		}).start();;	
		
		stage = primaryStage;
		stage.setScene(loadingScene);
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
	
	public void loadAll() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Images.loadResource();
		Maps.loadMap();
		mainMenu = new MainMenuScene();
		gameScene = new GameScene();
		Platform.runLater(() -> {
			stage.setScene(mainMenu);
		});
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
