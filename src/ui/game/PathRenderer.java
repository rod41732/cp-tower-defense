package ui.game;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import util.GameUtil;
import util.cpp;

public class PathRenderer {
	
	public static void render(cpp.pii[][] path, cpp.pii start, cpp.pii end, GraphicsContext gc) {
		cpp.pii prev = null, next = null; // the function does handle null correctly
		cpp.pii pos = new cpp.pii(start.first, start.second);
		while (pos != null && !pos.equals(end)) {
			next = path[pos.first][pos.second];
			int d1 = GameUtil.directionIndex(prev, pos);
			int d2 = GameUtil.directionIndex(next, pos);
			if (d1 == -1) d1 = (d2+2)%4;
			if (d2 == -1) d2 = (d1+2)%4;
			gc.drawImage(Images.arrows[d1][d2], pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE);
			prev = pos;
			pos = next;
			
		}
		if (pos != null) {
			int d1 = GameUtil.directionIndex(prev, pos);
			int d2 = GameUtil.directionIndex(next, pos);
			if (d1 == -1) d1 = (d2+2)%4;
			if (d2 == -1) d2 = (d1+2)%4;
			gc.drawImage(Images.arrows[d1][d2], pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE);
			prev = pos;
			pos = next;			
		}
	}
	
	
}
