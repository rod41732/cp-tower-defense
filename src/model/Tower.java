package model;


import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Entity;
import model.monster.GroundMonster;
import model.projectile.Bomb;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.Render;
import util.cpp;

public class Tower extends Tile {

	// TODO : more fields
	
	
	protected double rotation = 0;
	protected double attack;
	protected double attackCooldown = 1000;
	protected double cooldown; // in ms
	protected double range; 
	
	protected Monster target;
	private double minDist;
	
	public Tower(Image img, double cellX, double cellY,
			double cooldown, double range) {
		super(img, cellX, cellY);
		this.cooldown = cooldown;
		this.range = range;
	}
	
	public void clearTarget() {
		target = null;
		minDist = 0;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		Render.drawRotatedImage(gc, image, rotation-180, getRenderX(), getRenderY());
		if (this.getPosition().containedBy(GameManager.getInstance().getSelectedTile())) {
			double tz = Numbers.TILE_SIZE;
			double t = GameManager.getInstance().getRenderTickCount()%120;
			double multiplier = t/60.;
			if (multiplier > 1) multiplier = 2-multiplier;
			multiplier = 0.3+0.7*multiplier;
			gc.setLineWidth(3);
			gc.setStroke(new Color(1, 0, 1, 0.8));
			gc.strokeOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);			
			gc.setFill(new Color(1, 0, 1, 0.4*multiplier));
			gc.fillOval(x*tz-range*tz, y*tz-range*tz, 2*range*tz, 2*range*tz);
		}	
	}
	
	public boolean isInRange(Monster m) {
		System.out.println(m + "has dist" + distanceTo(m) + "range =" + range);
		return Double.compare(distanceTo(m), range) < 0;
	}
	// change target to monster m if it's closer than current monster
	public void tryTarget(Monster m) {
		// TODO
		if ((target == null || Double.compare(distanceTo(m), minDist) < 0) && isInRange(m)){
			if (m.isDead()) return ;
			rotateTo(m);
			target = m;
			System.out.println("now target" + target);
			minDist = distanceTo(m);
		}
	}
	
	public void rotateTo(Monster m) {
		double angle = Math.toDegrees(Math.atan2(m.getY()-y, m.getX()-x));
		rotation = angle-90;
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		System.out.println("Cooldown " + cooldown);
		if (target == null) return;
		if (cooldown > 0) {
			cooldown -= 16; // 1 tick = 16 ms
			return ;
		}
		
		cpp.pff v = GameUtil.unitVector(this, target);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), target.getX(), target.getY(), v);
		

		GameManager.getInstance().getBullets().add(new 
				Bomb(Images.bullet1,x, y, v.first*9, v.second*9, range, 1, 2));
		
		cooldown = attackCooldown;
		clearTarget();
	}
	
	public String toString() {
		return String.format("Tower R=%.1f A=%.1f C=%.1f", range, attack, cooldown);
	}
	
}
