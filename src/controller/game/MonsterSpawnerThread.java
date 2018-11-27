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
						MonsterSpawningSequence top = jobs.peek();
						if (top == null) continue;
						System.out.println("[Monster Thread] running job");
						top.start();
						currentJob = top;
						top.join();
						currentJob = null;
						jobs.remove(); // only remove jobs when done
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
		return jobs.isEmpty();
	}
	
	public static void cancelAll() {
		currentJob.interrupt();
		jobs.clear();
	}
}

