package ActiveObjects;
import java.awt.Frame;
import AirportObjects.*;

public class Airplane extends Thread {
    
    private Airport from, to;
    private int startX, startY;
    private int endX, endY;
    private double curX, curY;      
    private int departureTime, duration;
    private double dx, dy;         
    private Frame map;
    private static boolean paused;
    private boolean started;
    private int flightTime;
    private int steps;
    private int currentSteps;

    public Airplane(Flight f, Frame fr) {
        this.from = f.getFrom();
        this.to = f.getTo();
        this.departureTime = f.getDepartureHours()*60 + f.getDepartureMinutes();
        startX = from.getX();
        startY = from.getY();
        endX = to.getX();
        endY = to.getY();
        curX = startX;
        curY = startY;
        duration = f.getDuration();

        steps = (duration * 5) / 10; 
        dx = (double)(endX - startX) / steps;
        dy = (double)(endY - startY) / steps;

        map = fr;
        paused = false;
        started = false;
        flightTime = 0;
        currentSteps = 0;
    }
    
    public void resetAirplane() {
    	curX = startX;
    	curY = startY;
    	started = false;
    	currentSteps = 0;
    	paused = true;
    	map.repaint();
    	flightTime = 0;
    }
    
    public static synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void setPaused(boolean val) {
        paused = val;
        if(!paused) {
        	notifyAll();
        }
    }

    public int getX() {
        return (int)curX; 
    }

    public int getY() {
        return (int)curY;
    }

    public boolean hasStarted() {
        return started;
    }

    
    public Airport getFrom() {
    	return from;
    }
    
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
            	synchronized(this) {
            		while(isPaused()) {
            			wait();
            		}
            	}
                if(currentSteps >= steps) {
                    curX = endX;
                    curY = endY;
                    started = false;
                    map.repaint();
                    break;
                }

                while(flightTime == 0 && departureTime > SimulationClock.getInstance().getSimulationTime()) {
                    Thread.sleep(50); 
                }

                synchronized(from) {
                    if(flightTime == 0 && SimulationClock.getInstance().getSimulationTime() - from.getDepartureTime() < 10)
                        from.wait();
                }

                from.setDepartureTime(SimulationClock.getInstance().getSimulationTime());
                started = true;
                flightTime++;

                synchronized(from) {
                    if(flightTime == 5)
                        from.notify();
                }
                curX += dx;
                curY += dy;
                currentSteps++;

                Thread.sleep(200);
                map.repaint();
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
