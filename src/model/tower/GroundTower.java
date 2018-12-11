package model.tower;


import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import model.Particle;
import model.Tower;
import model.particle.FadingParticle;
import model.projectile.GroundProjectile;
import util.GameUtil;
import util.cpp;

public class GroundTower extends Tower {

	public GroundTower(double cellX, double cellY) {
		super("Ground", cellX, cellY);
	}
		
	public void fire() {
		if (currentTarget == null) return;
		Sounds.gunLoud.play();
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				GroundProjectile(x, y, v.first*15, v.second*15, range, attack));
				Particle p = new FadingParticle(Images.normalTowerFlash, x+v.first*0.6 , y+v.second*0.6, 0, 0, 300);
				p.setRotation(rotation);
				GameManager.getInstance().addParticle(p);
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Ground Defense";
	}
}

