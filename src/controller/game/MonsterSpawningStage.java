package controller.game;

import java.util.ArrayList;

public class MonsterSpawningStage {
	
	private int delay;
	private ArrayList<MonsterSpawningSequence> seqs = new ArrayList<>();
	
	public MonsterSpawningStage(int delay, ArrayList<MonsterSpawningSequence> seqs) {
		super();
		this.delay = delay;
		for (MonsterSpawningSequence mseq: seqs) {
			this.seqs.add(mseq);
		}
	}
	
	public MonsterSpawningStage(int delay, MonsterSpawningSequence ...seqs) {
		super();
		this.delay = delay;
		for (MonsterSpawningSequence mseq: seqs) {
			this.seqs.add(mseq);
		}
	}
	
	public ArrayList<MonsterSpawningSequence> getSequence() {
		return this.seqs;
	}
	
	
	public void play() {
		for (int i=seqs.size()-1; i>=0; i--) {
			MonsterSpawnerThread.addSequence(seqs.get(i));
		}
	}
	
}
