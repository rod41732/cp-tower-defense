package ui.game;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import util.GameUtil;
import util.cpp;

public class PathRenderer {
	
	private static final Bloom bloom = new Bloom(0.1);
	
	public static void render(cpp.pii[][] path, cpp.pii start, cpp.pii end, GraphicsContext gc) {
		cpp.pii prev = null, next = null; // the function does handle null correctly
		cpp.pii pos = new cpp.pii(start.first, start.second);
		if (path[pos.first][pos.second] == null) return;
		gc.save();
		gc.setEffect(bloom);
		gc.setGlobalAlpha(0.5+0.3*GameUtil.transparencyCycle(Renderer.getInstance().getRenderTick(), 120));
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
		gc.restore();
	}
	
	
}
