package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.MainMenu;
import scenes.Settings;

public class Main extends Application {

	
	private static Stage stage;
	// TODO: Change
	public static Scene mainMenu = new MainMenu();
	public static Scene settings = new Settings();
	
	@Override
	public void start(Stage primaryStage) {
		
		stage = primaryStage;
		
		stage.setScene(mainMenu);
		stage.setWidth(1600);
		stage.setHeight(900);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
}
