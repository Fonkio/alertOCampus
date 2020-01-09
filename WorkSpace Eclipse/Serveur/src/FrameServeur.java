import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JFrame;

public class FrameServeur extends JFrame implements Serializable{

	private static final long serialVersionUID = 5052119543604246119L;
	
	public FrameServeur() {
		this.setTitle("ServeurAlertOCampus");
		VueServeur vue = new VueServeur();
		this.add(vue);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		Serveur s = new Serveur();
		Thread t = new Thread(s);
		t.start();
		s.run();
		
		this.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                s.close();
	            }
	        });


	}
	
	
	


}
