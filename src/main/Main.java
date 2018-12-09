package main;
//package main;

import constants.Images;
import constants.Maps;
import constants.TowerStats;
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
		stage = primaryStage;
		stage.setTitle("Higon TD");
		loadingScene = new LoadingScene();
		stage.setScene(loadingScene);
		stage.setResizable(false);
		stage.show();
		
		new Thread(() ->  {
			loadAll();
		}).start();
		
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
			Images.loadResource();
			Maps.loadMap();
			TowerStats.loadTower();
			mainMenu = new MainMenuScene();
			gameScene = new GameScene();
			Thread.sleep(300);
			Platform.runLater(() -> {
				stage.setScene(mainMenu);
			});
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
