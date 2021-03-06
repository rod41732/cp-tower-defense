package model.monster;

import constants.Images;
import constants.Numbers;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import model.particle.Explosion;
import util.GameUtil;
import util.cpp;

public abstract class Plane extends FlyingMonster {
	private static final double DEFAULT_SIZE = 0.5;
	protected double level = 0;
	
	
	public Plane(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Plane", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		Sounds.planeExplosion.play();
		GameManager.getInstance().addParticle(new Explosion(Images.deathExplosion, x, y, 0, 0));
	}	
	
	private static double x0 = 13, y0 = 5, scale = 1.07;
	
	
	@Override
	public void render(GraphicsContext gc) {
		PerspectiveTransform per = new PerspectiveTransform();
		
		cpp.pff center = new cpp.pff((x0+(x+w/Numbers.TILE_SIZE/2-x0)*scale)*Numbers.TILE_SIZE, (y0+(y+h/Numbers.TILE_SIZE/2-y0)*scale)*Numbers.TILE_SIZE);
		cpp.pff now = new cpp.pff(0, 0);

		now.first = (x0+(x-x0)*scale)*Numbers.TILE_SIZE;
		now.second = (y0+(y-y0)*scale)*Numbers.TILE_SIZE;
		now = GameUtil.rotatePoint(center, now, rotation);
		per.setUlx(now.first);
		per.setUly(now.second);
		

		now.first = (x0+(x+w/Numbers.TILE_SIZE-x0)*scale)*Numbers.TILE_SIZE;
		now.second = (y0+(y-y0)*scale)*Numbers.TILE_SIZE;
		now = GameUtil.rotatePoint(center, now, rotation);
		per.setUrx(now.first);
		per.setUry(now.second);
		
		now.first = (x0+(x-x0)*scale)*Numbers.TILE_SIZE;
		now.second = (y0+(y+h/Numbers.TILE_SIZE-y0)*scale)*Numbers.TILE_SIZE;
		now = GameUtil.rotatePoint(center, now, rotation);
		per.setLlx(now.first);
		per.setLly(now.second);
		
		now.first = (x0+(x+w/Numbers.TILE_SIZE-x0)*scale)*Numbers.TILE_SIZE;
		now.second = (y0+(y+h/Numbers.TILE_SIZE-y0)*scale)*Numbers.TILE_SIZE;
		now = GameUtil.rotatePoint(center, now, rotation);
		per.setLrx(now.first);
		per.setLry(now.second);
		

		gc.setEffect(per);
		gc.drawImage(Images.planeShadow, getRenderX(), getRenderY());
//		gc.setFill(Color.RED);
//		gc.fillRect(0, 0, w, h);
		gc.setEffect(null);
		super.render(gc);
	}
	
}
