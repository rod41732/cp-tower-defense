package ui;

import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Tile;
import model.Tower;
import util.cpp;
public class TowerMenu {
	
//	public static TowerMenu instance = new TowerMenu();
	
	private static double LEFT = 1344;
	private static double TOP = 0;
	private static int COL = 3;
	private static int ROW = 3;
	private static Image[] images = {Images.tower2, Images.tower1};
	
	
	
	
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
			gc.fillText(t.toString(), LEFT, TOP+300);
	}

	public static boolean shouldHandle(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		return LEFT < x && x < LEFT+COL*Numbers.TILE_SIZE &&
				TOP < y && y < TOP+ROW*Numbers.TILE_SIZE;
	}
	
	public static void handleClick(MouseEvent e) {
		System.out.println("clicked at" + posToGrid(e.getX(), e.getY()));
		if (shouldHandle(e)) {
			cpp.pii grid = posToGrid(e.getX(), e.getY());
			int s = grid.first+grid.second*COL;
			System.out.println("selected" + s);
			GameManager.getInstance().setTowerChoice(grid.first+grid.second*COL);
		}
	}
	
	private static cpp.pii posToGrid(double x, double y){
		return new cpp.pff((x-LEFT)/Numbers.TILE_SIZE, (y-TOP)/Numbers.TILE_SIZE).toI();
	}
	
	
}
