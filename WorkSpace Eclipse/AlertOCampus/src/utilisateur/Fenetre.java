package utilisateur;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private VueUtilisateur vueUtilisateur;
	
	
	public Fenetre() {		
		
	    this.setTitle("AlertOCampus");
	    // selectionner le gestionnaire de mise en page de la FenetreCouleur
	    //this.setLayout(new GridLayout(1, 1));
	    
	    this.vueUtilisateur = new VueUtilisateur();
	    this.add(this.vueUtilisateur);
	   
	    this.pack();
	    this.setVisible(true);
	}

}