package AirportObjects;

import Errors.*;

public class Flight {
	
	private Airport airportFrom;
	private Airport airportTo;
	private int departureTimeHour;
	private int departureTimeMinute;
	private int flightDuration;
	
	public Flight(Airport from, Airport to, int hour, int minute, int duration) {
		this.airportFrom = from;
		this.airportTo = to;
		this.departureTimeHour = hour;
		this.departureTimeMinute = minute;
		this.flightDuration = duration;
	}
	
	public Airport getFrom() {
		return airportFrom;
	}
	
	public Airport getTo() {
		return airportTo;
	}
	
	public int getDepartureHours() {
		return departureTimeHour;
	}
	
	public int getDepartureMinutes() {
		return departureTimeMinute;
	}
	
	public int getDuration() {
		return flightDuration;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(airportFrom).append(" -> ").append(airportTo).append(", ").append(departureTimeHour).
		append(":").append(departureTimeMinute).append(", ").append(flightDuration).append("\n");
		return sb.toString();
	}
}
