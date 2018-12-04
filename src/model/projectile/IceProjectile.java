package model.projectile;

import buff.MoveSpeedBuff;
import constants.Images;
import controller.game.GameManager;
import model.Monster;
import model.Particle;
import util.cpp;

public class IceProjectile extends NormalProjectile {
	
	private double slowness;
	private double splashRadius;
	
	
	protected boolean isExpired = false;
	
	public IceProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double slowness, double splashRadius) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, damage); // default size ?
		this.slowness = slowness;
		this.splashRadius = splashRadius;
	}
	@Override
	public boolean shouldCollide(Monster m) {
		return !m.hasBuff(new MoveSpeedBuff(1, 1)) && super.shouldCollide(m);
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (ms.distanceTo(impact.first, impact.second) <= splashRadius+ms.getSize()) {
					ms.addBuff(new MoveSpeedBuff(2000, -slowness));
				}
			}
			forceExpire();
		}
		return isExpired();
	}
		
}
