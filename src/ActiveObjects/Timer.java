package ActiveObjects;

import Test.*;

public class Timer extends Thread{
	private static int TIME_END = 10;
	private boolean isPaused;
	private int currentTime;
	public Timer() {
		currentTime = 0;
		isPaused = false;
	}
	
	public int getRemainingTime() {
		return TIME_END - this.currentTime;
	}
	
	public void resetTimer() {
		currentTime = 0;
	}
	
	public void pause() {
		isPaused = true;
	}
	
	public synchronized void unpause() {
		isPaused = false;
		notify();
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void run() {
		RemainingTime rt = null;
		try {
		while(!Thread.interrupted()) {
			synchronized(this) {
				while(isPaused()) {
					wait();
				}
			}
			Thread.sleep(1000);
			currentTime++;
			
			if(getRemainingTime()==5) {
				rt = new RemainingTime(Traffic.instance,this);
			}
			if(getRemainingTime()<=5) {
				rt.repaint();
			}
			if(currentTime==TIME_END)	System.exit(0);
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
