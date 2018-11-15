package model.tower;


import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Entity;
import model.Tower;
import model.monster.GroundMonster;
import model.projectile.Bomb;
import model.projectile.FireProjectile;
import model.projectile.NormalProjectile;
import util.GameUtil;
import util.Render;
import util.cpp;

public class FireTower extends Tower {

	// TODO : more fields
	private static final double RADIUS = 0.7;

	public FireTower(Image image ,double cellX, double cellY, double attack, double cooldown, double range) {
		super(image, cellX, cellY, attack, cooldown, range);
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		if (cooldown > 0) {
			cooldown -= 16; // 1 tick = 16 ms
			return ;
		}
		if (target == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, target);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), target.getX(), target.getY(), v);
		

		GameManager.getInstance().getBullets().add(new 
				FireProjectile(Images.bullet3, x, y, v.first*9, v.second*9, range, attack, RADIUS));
		
		cooldown = attackCooldown;
		clearTarget();
	}
	
	public String toString() {
		return "FireTower";
	}
	
	@Override 
	public String description() {
		return super.description() + String.format("Fire radius: %.2f\n", RADIUS);
	}
	
}
