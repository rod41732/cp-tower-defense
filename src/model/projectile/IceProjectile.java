package model.projectile;

import buff.SlowDebuff;
import constants.Images;
import controller.game.GameManager;
import model.Monster;
import util.cpp;

public class IceProjectile extends NormalProjectile {
	
	private double slowness;
	private double splashRadius;
	private double duration;
		
	public IceProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double slowness, double splashRadius, double duration) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, damage); // default size ?
		this.slowness = slowness;
		this.splashRadius = splashRadius;
		this.duration = duration;
		this.targetFlag = 3;
	}
	@Override
	public boolean shouldCollide(Monster m) {
		return !m.hasBuff(SlowDebuff.ID) && super.shouldCollide(m);
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (ms.distanceTo(impact.first, impact.second) <= splashRadius+ms.getSize()) {
					ms.addBuff(new SlowDebuff(duration, slowness));
				}
			}
			forceExpire();
		}
		return isExpired();
	}
		
}
