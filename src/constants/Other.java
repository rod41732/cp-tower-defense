package constants;

import buff.Buff;
import buff.DamageTakenDebuff;
import buff.MoveSpeedBuff;
import javafx.scene.text.Font;

public class Other {
	public static int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	public static Font normalButtonFont = new Font("KenVector Future Regular", 20);
	public static Font thinButtonFont = new Font("KenVector Future Regular", 20);
	
	public static Buff moveSpeedBuffInstance = new MoveSpeedBuff(1, 1);
	public static Buff damageTakenDebuffInsance = new DamageTakenDebuff(1, 1);
	static {

	}
}
