package ui;



import constants.Images;
import constants.Numbers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoadingScene extends Scene {

	double curProg = 0;
	Timeline tl;
	
	public LoadingScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane)getRoot();
		Canvas bg = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		GraphicsContext gc = bg.getGraphicsContext2D();
		
		double loadingFrameLeft = 280, loadingFrameTop = 550;
		double loadingW = 1010, loadingH = 17, loadingLeft = loadingFrameLeft+15,
				loadingTop = loadingFrameTop+15;
		
		tl = new Timeline(new KeyFrame(Duration.seconds(1./60), e-> {
			double prog;
			try {
				prog = Images.getProgress().get();				
			}
			catch (Exception ex) {
				tl.pause();
				prog = 0;
			}
			curProg = (prog*0.1+curProg*0.9); // smooth bar
			if (curProg >= 0.99) curProg = 1;
			for (int i=1; i<=5; i++) { // calculate fade
				gc.save();
				gc.setGlobalAlpha((curProg > i/5. || i == 1) ? (1): (curProg > i/5.-0.2 ? ((curProg-i/5.+0.2)*5): (0))  );
				gc.drawImage(Images.loading[i-1], 0, 0);
				gc.restore();
			}
			gc.save();
			gc.setGlobalAlpha(0.8);
			// zooming vignetete
			gc.drawImage(Images.vignette, 700*curProg, 394*curProg, Numbers.WIN_WIDTH-2*700*curProg, Numbers.WIN_HEIGHT-2*394*curProg, 0, 0, 1600, 960);

			if (prog > 0.99 && curProg > 0.99) tl.pause(); 
			gc.restore();
			gc.drawImage(Images.logo, 0, 0);
			gc.setFill(Color.color(0.2, 0.2, 0.2));
			gc.drawImage(Images.loadingBar, loadingFrameLeft, loadingFrameTop);
			gc.fillRect(loadingLeft, loadingTop, loadingW*curProg, loadingH);
			gc.drawImage(Images.loadingText, 540, 470);
		
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
		root.getChildren().addAll(bg);
	}
	
}
