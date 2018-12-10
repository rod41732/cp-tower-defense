package controller.game;

import java.util.ArrayList;

import controller.SuperManager;
import model.monster.CarArmored;
import model.monster.CarBoss;
import model.monster.PlaneArmored;
import model.monster.SoldierArmored;
import model.monster.SoldierBoss;
import model.monster.TankArmored;
import model.monster.TankBoss;
import model.monster.CarFast;
import model.monster.PlaneFast;
import model.monster.SoldierFast;
import model.monster.TankFast;
import model.monster.CarHeavy;
import model.monster.PlaneHeavy;
import model.monster.SoldierHeavy;
import model.monster.TankHeavy;
import model.monster.CarNormal;
import model.monster.PlaneNormal;
import model.monster.SoldierNormal;
import model.monster.TankNormal;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private static ArrayList<MonsterSpawningStage> stages = new ArrayList<>(); 
	private int index= 0;
	static {
		for (int i=1; i<=20; i++) {
			MonsterSpawningStage stage = new MonsterSpawningStage(500000, // delay between each "part" of wave
					// spawns two monster, with 100ms nanosec delay, i+1 times, with 500ms nanosec delay
					// so it will e like this xxx--yyy--------xxx--yyy---------xxx--yyy------ and so on
					// useful when spawning mixed multiple monsters 
					new MonsterSpawningSequence(200000, 100000, i, 
//							new CarBoss(0, 0, 0), 
//							new TankBoss(0, 0, 0),
							new SoldierBoss(0, 0, 0)
//							new CarArmored(0, 0, 0)
							)
//					 like above but with differnt set of monster
//					new MonsterSpawningSequence(500000, 100000, i, // part 2 of wave
//							new TankNormal(0, 0, 0), 
//							new TankHeavy(0, 0, 3),
//							new TankFast(0, 0, 0),
//							new TankArmored(0, 0, 0)
//							),
//					new MonsterSpawningSequence(500000, 0, i, 
//							new SoldierNormal(0, 0, 0), 
//							new SoldierHeavy(0, 0, 3),
//							new SoldierFast(0, 0, 0),
//							new SoldierArmored(0, 0, 0)
//							),
//					new MonsterSpawningSequence(500000, 0, i, 
//							new PlaneNormal(0, 0, 0), 
//							new PlaneHeavy(0, 0, 3),
//							new PlaneFast(0, 0, 0),
//							new PlaneArmored(0, 0, 0)
//							)
					); // .. there can be more
			
			stages.add(stage);
		}
	}
	

	
	public MonsterSpawner() {	
//		pii tile = gm.getStartTilePos();	
	}
	
	
	
	
	
	public void nextWave() {
		if (!isReady()) {
			System.out.println("not ready yet");
			return;
		}
		System.out.println("stage" + index);
		stages.get(index).play();
		index++;
		if (index >= stages.size()) {
			SuperManager.getInstance().getGameStateProp().set(2);
		}
	}
	
	public void pauseWave() {
		MonsterSpawnerThread.onGamePause();
	}
	
	public void cancelWave() {
		MonsterSpawnerThread.getInstance().onGameReset();
	}
	
	public void resumeWave() {
		MonsterSpawnerThread.onGameResume();	
	}
	
	public void reset() {
		cancelWave();
		index = 0;
	}
	
	
	public int getLevel() {
		return index;
	}
	
	
	public boolean isReady() {
		return MonsterSpawnerThread.isIdle();
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}

