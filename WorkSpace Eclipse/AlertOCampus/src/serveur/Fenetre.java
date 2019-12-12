package serveur;


import javax.swing.JFrame;

public class Fenetre extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private VueServeur vueServeur;
	
	
	public Fenetre() {		
		
	    this.setTitle("AlertOCampus serveur");
	    // selectionner le gestionnaire de mise en page de la FenetreCouleur
	    //this.setLayout(new GridLayout(1, 1));
	    
	    this.vueServeur = new VueServeur();
	    this.add(this.vueServeur);
	   
	    this.pack();
	    this.setVisible(true);
	}

}
