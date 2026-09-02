package Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ActiveObjects.Timer;

public class RemainingTime extends Dialog{
	
	private Timer timer;
	private Label infoText;
	
	public RemainingTime(Frame f, Timer t){
		super(f);
		this.setSize(300,300);
		this.setTitle("Upozorenje malo je vremena ostalo!");
		this.setResizable(false);
		setVisible(true);
		timer = t;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				timer.resetTimer();
                dispose();
            }
		});
		infoText = new Label("Preostalo vreme "+timer.getRemainingTime()+". Kraj rada?");
		Button confirm = new Button("Da");
		Button cancel = new Button("Ne");
		
		confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				timer.resetTimer();
                dispose();
			}
			
		});
		
		add(infoText,BorderLayout.CENTER);
		Panel buttons = new Panel();
		buttons.add(confirm);
		buttons.add(cancel);
		add(buttons,BorderLayout.SOUTH);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(infoText!=null)
			infoText.setText("Preostalo vreme "+timer.getRemainingTime()+". Kraj rada?");
        revalidate();
	}
	
}
