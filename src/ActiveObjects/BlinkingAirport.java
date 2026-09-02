package ActiveObjects;
import java.awt.Color;
import java.awt.Frame;

import AirportObjects.Airport;

public class BlinkingAirport extends Thread{
	private Airport currentAirport;
	private static Color redColor = Color.RED;
	private static Color grayColor = Color.GRAY;
	private Color currentColor;
	private Frame frame;
	
	public  synchronized void setAirport(Airport a) {
		this.currentAirport = a;
		if(a!=null)	notify();
	}
	
	public synchronized Airport getAirport() {
		return currentAirport;
	}
	
	public synchronized Color getColor() {
		return currentColor;
	}
	
	public BlinkingAirport(Frame f){
		currentAirport = null;
		currentColor = redColor;
		this.frame = f;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(this.getAirport()==null) {
						wait();
					}
				}
				if(currentColor==grayColor) {
					currentColor = redColor;
				}
				else currentColor = grayColor;
				Thread.sleep(500);
				frame.repaint();
			}
		}
		catch(InterruptedException e) {
			
		}
	}
}
