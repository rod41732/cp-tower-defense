package controller.game;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MonsterSpawnerThread extends Thread {
	
	private static MonsterSpawnerThread instance = new MonsterSpawnerThread();	
	private static ConcurrentLinkedQueue<MonsterSpawningSequence> jobs = new ConcurrentLinkedQueue<>();
	private static MonsterSpawningSequence currentJob;
	public MonsterSpawnerThread() {
		super(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(16);
						MonsterSpawningSequence top = jobs.poll();
						if (top == null) continue;
						System.out.println("[Monster Thread] running job");
						top.start();
						currentJob = top;
						top.join();
						currentJob = null;
					}
					catch (InterruptedException e) {
						System.out.println("monster spawner interrupted");
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
	
	public static MonsterSpawnerThread getInstance() {
		return instance;
	}
	
	public static boolean isIdle() {
		return jobs.isEmpty() && currentJob == null;
	}
		
	public static void cancelAll() { // cancel all = cancel whole wave
		if (currentJob != null) {
			currentJob.interrupt();			
		}
		jobs.clear();
	}
}

