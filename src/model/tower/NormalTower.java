package model.tower;


import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.FadingParticle;
import model.Particle;
import model.Tower;
import model.projectile.NormalProjectile;
import model.projectile.SplittingProjectile;
import util.GameUtil;
import util.cpp;

public class NormalTower extends Tower {

	private static final Image DEFAULT_IMAGE = Images.normalTower;
		
	public NormalTower(double cellX, double cellY) {
		super("Default", DEFAULT_IMAGE, cellX, cellY);
	}
	
	public void fire() {
		if (currentTarget == null) return;
		Sounds.gunQuiet.play();
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		if (level < 5) {
		GameManager.getInstance().addProjectile(new 
				NormalProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, attack));
				Particle p = new FadingParticle(Images.normalTowerFlash, x+v.first*0.6 , y+v.second*0.6, 0, 0, 300);
				p.rotateTo(currentTarget);
				GameManager.getInstance().addParticle(p);
		}
		else {
			GameManager.getInstance().addProjectile(new 
					SplittingProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, attack, 
							Math.min(distanceTo(currentTarget)*0.6, distanceTo(currentTarget)-currentTarget.getSize()-size)));			
		}		
		currentCooldown = attackCooldown;
	}

}

