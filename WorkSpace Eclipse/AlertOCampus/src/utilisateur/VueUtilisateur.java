package utilisateur;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;

public class VueUtilisateur extends JPanel {
	Client client;
	ControleurUtilisateur controleur;
	
	public VueUtilisateur() {
		this.controleur = new ControleurUtilisateur(this);
	
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
		//westPanel.add(arbreTickets); pourquoi 2x la même ligne ?
		
		/* Création du panneau central */
		/* Composé de la discussion et de la zone de saisie */
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		/* Création discussion */
		JPanel panelDiscussion = new JPanel();
		panelDiscussion.setLayout(new BorderLayout());
		
		/* Création Saisie */
		JPanel panelSaisie = new JPanel();
		this.setLayout(new BorderLayout());
		JTextPane zoneTexte = new JTextPane(); 
		panelSaisie.add(zoneTexte, BorderLayout.CENTER);
		JButton buttonEnvoyer = new JButton();
		buttonEnvoyer.setText("Envoyer");
		panelSaisie.add(buttonEnvoyer, BorderLayout.EAST);
		
		
		
		centerPanel.add(panelDiscussion, BorderLayout.CENTER);
		centerPanel.add(panelSaisie, BorderLayout.SOUTH);
		
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	
	
}
