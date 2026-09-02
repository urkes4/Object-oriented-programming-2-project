package ActiveObjects;

public class SimulationClock extends Thread{

	private int time;
	private static SimulationClock inst = null;
	private boolean paused;
	
	private SimulationClock() {
		time = 0;
		paused = true;
	}
	
	public static SimulationClock getInstance() {
		if(inst ==null)	inst = new SimulationClock();
		return inst;
	}
	
	public synchronized int getSimulationTime() {
		return time;
	}
	
	public synchronized void setPaused(boolean val) {
		paused = val;
		if(!paused) {
			notifyAll();
		}
	}
	
	public synchronized void reset() {
		time = 0;
	}
	
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
                    while (paused) {
                        wait(); 
                    }
                }
				Thread.sleep(1000);
				time+=10;
			}
		}
		catch(InterruptedException e) {
			
		}
	}
}
