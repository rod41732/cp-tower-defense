package ui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
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

	
	public static Button make(double x, double y, Image imgNormal, Image imgHover, Image imgDisable, Image imgPressed, Font font, String text) {
		Button btn = new Button(text);
		btn.setLayoutX(x);
		btn.setLayoutY(y);
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
		btn.setPadding(Insets.EMPTY);
		
		btn.setOnMouseEntered(e -> {
			btn.setBackground(bgHover);
			btn.setPrefWidth(imgHover.getWidth());
			btn.setPrefHeight(imgHover.getHeight());
		});		
		btn.setOnMouseExited(e -> {
			if (!e.isPrimaryButtonDown()) {
				btn.setBackground(bgNormal);
				btn.setPrefWidth(imgNormal.getWidth());
				btn.setPrefHeight(imgNormal.getHeight());
			}
		});
		btn.setOnMousePressed(e -> {
			btn.setBackground(bgPressed);
			btn.setPrefWidth(imgPressed.getWidth());
			btn.setPrefHeight(imgPressed.getHeight());
		});
		btn.setOnMouseReleased(e -> {
			btn.setBackground(bgNormal);
			btn.setPrefWidth(imgNormal.getWidth());
			btn.setPrefHeight(imgNormal.getHeight());
		});
		
		return btn;
	}
	
	
	public static Button make(double x, double y, Image imgNormal, Image imgPressed, Font font, String text) {
		Button btn = new Button(text);
		btn.setLayoutX(x);
		btn.setLayoutY(y);
		BackgroundImage bn = new BackgroundImage(imgNormal, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgNormal = new Background(bn);
		BackgroundImage bp = new BackgroundImage(imgPressed, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgPressed = new Background(bp);
		
		btn.setFont(font);
		btn.setBackground(bgNormal);
		btn.setPrefWidth(imgNormal.getWidth());
		btn.setPrefHeight(imgNormal.getHeight());
		btn.setPadding(Insets.EMPTY);
		
		btn.setOnMousePressed(e -> {
			btn.setBackground(bgPressed);
			btn.setPrefWidth(imgPressed.getWidth());
			btn.setPrefHeight(imgPressed.getHeight());
		});
		btn.setOnMouseReleased(e -> {
			btn.setBackground(bgNormal);
			btn.setPrefWidth(imgNormal.getWidth());
			btn.setPrefHeight(imgNormal.getHeight());
		});
				
		return btn;
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
		});
					
		return btn;
	}
	
	
	
}
