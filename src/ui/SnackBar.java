package ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableDoubleValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SnackBar {
	
	private static class DoubleWrapper implements WritableDoubleValue {
		private double x;
		public DoubleWrapper(double x) {
			this.x = x;
		}
		@Override
		public Number getValue() {
			return x;
		}

		@Override
		public double get() {
			return x;
		}

		@Override
		public void set(double value) {
			this.x = value;
		}

		@Override
		public void setValue(Number value) {
			this.x=  (double) value;
			
		}
		
	}
	
	
	private static boolean isShown;
	private static String message = "Hello";
	private static DoubleWrapper x = new DoubleWrapper(700);
	private static DoubleWrapper y = new DoubleWrapper(0);
	private static DoubleWrapper opacity = new DoubleWrapper(0);
	private static double w = 200, h = 75;
	public static void render(GraphicsContext gc) {
		if (isShown) {
			gc.setFill(Color.color(0, 0, 0, 0.8));
			gc.fillRect(x.get(), y.get(), w, h);
			gc.setFill(Color.color(1, 1, 1, 0.9));
			gc.fillText(message, x.get(), y.get()+ 25);			
		}
	}
	
	public static void play(String msg) {
		Timeline tl = new Timeline();
		Timeline rev = new Timeline();
		isShown = true;
		y.set(800);
		opacity.set(0.4);
		message = msg;
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
		
		
		tl.play();
	}
}
