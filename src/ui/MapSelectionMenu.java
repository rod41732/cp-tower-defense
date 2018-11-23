package ui;

import constants.Images;
import constants.Maps;
import constants.Numbers;
import constants.Other;
import controller.GameManager;
import controller.SuperManager;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Main;

public class MapSelectionMenu extends Pane{
	
	private int choice;
	
	public MapSelectionMenu() {
		super();
		choice = -1;
		Canvas backGround = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Button backButton = ButtonMaker.make(0, 0, Images.buttonUpgrade, Images.buttonUpgradePressed, Other.normalButtonFont, "Back");
		Button newGameButton = ButtonMaker.make(1300, 740, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "New Game");
		newGameButton.setOnAction(e -> {
			GameManager.getInstance().newGame();
			GameManager.getInstance().initialize(1);
			Main.setScene(Main.getGameScene());
			SuperManager.getInstance().onResumeGame();
			Main.getMainMenu().hideMapSelect();
		});
		setLayoutY(Numbers.WIN_HEIGHT);
		for (int i=0; i<Maps.numOfAvaiableMaps(); i++) {
		}
		backGround.getGraphicsContext2D().setFill(Color.color(1, 1,0, 0.5));
		backGround.getGraphicsContext2D().fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		backButton.setOnAction(e -> {
			Main.getMainMenu().hideMapSelect();
		});
		getChildren().addAll(backGround, backButton, newGameButton);
	}
	
}
	