package model;


import constants.Images;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import model.Entity;
import model.monster.GroundMonster;
import model.projectile.Bomb;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.Render;
import util.cpp;

public class Tower extends Entity {

	// TODO : more fields
	
	
	protected double rotation = 0;
	protected double attack;
	protected double attackCooldown = 1000;
	protected double cooldown; // in ms
	protected double range; 
	
	protected Monster target;
	private double minDist;
	
	public Tower(Image img, double cellX, double cellY, double attack, double cooldown, double range) {
		super(img, cellX, cellY, 0.5);
		this.attack = attack;
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
	}
	
	public boolean isInRange(Monster m) {
		return Double.compare(this.distanceTo(m), range) < 0;
	}
	// change target to monster m if it's closer than current monster
	public void tryTarget(Monster m) {
		// TODO
		clearTarget();
		if ((target == null || Double.compare(distanceTo(m), minDist) < 0) && isInRange(m)){
			if (m.isDead()) return ;
			rotateTo(m);
			target = m;
			minDist = distanceTo(m);
		}
	}
	
	public void rotateTo(Monster m) {
		double angle = Math.toDegrees(Math.atan2(m.getY()-y, m.getX()-x));
		rotation = angle-90;
	}
	
	public void fire() {
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
}
