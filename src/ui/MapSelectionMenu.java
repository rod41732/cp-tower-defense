package ui;

import constants.Images;
import constants.Maps;
import constants.Numbers;
import constants.Other;
import controller.GameManager;
import controller.SuperManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import main.Main;

public class MapSelectionMenu extends Pane{
	
	private int choice;
	
	public MapSelectionMenu() {
		super();
		choice = -1;
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Button backButton = ButtonMaker.make(0, 0, Images.buttonUpgrade, Images.buttonUpgradePressed, Other.normalButtonFont, "Back");
		Button newGameButton = ButtonMaker.make(1300, 740, Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "New Game");
		
		ToggleGroup toggleGroup = new ToggleGroup();
		newGameButton.setOnAction(e -> {
			GameManager.getInstance().newGame();
			GameManager.getInstance().initialize((int)toggleGroup.getSelectedToggle().getUserData());
			Main.setScene(Main.getGameScene());
			SuperManager.getInstance().onResumeGame();
			Main.getMainMenu().hideMapSelect();
		});
		setLayoutY(Numbers.WIN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.color(1, 0, 1, 0.8));
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		getChildren().addAll(canvas, backButton, newGameButton);
//		RadioButton rdio = new RadioButton();
		for (int i=0; i<Maps.numOfAvaiableMaps(); i++) { // add button after 
			RadioButton btn = ButtonMaker.makeMapSelectButton(250+(i%3)*450, 150+(i/3)*307,
					Maps.getMap(i).getPreviewImage(), Maps.getMap(i).getPreviewImage(), i);
			toggleGroup.getToggles().add(btn);
			Label lbl = new Label("Map " + i);
			lbl.setFont(Other.normalButtonFont);
			lbl.setPrefSize(350, 32);
			lbl.setTextAlignment(TextAlignment.CENTER);
			lbl.setLayoutX(250+(i%3)*450);
			lbl.setLayoutY(150+(i/3)*307+236);
			
			getChildren().addAll(btn, lbl);
		}
		toggleGroup.selectToggle(toggleGroup.getToggles().get(0));
		backButton.setOnAction(e -> {
			Main.getMainMenu().hideMapSelect();
		});
	}
	
}
	