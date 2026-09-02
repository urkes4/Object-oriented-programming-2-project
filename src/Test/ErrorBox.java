package Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ErrorBox extends Dialog{

	public ErrorBox(Frame f, Exception e) {
		super(f,"GRESKA");
		Label errorInfo = new Label(e.getMessage());
		this.add(errorInfo,BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(300,300);
		Button ok = new Button("OK");
		Panel buttons = new Panel();
		buttons.add(ok);
		this.add(buttons,BorderLayout.SOUTH);
		Traffic.instance.t.pause();
		Traffic.instance.t.resetTimer();
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Traffic.instance.t.unpause();
				dispose();
			}
			
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Traffic.instance.t.unpause();
                dispose();
            }
		});
	}

}
