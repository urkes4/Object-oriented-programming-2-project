package Test;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ActiveObjects.*;
import AirportObjects.*;
import Errors.*;
import InputOutput.*;

public class Traffic extends Frame {
	
	static public Traffic instance;
	
	static HashMap<String,Airport> allAirports = new HashMap<String, Airport>();
	static ArrayList<Flight> allFlights = new ArrayList<Flight>();
	static ArrayList<Airplane> allPlanes = new ArrayList<Airplane>();
	Timer t;
	static BlinkingAirport ba;
	static AirportMap map;

	//Panels
	private Panel userInput;
	private Panel dataView;
	private Panel checkBoxAirports;
	private Panel simulation;
	
	//IO members
	private Reader reader;
	private Writer writer;
	
	//TextFields
	private TextField airportName;
	private TextField airportCode;
	private TextField airportX;
	private TextField airportY;
	
	private TextField flightStart;
	private TextField flightEnd;
	private TextField flightTime;
	private TextField flightDuration;
	
	private TextField airportInputFile;
	private TextField airportOutputFile;
	
	private TextField flightInputFile;
	private TextField flightOutputFile;
	
	//Buttons
	private Button submitAirport;
	private Button submitFlight;
	private Button writeAirports;
	private Button readAirports;
	private Button writeFlights;
	private Button readFlights;
	private Button startSimulation;
	private Button pauseSimulation;
	private Button resumeSimulation;
	private Button restartSimulation;
	
	//TextAreas
	
	private TextArea airportView;
	private TextArea flightView;
	
