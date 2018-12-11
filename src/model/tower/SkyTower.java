package model.tower;


import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import model.Particle;
import model.Tower;
import model.particle.FadingParticle;
import model.projectile.AirProjectile;
import model.projectile.SplittingAirProjectile;
import util.GameUtil;
import util.cpp;

public class SkyTower extends Tower {
		
	public SkyTower(double cellX, double cellY) {
		super("Air", cellX, cellY);
	}
	
	
	public void fire() {
		if (currentTarget == null) return;
		Sounds.missileLaunch.play();
		rotateTo(currentTarget);
		cpp.pff v = GameUtil.unitVector(this, currentTarget);		
		if (level < 5) {
			// tower "flares"
			cpp.pff v1 = new cpp.pff(v.first*0.6, v.second*0.6);
			cpp.pff v2 = GameUtil.rotateVector(v1.first, v1.second, 30);
			cpp.pff v3 = GameUtil.rotateVector(v1.first, v1.second, -30);
			Particle p = new FadingParticle(Images.normalTowerFlash, x+v1.first , y+v1.second, 0, 0, 300);
			p.setRotation(rotation);
			GameManager.getInstance().addParticle(p);
			Particle p2 = new FadingParticle(Images.normalTowerFlash, x+v2.first , y+v2.second, 0, 0, 300);
			p2.setRotation(rotation);
			GameManager.getInstance().addParticle(p2);
			Particle p3 = new FadingParticle(Images.normalTowerFlash, x+v3.first , y+v3.second, 0, 0, 300);
			p3.setRotation(rotation);
			GameManager.getInstance().addParticle(p3);
			
			
			GameManager.getInstance().addProjectile(new 
					AirProjectile(x, y, v.first*15, v.second*15, range, attack));
		}
		else {
			// tower "flares"
			cpp.pff v0 = new cpp.pff(v.first*0.6, v.second*0.6);
			cpp.pff v1 = GameUtil.rotateVector(v0.first, v0.second, -8);
			cpp.pff v2 = GameUtil.rotateVector(v0.first, v0.second, 8);
			cpp.pff v3 = GameUtil.rotateVector(v0.first, v0.second, -30);
			cpp.pff v4 = GameUtil.rotateVector(v0.first, v0.second, 30);
			Particle p = new FadingParticle(Images.normalTowerFlash, x+v1.first , y+v1.second, 0, 0, 300);
			p.setRotation(rotation);
			GameManager.getInstance().addParticle(p);
			Particle p2 = new FadingParticle(Images.normalTowerFlash, x+v2.first , y+v2.second, 0, 0, 300);
			p2.setRotation(rotation);
			GameManager.getInstance().addParticle(p2);
			Particle p3 = new FadingParticle(Images.normalTowerFlash, x+v3.first , y+v3.second, 0, 0, 300);
			p3.setRotation(rotation);
			GameManager.getInstance().addParticle(p3);
			Particle p4 = new FadingParticle(Images.normalTowerFlash, x+v4.first , y+v4.second, 0, 0, 300);
			p4.setRotation(rotation);
			GameManager.getInstance().addParticle(p4);
			
			GameManager.getInstance().addProjectile(new 
					SplittingAirProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, attack, 
							Math.min(distanceTo(currentTarget)*0.6, distanceTo(currentTarget)-currentTarget.getSize()-size)));			
		}		
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Sky Turret";
	}
}

