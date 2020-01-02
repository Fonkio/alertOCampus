package utilisateur;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.annotation.processing.Messager;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.table.TableColumnModel;

public class VueUtilisateur extends JPanel {
	Client client;
	ControleurUtilisateur controleur;
	private JLabel titreFil = new JLabel("Titre");
	private JLabel dateFil = new JLabel("Date");
	private JLabel createur = new JLabel("Créateur");
	private JLabel groupe = new JLabel("Groupe");
	ModeleTableau modeleTableau = new ModeleTableau();
	private JTable messages = new JTable(modeleTableau);
	
	public VueUtilisateur() {
		
		this.controleur = new ControleurUtilisateur(this);
	
		// Création panel
		this.setLayout(new BorderLayout());
		
		
			/* Création du panneau de gauche */
			/* Composé de l'arbre des tickets et le bouton de création des fils de discussion */
			JPanel westPanel = new JPanel();
			westPanel.setLayout(new BorderLayout());
				JTree arbreTickets = new JTree();
				JButton boutonCreationFil = new JButton(); //TODO factoriser Jtree et jbutton ?
				boutonCreationFil.setText("Nouveau Fil");
			westPanel.add(arbreTickets, BorderLayout.CENTER);
			westPanel.add(boutonCreationFil, BorderLayout.SOUTH);
		
			
			/* Création du panneau central */
			/* Composé de la discussion et de la zone de saisie */
			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			
				/* Création discussion */
				JPanel panelDiscussion = new JPanel();
				panelDiscussion.setLayout(new BorderLayout());
					/* Barre titre fil */
					JPanel panelTitreFil = new JPanel();
					Box line1 = new Box(BoxLayout.X_AXIS);
					titreFil.setFont(titreFil.getFont().deriveFont(20f));
					line1.add(titreFil);
					Box line2 = new Box(BoxLayout.X_AXIS);
					line2.add(Box.createHorizontalGlue());
					line2.add(dateFil);
					line2.add(Box.createHorizontalGlue());
					line2.add(createur);
					line2.add(Box.createHorizontalGlue());
					line2.add(groupe);
					line2.add(Box.createHorizontalGlue());
					panelTitreFil.setLayout(new BoxLayout(panelTitreFil, BoxLayout.Y_AXIS));
					panelTitreFil.add(line1);
					panelTitreFil.add(line2);
					panelTitreFil.add(Box.createVerticalGlue());
					panelTitreFil.setBorder(BorderFactory.createLineBorder(Color.black));
					/* Tableau message */
					TableColumnModel tcm = messages.getColumnModel();
					messages.setRowHeight(50);
					tcm.getColumn(0).setCellRenderer(new RenduTableau());
					JPanel messagep = new JPanel();
					messagep.setLayout(new BorderLayout());
					messagep.setBackground(Color.red);
					messagep.add(messages, BorderLayout.NORTH);
					JScrollPane scrollPane = new JScrollPane(messagep);
					scrollPane.setBackground(Color.red);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					
					
				panelDiscussion.add(panelTitreFil, BorderLayout.NORTH);
				panelDiscussion.add(scrollPane, BorderLayout.CENTER);
				
				/* Création Saisie */
				JPanel panelSaisie = new JPanel();
				panelSaisie.setLayout(new BorderLayout());
				JTextArea zoneTexte = new JTextArea(); 
				zoneTexte.setRows(3);
				zoneTexte.setEditable(true);
				panelSaisie.add(zoneTexte, BorderLayout.CENTER);
				JButton buttonEnvoyer = new JButton();
				buttonEnvoyer.setText("Envoyer");
				panelSaisie.add(buttonEnvoyer, BorderLayout.EAST);
			
			/* Ajout des 2 composants */
			centerPanel.add(panelDiscussion, BorderLayout.CENTER);
			centerPanel.add(panelSaisie, BorderLayout.SOUTH);
		
		/* Ajout des 2 composants */
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
		nouveauMessage(new Message(1, "C'est mon message Test : BONNE ANNEE", new Date(2020, 1, 1), new Utilisateur(1, "FABRE", "Maxime")));
	
	}
	
	public void setInfoFil(String titre, String date, String createur, String groupe) {
		this.titreFil.setText(titre);
		this.dateFil.setText(date);
		this.createur.setText(createur);
		this.groupe.setText(groupe);
	}
	
	public void nouveauMessage(Message m) {
		modeleTableau.messages.add(m);
		modeleTableau.fireTableDataChanged();
	}

	
	
}
