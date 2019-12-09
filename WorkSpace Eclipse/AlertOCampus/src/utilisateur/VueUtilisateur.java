package utilisateur;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;

public class VueUtilisateur extends JFrame {
	Client client;
	ControleurUtilisateur controleur;
	
	public VueUtilisateur(ControleurUtilisateur controleur) {
		this.controleur = controleur;
		
		this.setTitle("AlertOCampus");
		intialiserComposants();
		
		
	}

	private void intialiserComposants() {
		// TODO Auto-generated method stub
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		/* Création du panneau de gauche */
		/* Composé de l'arbre des tickets et le bouton de création des fils de discussion */
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new FlowLayout());
		JTree arbreTickets = new JTree();
		JButton boutonCreationFil = new JButton(); //TODO factoriser Jtree et jbutton ?
		westPanel.add(arbreTickets);
		westPanel.add(arbreTickets);
		
		/* Création du panneau central */
		/* Composé de la discussion et de la zone de saisie */
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel panelDiscussion = new JPanel();
		panelDiscussion.setLayout(new BorderLayout());
		
		JPanel panelSaisie = new JPanel();
	}

	
	
}
