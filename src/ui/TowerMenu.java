package ui;

import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Tile;
import model.Tower;
import util.cpp;
public class TowerMenu {
	
//	public static TowerMenu instance = new TowerMenu();
	
	private static double LEFT = 1344;
	private static double TOP = 0;
	private static int COL = 3;
	private static int ROW = 3;
	private static Image[] images = {Images.bombTower, Images.normalTower, Images.fireTower, Images.iceTower};
	private static ArrayList<Button> buttons = new ArrayList<>();
	
	private static Button upgradeButton, sellButton;
	
	
	static {
		upgradeButton = new Button(Images.buttonUpgrade,
				Images.buttonUpgradeHover, Images.buttonUpgradeDisabled, 1400, 575);
		upgradeButton.setHandler(e -> {
			GameManager.getInstance().upgradeTower();
		});
		sellButton = new Button(Images.buttonSell, Images.buttonSellHover,
				Images.buttonSellDisabled, 1400, 655);
		sellButton.setHandler(e -> {
			GameManager.getInstance().sellTower();
		});
		buttons.add(upgradeButton);
		buttons.add(sellButton);
	}
	
	public static void render(GraphicsContext gc) {
		gc.setFill(new Color(0, 1, 1, 1));
		gc.fillRect(LEFT, TOP, COL*Numbers.TILE_SIZE, ROW*Numbers.TILE_SIZE);
		
		int sel = GameManager.getInstance().getTowerChoice();
		if (0 <= sel && sel < images.length) 
			gc.strokeRect(LEFT+(sel%COL)*Numbers.TILE_SIZE, TOP+(sel/COL)*Numbers.TILE_SIZE,
					Numbers.TILE_SIZE, Numbers.TILE_SIZE);
		
		for (int i=0; i<images.length; i++) {
			gc.drawImage(images[i], LEFT+(i%COL)*Numbers.TILE_SIZE, TOP+(i/COL)*Numbers.TILE_SIZE);
		}
		
		gc.setFill(Color.MAGENTA);
		Tile t = GameManager.getInstance().getSelectedTile();
		if (t != null)
			renderTowerInfo(gc, t);

		
		
		for (Button b: buttons) b.render(gc);					
		
	}

	
	public static void renderTowerInfo(GraphicsContext gc, Tile t) {
		double top = 400;
		double left = LEFT;
		gc.setFill(Color.color(0, 0, 0, 0.8));
		Font title = Font.font("Consolas", FontWeight.BOLD, 22);
		Font text = Font.font("Consolas", 16);
//		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
//		double titleW = fontLoader.computeStringWidth("Bomb Tower", title);
//		double titleH = fontLoader.getFontMetrics(title).getLineHeight();
//		// TODO: better calculate this
		Tower tw = (Tower) t;
		
		gc.fillRoundRect(left , top , 300, 200, 4, 4);
		gc.setFill(Color.color(1, 0.6, 0.4, 0.8));
		gc.setFont(title);
		gc.fillText(tw.toString(), left+8, top+30);
		gc.setFont(text);
		gc.fillText(tw.description(), left+8, top+65);
		
//		sellButtonPos.first = t.getRenderX()+128;
//		sellButtonPos.second = t.getRenderY();
//		
//		upButtonPos.first = t.getRenderX()+128;
//		upButtonPos.second = t.getRenderY()+64;		
	}
	
	public static boolean shouldHandle(MouseEvent e) {
		
		
		double x = e.getX();
		double y = e.getY();
		return LEFT < x && x < LEFT+COL*Numbers.TILE_SIZE &&
				TOP < y && y < TOP+ROW*Numbers.TILE_SIZE;
	}
	
	public static void handleMouseMove(MouseEvent e) {
		for (Button b: buttons) b.handleHover(e);			
	}
	
	public static void handleClick(MouseEvent e) {
		if (e.isConsumed()) return ;
		
		for (Button b: buttons) b.handleClick(e);			
//		GameManager.getInstance().setSelectedTile(null);
		if (shouldHandle(e)) {
			cpp.pii grid = posToGrid(e.getX(), e.getY());
			int s = grid.first+grid.second*COL;
			System.out.println("selected" + s);
			if (0 <= s && s <= images.length) {
				GameManager.getInstance().setTowerChoice(grid.first+grid.second*COL);				
			}
			return ;
		}		
		return ;
	}
	
	private static cpp.pii posToGrid(double x, double y){
		return new cpp.pff((x-LEFT)/Numbers.TILE_SIZE, (y-TOP)/Numbers.TILE_SIZE).toI();
	}


	public static Button getUpgradeButton() {
		return upgradeButton;
	}


	public static Button getSellButton() {
		return sellButton;
	}
	
	
	
	
}