	public Traffic() {
		map = new AirportMap();
		add(map,BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(1440,720);
		this.setTitle("Traffic simulation");
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
		
		addUserInput();
		addDataView();
		addSimulation();
		addActions();
		validate();
		t = new Timer();
		ba = new BlinkingAirport(this);
		ba.start();
		SimulationClock.getInstance().start();
		//t.start();
	}
	
	
	public void addAirport(Airport a) {
			allAirports.put(a.getCode(),a);
			airportView.append(a.toString());
			Label lb = new Label(a.getCode());
			Checkbox cb = new Checkbox();
			checkBoxAirports.add(lb);
			checkBoxAirports.add(cb);
			cb.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent e) {
					if(cb.getState()) {
						a.setVisible(true);
					}
					else {
						a.setVisible(false);
						if(ba.getAirport()==a)	{
							ba.setAirport(null);
							t.unpause();
						}
					}
					repaint();
				}
				
			});
			validate();
			repaint();
		}
	
	public void addActions() {
		
		submitAirport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name, code;
				int x,y;
				try {
				name = airportName.getText();
				code = airportCode.getText();
				if(allAirports.containsKey(code))	throw new DuplicateNames(code);
				x = Integer.parseInt(airportX.getText());
				y = Integer.parseInt(airportY.getText());
				Airport a = new Airport(name,code,x,y);
				addAirport(a);
				t.resetTimer();
				}
				catch(Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		submitFlight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String from, to, time;
					int duration;
					from = flightStart.getText();
					to = flightEnd.getText();
					time = flightTime.getText();
					duration = Integer.parseInt(flightDuration.getText());
					int h, m;
					if(time.length()!=5 ) throw new InvalidTimeFormat(time);
					h = Integer.parseInt(time.substring(0,2));
					m = Integer.parseInt(time.substring(3,5));
					if(h<0 || h>24)	throw new InvalidHours();
					if(m<0 || m>60)	throw new InvalidMinutes();
					Airport a1,a2;
					a1 = allAirports.get(from);
					a2 = allAirports.get(to);
					if(a1==null)	throw new AirportNotFound(from);
					if(a2==null)	throw new AirportNotFound(to);
					Flight f = new Flight(a1,a2,h,m,duration);
					allFlights.add(f);
					flightView.append(f.toString());
					t.resetTimer();
				}
				catch(Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		
		writeAirports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					writer = new Writer("aerodromiIzlaz.csv");
					for(String cd:allAirports.keySet()) {
						Airport a = allAirports.get(cd);
						String line = a.getName()+","+a.getCode()+","+a.getX()+","+a.getY();
						writer.writeFile(line);
					}
					writer.close();
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		
		writeFlights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					writer = new Writer("letoviIzlaz.csv");
					for(Flight f:allFlights) {
						String line = f.getFrom().getCode()+","+f.getTo().getCode()+","+f.getDepartureHours()+":"+f.getDepartureMinutes()+","+f.getDuration();
						writer.writeFile(line);
					}
					writer.close();
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		
		readAirports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reader = new Reader("aerodromiUlaz.csv");
					while(true) {
						String current = reader.readLine();
						if(reader.isEndOfFile())	break;
						String[] lines = current.split(",");
						Airport a = new Airport(lines[0],lines[1],Integer.parseInt(lines[2]),Integer.parseInt(lines[3]));
						addAirport(a);
						t.resetTimer();
					}
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		readFlights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reader = new Reader("letoviUlaz.csv");
					while(true) {
						String current = reader.readLine();
						if(reader.isEndOfFile())	break;
						String[] lines = current.split(",");
						Airport a1,a2;
						a1=allAirports.get(lines[0]);
						a2=allAirports.get(lines[1]);
						if(a1==null)	throw new AirportNotFound(lines[0]);
						if(a2==null)	throw new AirportNotFound(lines[1]);
						int h,m;
						h = Integer.parseInt(lines[2].substring(0, 2));
						m = Integer.parseInt(lines[2].substring(3, 5));
						Flight f = new Flight(a1,a2,h,m,Integer.parseInt(lines[3]));
						allFlights.add(f);
						Airplane a = new Airplane(f,Traffic.this);
						allPlanes.add(a);
						flightView.append(f.toString());
						t.resetTimer();
						
					}
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		
		startSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					t.pause();
					startSimulation.setEnabled(false);
					pauseSimulation.setEnabled(true);
					resumeSimulation.setEnabled(true);
					restartSimulation.setEnabled(true);
					for(Airplane a:allPlanes) {
						a.start();
					}
					SimulationClock.getInstance().setPaused(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		pauseSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimulationClock.getInstance().setPaused(true);
					for(Airplane a: allPlanes) {
						a.setPaused(true);
					}
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		resumeSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimulationClock.getInstance().setPaused(false);
					for(Airplane a: allPlanes) {
						a.setPaused(false);
					}
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
			
		});
		
		restartSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					t.unpause();
					startSimulation.setEnabled(true);
					pauseSimulation.setEnabled(false);
					resumeSimulation.setEnabled(false);
					restartSimulation.setEnabled(false);
					SimulationClock.getInstance().setPaused(true);
			        SimulationClock.getInstance().reset();
					for(String cd:allAirports.keySet()) {
						Airport a  = allAirports.get(cd);
						a.setDepartureTime(-900);
					}

					for (Airplane a : allPlanes) {
						a.resetAirplane();
		                a.interrupt();
		            }
		            for (Airplane a : allPlanes) {
		                try {
		                    a.join();
		                } catch (InterruptedException ex) {
		                    Thread.currentThread().interrupt();
		                }
		            }
		           
		            ArrayList<Airplane> newPlanes = new ArrayList<>();
		            for (Flight f : allFlights) {
		                Airplane a = new Airplane(f, Traffic.this);
		                newPlanes.add(a);
		            }

		            allPlanes.clear();
		            allPlanes.addAll(newPlanes);
		            
		           
				} catch (Exception e1) {
					new ErrorBox(Traffic.this,e1);
				}
			}
		});
		
	}
	
	public void addUserInput() {
		userInput = new Panel();
		userInput.setBackground(Color.YELLOW);
		userInput.setLayout(new GridLayout(0,2,0,20));
		
		//Airport input
		userInput.add(new Label("Naziv aerodroma"));
		userInput.add(airportName = new TextField());
		userInput.add(new Label("Oznaka aerodroma"));
		userInput.add(airportCode = new TextField());
		userInput.add(new Label("x koordinata"));
		userInput.add(airportX = new TextField());
		userInput.add(new Label("y koordinata"));
		userInput.add(airportY = new TextField());
		userInput.add(new Label("Potvrdite unos aerodroma:"));
		userInput.add(submitAirport = new Button("Potvrdi aerodrom"));
		userInput.add(writeAirports = new Button("Upisi aerodrome u datoteku:"));
		userInput.add(airportOutputFile = new TextField());
		userInput.add(readAirports = new Button("Procitaj aerodrome iz datoteke:"));
		userInput.add(airportInputFile = new TextField());
		
		
		//Flight input
		userInput.add(new Label("Pocetni aerodrom"));
		userInput.add(flightStart = new TextField());
		userInput.add(new Label("Kranji aerodrom"));
		userInput.add(flightEnd = new TextField());
		userInput.add(new Label("Vreme leta"));
		userInput.add(flightTime = new TextField());
		userInput.add(new Label("Trajanje leta"));
		userInput.add(flightDuration = new TextField());
		userInput.add(new Label("Potvrdite unos leta:"));
		userInput.add(submitFlight = new Button("Potvrdi let"));
		userInput.add(writeFlights = new Button("Upisi letove u datoteku:"));
		userInput.add(flightOutputFile = new TextField());
		userInput.add(readFlights = new Button("Procitaj letove iz datoteke:"));
		userInput.add(flightInputFile = new TextField());
		
		this.add(userInput, BorderLayout.WEST);
	}
	
	public void addDataView() {
		dataView = new Panel();
		dataView.setBackground(Color.MAGENTA);
		dataView.setLayout(new GridLayout(0,1));
		dataView.add(new Label("Prikaz svih aerodroma:"));
		dataView.add(airportView = new TextArea());
		dataView.add(new Label("Prikaz svih letova:"));
		dataView.add(flightView = new TextArea());
		checkBoxAirports = new Panel();
		checkBoxAirports.setLayout(new GridLayout(0,2));
		dataView.add(checkBoxAirports);
		
		flightView.setEditable(false);
		airportView.setEditable(false);
		this.add(dataView,BorderLayout.EAST);
	}
	
	public void addSimulation() {
		simulation = new Panel();
		simulation.setBackground(Color.GREEN);
		simulation.add(new Label("Pokrenite simulaciju:"));
		simulation.add(startSimulation = new Button("Pokreni simulaciju"));
		simulation.add(new Label("Pauzirajte simulaciju:"));
		simulation.add(pauseSimulation = new Button("Pauziraj simulaciju"));
		simulation.add(new Label("Nastavite simulaciju:"));
		simulation.add(resumeSimulation = new Button("Nastavi simulaciju"));
		simulation.add(new Label("Resetujte simulaciju na pocetno stanje"));
		simulation.add(restartSimulation = new Button("Resetujte simulaciju"));
		
		pauseSimulation.setEnabled(false);
		resumeSimulation.setEnabled(false);
		restartSimulation.setEnabled(false);
		
		this.add(simulation,BorderLayout.SOUTH);
	}
	
	public void paint(Graphics g) {
		map.repaint();
	}

	public static void main(String[] args) {
		instance = new Traffic();
	}
	
}
