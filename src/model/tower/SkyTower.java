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
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		Particle p = new FadingParticle(Images.normalTowerFlash, x+v.first*0.6 , y+v.second*0.6, 0, 0, 300);
		GameManager.getInstance().addParticle(p);
		if (level < 5) {
		GameManager.getInstance().addProjectile(new 
				AirProjectile(x, y, v.first*15, v.second*15, range, attack));
				p.rotateTo(currentTarget);
		}
		else {
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

