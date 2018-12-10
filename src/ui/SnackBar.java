package ui;

import com.sun.javafx.tk.Toolkit;

import constants.Other;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SnackBar {
	
	private static final Font font = Other.loadFontWithSize(20); 
	
	private static boolean isShown;
	private static String message = "Hello";
	private static DoubleProperty x = new SimpleDoubleProperty(700);
	private static DoubleProperty y = new SimpleDoubleProperty(0);
	private static DoubleProperty opacity = new SimpleDoubleProperty(0);
	private static double w = 200, h = 75;
	private static Timeline tl = new Timeline();
	private static Timeline rev = new Timeline();
	
	static {
		rev.getKeyFrames().add(new KeyFrame(Duration.seconds(0.350),
				new KeyValue(y, 900, Interpolator.EASE_OUT)));
		rev.getKeyFrames().add(new KeyFrame(Duration.seconds(0.170), 
				new KeyValue(opacity, 0, Interpolator.EASE_OUT)));
		rev.setOnFinished(e -> {isShown = false;});
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.350),
				new KeyValue(y, 600, Interpolator.EASE_OUT)));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1.5), e->{})); // some pause
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.175),
				new KeyValue(opacity, 1, Interpolator.EASE_OUT)));
		tl.setOnFinished(e -> {
			rev.play();
		});
	}
	public static void render(GraphicsContext gc) {
		if (isShown) {
			gc.setFont(font);
			w = Toolkit.getToolkit().getFontLoader().computeStringWidth(message, font);
			h = Toolkit.getToolkit().getFontLoader().getFontMetrics(font).getLineHeight();
			gc.setFill(Color.color(0, 0, 0, 0.8));
			gc.fillRect(x.get(), y.get(), w+32, h+32);
			gc.setFill(Color.color(1, 1, 1, 0.9));
			gc.fillText(message, x.get()+16, y.get()+h+16);			
		}
	}
	
	public static void play(String msg) {
		isShown = true;
		y.set(800);
		opacity.set(0.4);
		message = msg;
		tl.stop();
		rev.stop();		
		tl.play();
	}
}
