package Test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ActiveObjects.Airplane;
import AirportObjects.Airport;

public class AirportMap extends Canvas{
	public AirportMap() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for(String cd:Traffic.allAirports.keySet()) {
					Airport a  = Traffic.allAirports.get(cd);
					int startX, startY;
					startX = a.getX()+90;
					startY = a.getY()+90;
					if(startX<=x && x<=startX+40 && startY<=y && y<=startY+40 && a.getVisible()==true) {
						if(Traffic.ba.getAirport()==a)	{
							Traffic.ba.setAirport(null);
							Traffic.instance.t.unpause();
						}
						else {
							Traffic.ba.setAirport(a);
							Traffic.instance.t.pause();
						}
						}
					}
				}
		});
	}
	public void paint(Graphics g) {
		for(String cd:Traffic.allAirports.keySet()) {
			Airport a  = Traffic.allAirports.get(cd);
			if(Traffic.ba.getAirport()==a) g.setColor(Traffic.ba.getColor());
			else
				g.setColor(Color.GRAY);
			if(a.getVisible()==true) {
				g.fillRect(a.getX()+90, a.getY()+90, 20, 20);
				g.drawString(a.getCode(), a.getX()+90, a.getY()+80);
			}
		}
		for(Airplane a:Traffic.allPlanes) {
			if(!a.hasStarted())	continue;
			g.setColor(Color.BLUE);
			g.fillOval(a.getX()+90, a.getY()+90, 10, 10);
		}
	}
}
