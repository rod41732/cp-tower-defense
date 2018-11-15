package ui;

import java.util.ArrayList;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

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
	private static Image[] images = {Images.tower2, Images.tower1, Images.tower3};
//	private static cpp.pff sellButtonPos = new cpp.pff(-999,  -999);
//	private static cpp.pff upButtonPos = new cpp.pff(-999,  -999);;
		
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
		
		
//		gc.drawImage(Images.buttonSell, sellButtonPos.first, sellButtonPos.second);
//		gc.drawImage(Images.buttonUpgrade, upButtonPos.first, upButtonPos.second);
	}

	
	public static void renderTowerInfo(GraphicsContext gc, Tile t) {
		double top = Math.max(t.getRenderY() - 140, 0);
		double left = Math.max(t.getRenderX() - 150, 0);
		gc.setFill(Color.color(0, 0, 0, 0.8));
		Font title = Font.font("Consolas", FontWeight.BOLD, 22);
		Font text = Font.font("Consolas", 16);
//		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
//		double titleW = fontLoader.computeStringWidth("Bomb Tower", title);
//		double titleH = fontLoader.getFontMetrics(title).getLineHeight();
//		// TODO: better calculate this
		Tower tw = (Tower) t;
		
		gc.fillRoundRect(left , top , 300, 140, 4, 4);
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
//		if (sellButtonPos.first <= x && x <= sellButtonPos.first + 190 && 
//			sellButtonPos.second <= y && y <= sellButtonPos.second + 60 ) {
//			GameManager.getInstance().sellTower();
//			return true;
//		}
//		if (upButtonPos.first <= x && x <= upButtonPos.first + 190 && 
//				upButtonPos.second <= y && y <= upButtonPos.second + 60 ) {
//				GameManager.getInstance().upgradeTower();
//				return true;
//			}
		return LEFT < x && x < LEFT+COL*Numbers.TILE_SIZE &&
				TOP < y && y < TOP+ROW*Numbers.TILE_SIZE;
	}
	
	public static void handleClick(MouseEvent e) {
		System.out.println("clicked at" + posToGrid(e.getX(), e.getY()));
//		GameManager.getInstance().setSelectedTile(null);
		if (shouldHandle(e)) {
			cpp.pii grid = posToGrid(e.getX(), e.getY());
			int s = grid.first+grid.second*COL;
			System.out.println("selected" + s);
			GameManager.getInstance().setTowerChoice(grid.first+grid.second*COL);
			return ;
		}
		return ;
	}
	
	private static cpp.pii posToGrid(double x, double y){
		return new cpp.pff((x-LEFT)/Numbers.TILE_SIZE, (y-TOP)/Numbers.TILE_SIZE).toI();
	}
	
	
}
