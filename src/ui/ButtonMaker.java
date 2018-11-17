package ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class ButtonMaker {

	
	public static Button make(Image imgNormal, Image imgHover, Image imgDisable, Image imgPressed, Font font, String text) {
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
	
	public static Button make(Image imgNormal, Image imgPressed, Font font, String text) {
		Button btn = new Button(text);
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
}
