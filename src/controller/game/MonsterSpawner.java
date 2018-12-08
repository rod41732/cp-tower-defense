package controller.game;

import java.util.ArrayList;

import model.monster.ArmoredCar;
import model.monster.ArmoredPlane;
import model.monster.ArmoredSoldier;
import model.monster.ArmoredTank;
import model.monster.FastCar;
import model.monster.FastPlane;
import model.monster.FastSoldier;
import model.monster.FastTank;
import model.monster.HeavyCar;
import model.monster.HeavyPlane;
import model.monster.HeavySoldier;
import model.monster.HeavyTank;
import model.monster.NormalCar;
import model.monster.NormalPlane;
import model.monster.NormalSoldier;
import model.monster.NormalTank;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private static ArrayList<MonsterSpawningStage> stages = new ArrayList<>(); 
	private int index= 0;
	static {
		for (int i=1; i<20; i++) {
			MonsterSpawningStage stage = new MonsterSpawningStage(2000000, // delay between each "part" of wave
					// spawns two monster, with 100ms nanosec delay, i+1 times, with 500ms nanosec delay
					// so it will e like this xxx--yyy--------xxx--yyy---------xxx--yyy------ and so on
					// useful when spawning mixed multiple monsters 
//					new MonsterSpawningSequence(500000, 100000, i, 
//							new NormalCar(0, 0, 0), 
//							new HeavyCar(0, 0, 3),
//							new FastCar(0, 0, 0),
//							new ArmoredCar(0, 0, 0)
//							),
					// like above but with differnt set of monster
//					new MonsterSpawningSequence(500000, 100000, i, // part 2 of wave
//							new NormalTank(0, 0, 0), 
//							new HeavyTank(0, 0, 3),
//							new FastTank(0, 0, 0),
//							new ArmoredTank(0, 0, 0)
//							),
//					new MonsterSpawningSequence(500000, 0, i, 
//							new NormalSoldier(0, 0, 0), 
//							new HeavySoldier(0, 0, 3),
//							new FastSoldier(0, 0, 0),
//							new ArmoredSoldier(0, 0, 0)
//							),
					new MonsterSpawningSequence(500000, 0, i, 
							new NormalPlane(0, 0, 0), 
							new HeavyPlane(0, 0, 3),
							new FastPlane(0, 0, 0),
							new ArmoredPlane(0, 0, 0)
							)
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

