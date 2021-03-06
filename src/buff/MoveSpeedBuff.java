package buff;

import model.Entity;
import model.Monster;

public class MoveSpeedBuff  extends Buff {

	
	public static final int ID = 2;
	
	private double mutliplier;
	public MoveSpeedBuff(double duration, double multiplier) {
		super(ID, duration);
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
