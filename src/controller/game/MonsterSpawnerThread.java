package controller.game;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MonsterSpawnerThread extends Thread {
	
	private static MonsterSpawnerThread instance = new MonsterSpawnerThread();	
	public static ConcurrentLinkedQueue<MonsterSpawningSequence> jobs = new ConcurrentLinkedQueue<>();
	private static MonsterSpawningSequence currentJob;
	private static boolean shouldBreak = false;
	public MonsterSpawnerThread() {
		super(new Runnable() {
			
			@Override
			public void run() {
				while (!shouldBreak) {
					try {
						Thread.sleep(16);
						MonsterSpawningSequence top = jobs.peek();
						if (top == null) continue;
						System.out.println("[Monster Thread] running job");
						top.run();							
						currentJob = top;
						top.join();
						jobs.poll();
						currentJob = null;
					}
					catch (InterruptedException e) {
						System.out.println("monster spawner interrupted");
						break;
					}
				}
				
			}
		});
		start();
	}
	
	public static void addSequence(MonsterSpawningSequence ...seq){
		for (MonsterSpawningSequence s: seq) {
			jobs.add(s);
		}
	}
	
	public static void onGamePause() {
		MonsterSpawningSequence.onGamePause();
	}
	
	public static void onGameResume() {
		MonsterSpawningSequence.onGameResume();
	}
	
	
	public static MonsterSpawnerThread getInstance() {
		return instance;
	}
	
	public static boolean isIdle() {
		return jobs.isEmpty() && currentJob == null;
	}
		
	@Override
	public void interrupt() {
		shouldBreak = true;
		super.interrupt();
	}
	
	public void onGameReset() { 
		MonsterSpawningSequence.onGameReset();
		jobs.clear();
	}
}

