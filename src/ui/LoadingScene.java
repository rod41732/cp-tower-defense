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
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoadingScene extends Scene {

	double curProg = 0;
	Timeline tl;
	
	public LoadingScene() {
//		Queue<Integer> q = new LinkedQue`
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane)getRoot();
		Canvas bg = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		GraphicsContext gc = bg.getGraphicsContext2D();
		
		
		
		tl = new Timeline(new KeyFrame(Duration.seconds(1./60), e-> {
			double prog = Images.getProgress().get();
			curProg = (prog*1+curProg*10)/11.;
//			curProg = prog;
			for (int i=1; i<=5; i++) {
				gc.save();
				gc.setGlobalAlpha((curProg > i/5. || i == 1) ? (1): (curProg > i/5.-0.2 ? ((curProg-i/5.+0.2)*5): (0))  );
				gc.drawImage(Images.loading[i-1], 0, 0);
				gc.restore();
			}
			gc.save();
			gc.setGlobalAlpha(0.8);
			gc.drawImage(Images.vignette, 560*curProg, 336*curProg, 1600-2*560*curProg, 960-2*336*curProg, 0, 0, 1600, 960);

			if (curProg < 0.95) { // stop when "full"
				gc.setGlobalAlpha(1);
				gc.setFont(Font.font(72));
				gc.setFill(Color.WHITE);
				gc.fillText("Loading ...", 1100, 800);
				
				gc.setStroke(Color.WHITE);
				gc.setFill(Color.RED);
				gc.strokeRoundRect(140, 845, 1350, 90, 8, 8);
				gc.fillRoundRect(159, 859, 1300*curProg, 64, 8, 8);				
			}
			if (prog > 0.99 && curProg > 0.99) tl.pause(); 
			gc.restore();
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
		root.getChildren().addAll(bg);
	}
}
