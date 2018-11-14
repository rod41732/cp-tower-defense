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

public class BombTower extends Tower {

	// TODO : more fields
	
	
	protected double attack;

	
	public BombTower(Image img, double cellX, double cellY, double attack, double cooldown, double range) {
		super(img, cellX, cellY, cooldown, range);
		this.attack = attack;
		this.attackCooldown = cooldown;
		this.range = range;
	}
	
	public void upgrade() {
		range += 0.5;
	}
	
	public void fire() {
		System.out.println("target = " + target + "cooldown " + cooldown);
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
		return String.format("Bomb Tower R=%.1f A=%.1f C=%.1f", range, attack, cooldown);
	}
	
}
