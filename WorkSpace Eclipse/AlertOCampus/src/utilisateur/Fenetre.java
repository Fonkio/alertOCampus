package utilisateur;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Fenetre extends JFrame implements WindowListener{
	
	private static final long serialVersionUID = 1L;
	private VueUtilisateur vueUtilisateur;
	
	
	public Fenetre() {		
		boolean find = false;
		boolean fileExist = true;
	    this.setTitle("AlertOCampus");
	    // créer le flot d’entrée et l’associer au fichier
 		ObjectInputStream in = null;
 		try {
 			in = new ObjectInputStream(new FileInputStream("data.txt"));
 		} catch (IOException e) {
 			System.out.println("INITIALISATION FIRST START");
 			fileExist = false;
 		}
 		// lire l’objet dans le flux d’entrée et le transtyper dans son type
 		if (fileExist) {
 			try {
 	 			find = true;
 	 			this.vueUtilisateur = (VueUtilisateur) in.readObject();
 	 		} catch (ClassNotFoundException | IOException e) {
 	 			e.printStackTrace();
 	 		}
 			// fermer le flux d’entrée
 	 		try {
 	 			in.close();
 	 		} catch (IOException e) {
 	 			e.printStackTrace();
 	 		}
 	 		
 		}
 		if(!find) {
	 		this.vueUtilisateur = new VueUtilisateur();
	 	}
 		
	    this.add(this.vueUtilisateur);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.addWindowListener(this);
	    this.setSize(1500,990);
	    this.setVisible(true);
	    Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
			    System.out.println("RECUP MESSAGE ...");
			    if(vueUtilisateur.controleur.getModeleUtilisateur().getCurrentUser() != null) {
			    	System.out.println("RECUP TERMINEE");
			    }else {
			    	System.out.println("RECUP TERMINEE : UTILISATEUR NON CONNECTE");
			    }
			  }
			}, 5*1000, 5*1000);
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// créer le flot de sortie et l’associer au fichier de nom « toto »
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("data.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// écrire l’objet dans le flux de sortie
		try {
			out.writeObject(this.vueUtilisateur);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// fermer le flux de sortie
		try {
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("APPLI SAVE");
		System.exit(0);
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}