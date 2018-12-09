package ui;

import constants.Images;
import constants.Maps;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;
import ui.component.ButtonMaker;

public class MapSelectionMenu extends Pane{
	
	
	public MapSelectionMenu() {
		super();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Button backButton = ButtonMaker.make(Images.buttonUpgrade, Images.buttonUpgradePressed, Other.normalButtonFont, "Back");
		Button newGameButton = ButtonMaker.make(Images.buttonSell, Images.buttonSellPressed, Other.normalButtonFont, "New Game");
		backButton.setLayoutX(50);
		backButton.setLayoutY(50);
		newGameButton.setLayoutX(1360);
		newGameButton.setLayoutY(750);
		ToggleGroup toggleGroup = new ToggleGroup();
		newGameButton.setOnAction(e -> {
			SuperManager.getInstance().onReset();
			GameManager.getInstance().loadMap((int)toggleGroup.getSelectedToggle().getUserData());
			SuperManager.getInstance().onResumeGame();
			Main.getMainMenu().hideMapSelect();
			Timeline tl = new Timeline();
			SuperManager.getInstance().getGameStateProp().set(0);
			tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), e2-> {
			}));
			tl.setOnFinished(e2 -> {Main.setScene(Main.getGameScene());});
			
		});
		setLayoutY(Numbers.WIN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(Images.bgMapSelect, 0, 0);
		getChildren().addAll(canvas, backButton, newGameButton);
		for (int i=0; i<Maps.numOfAvaiableMaps(); i++) {
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
	