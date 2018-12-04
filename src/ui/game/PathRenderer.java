package ui.game;

import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import util.cpp;

public class PathRenderer {
	
	public static void render(cpp.pii[][] path, cpp.pii start, cpp.pii end, GraphicsContext gc) {
		if (path != null) {
			cpp.pii pos = new cpp.pii(start.first, start.second);
			while (pos != null && !pos.equals(end)) {
				gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				pos = path[pos.first][pos.second];
			}
			if (pos != null) {
				gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);					
			}
		}	
	}
	
	
}
