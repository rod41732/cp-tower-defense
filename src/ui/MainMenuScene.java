package ui;



import constants.Images;
import constants.Numbers;
import constants.Other;
import constants.Sounds;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ui.component.ButtonMaker;

public class MainMenuScene extends Scene {
	
	private Timeline menuTick;
	private Pane root;
	private MapSelectionMenu mapMenu;
	
	private Timeline showMapMenu, hideMapMenu;
	
	public MainMenuScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root = (Pane) getRoot();
		// make some butons
		VBox menus = new VBox();
		menus.setMinWidth(Numbers.WIN_WIDTH);
		menus.setMinHeight(Numbers.WIN_HEIGHT);
		menus.setAlignment(Pos.CENTER);
		menus.setPadding(new Insets(5));
		menus.setSpacing(20);
		
		Canvas menuBackground = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		GraphicsContext gc = menuBackground.getGraphicsContext2D();
		Button resume = ButtonMaker.make(Images.buttonOrange, Images.buttonOrangePressed, Other.normalButtonFont, "Resume");
	
		gc.setFill(Color.BLACK);
		resume.setAlignment(Pos.CENTER);
		
		Font creditFont = Other.loadFontWithSize(30);
		menuTick = new Timeline(new KeyFrame(Duration.seconds(0.5), e->{
			resume.setDisable(!GameManager.getInstance().isInitialized() || SuperManager.getInstance().getGameStateProp().get() != 0);
			gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
			for (int i=0; i<5; i++) {
				gc.drawImage(Images.loading[i], 0, 0);
			}
			gc.drawImage(Images.logo, 0, 0);
			gc.drawImage(Images.mainButtonBg, 635, 360);
			gc.save();
			gc.setTextAlign(TextAlignment.RIGHT);
			gc.setTextBaseline(VPos.BOTTOM);
			gc.setFill(Color.WHITE);
			gc.setFont(creditFont);
			gc.fillText("Music by Eric Matyas\n" + 
					"www.soundimage.org", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
			gc.restore();
		}));
		menuTick.setCycleCount(Timeline.INDEFINITE);
		menuTick.play();
		tickle();
		
		MediaPlayer bgmPlayer = new MediaPlayer(new Media(Sounds.mainMenuMusic.getSource()));
		bgmPlayer.setOnEndOfMedia(() -> {
			bgmPlayer.seek(Duration.ZERO);
		});
		bgmPlayer.play();
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			boolean inGame = nw.booleanValue();
			if (!inGame) {
				System.out.println("enter menu");
				menuTick.play();
				bgmPlayer.play();
				fadeIn();
			}
			else {
				System.out.println("exit menu");
				menuTick.pause();
				bgmPlayer.pause();
				bgmPlayer.seek(Duration.ZERO);
				fadeOut();
			}
		});
		
		Button newGame = ButtonMaker.make(Images.buttonYellow, Images.buttonYellowPressed,
				Other.normalButtonFont, "new game ...");
		newGame.setOnAction(e -> {
			showMapSelect();
		});
		resume.setOnAction(e -> {
			SuperManager.getInstance().onResumeGame();
		});
		menus.getChildren().addAll(resume,newGame);
		mapMenu = new MapSelectionMenu();
		mapMenu.setLayoutY(Numbers.WIN_HEIGHT);
		showMapMenu = new Timeline(new KeyFrame(Duration.seconds(0.3), 
				new KeyValue(mapMenu.layoutYProperty(), 0)));		
		hideMapMenu = new Timeline(new KeyFrame(Duration.seconds(0.3), 
				new KeyValue(mapMenu.layoutYProperty(), Numbers.WIN_HEIGHT)));
				
		root.getChildren().addAll(menuBackground, menus, mapMenu);	
	}
	
	public void showMapSelect() {
		menuTick.pause();
		showMapMenu.play();
	}
	
	public void hideMapSelect() {
		menuTick.play();
		hideMapMenu.play();
	}
	
	public void tickle() {
		SuperManager.getInstance().getIsInGameProp().set(false);
		SuperManager.getInstance().getCanSellProp().set(false);
		SuperManager.getInstance().getCanUpgradeProp().set(false);
		SuperManager.getInstance().getIsGamePausedProp().set(false);
		SuperManager.getInstance().getnextWaveAvailableProp().set(false);
		SuperManager.getInstance().getIsInGameProp().set(true);
		SuperManager.getInstance().getCanSellProp().set(true);
		SuperManager.getInstance().getCanUpgradeProp().set(true);
		SuperManager.getInstance().getIsGamePausedProp().set(true);
		SuperManager.getInstance().getnextWaveAvailableProp().set(true);
		SuperManager.getInstance().getIsInGameProp().set(false);
		SuperManager.getInstance().getCanSellProp().set(false);
		SuperManager.getInstance().getCanUpgradeProp().set(false);
		SuperManager.getInstance().getIsGamePausedProp().set(false);
		SuperManager.getInstance().getnextWaveAvailableProp().set(false);
	}
	
	public void fadeIn() {
		new Timeline(
			new KeyFrame(Duration.seconds(0.5),
				new KeyValue(root.opacityProperty(), 1))).play();
	}
	
	public void fadeOut() {
		new Timeline(
				new KeyFrame(Duration.seconds(0.5),
					new KeyValue(root.opacityProperty(), 0))).play();	
	}
}




