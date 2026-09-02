package AirportObjects;

import Errors.*;

public class Airport {
	
	private String airportName;
	private String airportCode;
	
	private int xCord, yCord;
	private int lastDepartureTime;
	private boolean visible;
	
	public Airport(String name, String code, int x, int y)  throws Exception{
		if(code.length()!=3)	throw new InvalidCode(code);
		this.airportCode = code;
		this.airportName = name;
		if(x<-90 || x>90)	throw new InvalidX();
		if(y<-90 || y>90)	throw new InvalidY();
		this.xCord = x;
		this.yCord = y;
		visible = false;
		lastDepartureTime = -900;
	}
	
	public int getDepartureTime() {
		return lastDepartureTime;
	}
	
	public void setDepartureTime(int t) {
		this.lastDepartureTime = t;
	}
	
	public String getName() {
		return airportName;
	}
	
	public String getCode() {
		return airportCode;
	}
	
	public void setVisible(boolean value) {
		this.visible = value;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public int getX() {
		return xCord;
	}
	
	public int getY() {
		return yCord;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(airportName).append(", ").append(airportCode).
		append(", (").append(xCord).append(", ").append(yCord).append(")\n");
		return sb.toString();
	}
	
}
