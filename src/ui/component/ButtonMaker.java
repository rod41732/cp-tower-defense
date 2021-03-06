package ui.component;

import constants.Sounds;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import model.Tower;

public class ButtonMaker {

	
	public static Button make(Image imgNormal, Image imgPressed, Image imgHover, Image imgDisable, Font font, String text) {
		Button btn = new Button(text);
		BackgroundImage bn = new BackgroundImage(imgNormal, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgNormal = new Background(bn);
		BackgroundImage bh = new BackgroundImage(imgHover, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgHover = new Background(bh);
		BackgroundImage bd = new BackgroundImage(imgDisable, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgDisabled = new Background(bd);
		BackgroundImage bp = new BackgroundImage(imgPressed, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgPressed = new Background(bp);
		
		
		btn.setFont(font);
		btn.setBackground(bgNormal);
		btn.setPrefWidth(imgNormal.getWidth());
		btn.setPrefHeight(imgNormal.getHeight());
		btn.setAlignment(Pos.CENTER);
		
		btn.setOnMouseEntered(e -> {
			btn.setBackground(bgHover);
			
		});		
		btn.setOnMouseExited(e -> {
			if (!e.isPrimaryButtonDown()) {
				btn.setBackground(bgNormal);
			
			}
		});
		btn.setOnMousePressed(e -> {
			btn.setBackground(bgPressed);
			
		});
		btn.setOnMouseReleased(e -> {
			btn.setBackground(bgNormal);
			System.out.printf("w/h is %.2f %.2f\n", imgNormal.getWidth(), imgNormal.getWidth());
			Sounds.click.play();
		});
		
		
		return btn;
	}
	
	
	public static Button make(Image imgNormal, Image imgPressed, Font font, String text) {
		return make(imgNormal, imgPressed, imgNormal, imgNormal, font, text);
	}

//	@SuppressWarnings("unchecked")
	public static ToggleButton makeTowerButton(double x, double y, Image imgNormal, Image imgPressed, Tower t,Font font, int value) {
		ToggleButton btn;

		btn = new ToggleButton();
		btn.setLayoutX(x);
		btn.setLayoutY(y);
		BackgroundImage bn = new BackgroundImage(imgNormal, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgNormal = new Background(bn);
		BackgroundImage bp = new BackgroundImage(imgPressed, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgPressed = new Background(bp);
		
		btn.setFont(font);
		btn.backgroundProperty().bind(Bindings.when(btn.selectedProperty())
				.then(bgPressed)
				.otherwise(bgNormal));
		
		// specific to tower
		btn.setGraphic(new ImageView(t.getImage()));
		btn.setText("$" + t.getPrice());
		btn.setUserData(value);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setAlignment(Pos.CENTER);
		
		
		btn.setPrefWidth(imgNormal.getWidth());
		btn.setPrefHeight(imgNormal.getHeight());
		btn.setPadding(Insets.EMPTY);
		
		btn.setOnMousePressed(e -> {
			btn.setPrefWidth(imgPressed.getWidth());
			btn.setPrefHeight(imgPressed.getHeight());
		});
		btn.setOnMouseReleased(e -> {
			btn.setPrefWidth(imgNormal.getWidth());
			btn.setPrefHeight(imgNormal.getHeight());
			Sounds.click.play();
		});
					
		return btn;
	}
	
	public static RadioButton makeMapSelectButton(double x, double y, Image imgNormal, Image imgPressed, int value) {
		RadioButton btn;
		btn = new RadioButton();
		btn.getStyleClass().remove("radio-button");
		btn.getStyleClass().add("toggle-button");
		btn.setLayoutX(x);
		btn.setLayoutY(y);
		BackgroundImage bn = new BackgroundImage(imgNormal, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgNormal = new Background(bn);
		BackgroundImage bp = new BackgroundImage(imgPressed, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgPressed = new Background(bp);
		
		btn.backgroundProperty().bind(Bindings.when(btn.selectedProperty())
				.then(bgPressed)
				.otherwise(bgNormal));
		
		btn.setUserData(value);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setAlignment(Pos.CENTER);
		
		
		btn.setPrefWidth(imgNormal.getWidth());
		btn.setPrefHeight(imgNormal.getHeight());
		
		ColorAdjust fade = new ColorAdjust(0, 0, -0.4, 0);
		btn.setEffect(fade);
		btn.setOnMousePressed(e -> {
			btn.setPrefWidth(imgPressed.getWidth());
			btn.setPrefHeight(imgPressed.getHeight());
			btn.setEffect(null);
		});
		btn.setOnMouseReleased(e -> {
			btn.setPrefWidth(imgNormal.getWidth());
			btn.setPrefHeight(imgNormal.getHeight());
			Sounds.click.play();
		});
		
		btn.selectedProperty().addListener((obs, old, nw) -> {
			if (nw.booleanValue()) {
				btn.setEffect(null);
			} else {
				btn.setEffect(fade);
			}
		});
		
					
		return btn;
	}
	
}
