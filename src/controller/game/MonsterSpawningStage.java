package controller.game;

import java.util.ArrayList;

public class MonsterSpawningStage {
	
	private ArrayList<MonsterSpawningSequence> seqs = new ArrayList<>();
	
	public MonsterSpawningStage(int delay, MonsterSpawningSequence ...seqs) {
		super();
		for (MonsterSpawningSequence mseq: seqs) {
			this.seqs.add(mseq);
			this.seqs.add(new MonsterSpawningSequence(delay));
		}
	}
	
	public void play() {
		MonsterSpawnerThread.addSequence(new MonsterSpawningSequence(0));
		for (int i=0; i<seqs.size(); i++) {
			MonsterSpawnerThread.addSequence(seqs.get(i));
		}
	}
	
	public void addSequence(MonsterSpawningSequence ...sequences) {
		for (MonsterSpawningSequence ms: sequences) {
			seqs.add(ms);
		}
	}
	
}
