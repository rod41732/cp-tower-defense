package buff;

import model.Entity;
import model.Monster;

public class MoveSpeedBuff  extends Buff {

	private double mutliplier;
	
	public MoveSpeedBuff(double duration, double multiplier) {
		super("MoveSpeed ", "Increase movespeed", duration);
		this.mutliplier = multiplier;
		this.age = 0;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Monster) {
			((Monster) e).addMoveSpeedMultiplier(mutliplier);
		}
	}
}
