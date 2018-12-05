package controller.game;

import java.util.ArrayList;

public class MonsterSpawningStage {
	
	private int delay;
	private ArrayList<MonsterSpawningSequence> seqs = new ArrayList<>();
	
	public MonsterSpawningStage(int delay, MonsterSpawningSequence ...seqs) {
		super();
		this.delay = delay;
		for (MonsterSpawningSequence mseq: seqs) {
			this.seqs.add(mseq);
			this.seqs.add(new MonsterSpawningSequence(delay));
		}
	}
	
	public void play() {
		for (int i=0; i<seqs.size(); i++) {
			MonsterSpawnerThread.addSequence(seqs.get(i));
		}
	}
	
}
