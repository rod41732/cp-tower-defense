package controller.game;

import java.util.ArrayList;

public class MonsterSpawningStage {
	
	private int delay;
	private ArrayList<MonsterSpawningSequence> seqs;
	
	public MonsterSpawningStage(int delay, ArrayList<MonsterSpawningSequence> seqs) {
		super();
		this.delay = delay;
		this.seqs = seqs;
	}
	
	public void play() {
		
	}
	
}
