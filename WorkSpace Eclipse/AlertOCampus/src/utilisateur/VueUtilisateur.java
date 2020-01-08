package utilisateur;


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class VueUtilisateur extends JPanel implements Serializable {
	private static final long serialVersionUID = 1L;
	Client client;
	ControleurUtilisateur controleur;
	private JLabel titreFil = new JLabel("Titre");
	private JLabel dateFil = new JLabel("Date");
	private JLabel createur = new JLabel("Créateur");
	private JLabel groupe = new JLabel("Groupe");
	JTextArea zoneTexte = new JTextArea();
	private ModeleTableau modeleTableau = new ModeleTableau();
	private JTable messages = new JTable(modeleTableau);
	private DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Groupes");
	private JTree arbreTickets = new JTree(racine) ;
	private NavigableSet<Groupe> listeGroupes = new TreeSet<Groupe>();
	private NavigableSet<Fil> listeFils = new TreeSet<Fil>(new Comparator<Fil>() {

		@Override
		public int compare(Fil o1, Fil o2) {
			return o2.getMessages().first().getdCreation().compareTo(o1.getMessages().first().getdCreation());
		}
	});
	private Fil selectedFil;

	public VueUtilisateur() {
		
		this.controleur = new ControleurUtilisateur(this);
	
		// Création panel
		this.setLayout(new BorderLayout());

			/* Création du panneau de gauche */
			/* Composé de l'arbre des tickets et le bouton de crï¿½ation des fils de discussion */
			JPanel westPanel = new JPanel();
			westPanel.setLayout(new BorderLayout());
				arbreTickets.addTreeSelectionListener(controleur);
				JButton boutonCreationFil = new JButton(); //TODO factoriser Jtree et jbutton ?
				boutonCreationFil.addActionListener(controleur);
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
					messagep.add(messages, BorderLayout.NORTH);
					JScrollPane scrollPane = new JScrollPane(messagep);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					
					
				panelDiscussion.add(panelTitreFil, BorderLayout.NORTH);
				panelDiscussion.add(scrollPane, BorderLayout.CENTER);
				
				/* Création Saisie */
				JPanel panelSaisie = new JPanel();
				panelSaisie.setLayout(new BorderLayout());
				 
				zoneTexte.setRows(3);
				zoneTexte.setBorder(BorderFactory.createLineBorder(Color.black));
				zoneTexte.setEditable(true);
				panelSaisie.add(zoneTexte, BorderLayout.CENTER);
				JButton buttonEnvoyer = new JButton();
				buttonEnvoyer.addActionListener(controleur);
				buttonEnvoyer.setText("Envoyer");
				panelSaisie.add(buttonEnvoyer, BorderLayout.EAST);
			
			/* Ajout des 2 composants */
			centerPanel.add(panelDiscussion, BorderLayout.CENTER);
			centerPanel.add(panelSaisie, BorderLayout.SOUTH);
		
		/* Ajout des 2 composants */
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		
	}
	
	public void setInfoFil(String titre, String date, String createur, String groupe) {
		System.out.println("[EN COURS] Mise a jour des infos fil");
		this.titreFil.setText(titre);
		this.dateFil.setText(date);
		this.createur.setText(createur);
		this.groupe.setText(groupe);
		System.out.println("[TERMINE ] Mise a jour des infos fil");
	}
	
	public void setMessages(NavigableSet<Message> m) {
		System.out.println("[EN COURS] Mise a jour du tableau message");
		modeleTableau.messages = m;
		modeleTableau.fireTableDataChanged();
		System.out.println("[TERMINE ] Mise a jour du tableau message");
	}
	
	public void updateArbre() {
		System.out.println("[EN COURS] Mise a jour de l'arbre");
		//RAZ DE L'ARBRE
		racine.removeAllChildren();
		//RECLASSEMENT DES FILS
		if(selectedFil != null) {
			System.out.println("a"+listeFils.toString());
			listeFils.remove(selectedFil);
			listeFils.add(selectedFil);
			System.out.println("b"+listeFils.toString());
		}
		//PARCOURS DES GROUPES
		for (Groupe g : listeGroupes) {
			//ON LES AJOUTES
			DefaultMutableTreeNode dtn = new DefaultMutableTreeNode(g);
			racine.add(dtn);
			//PARCOURS DE FILS
			for (Fil f : listeFils) {
				//SI LA DEST. DU FIL CORRESPOND AU GROUPE
				if (f.getDestination().equals(g)) {
					//ON L'AJOUTE
					dtn.add(new DefaultMutableTreeNode(f));
				}
			}
		}
		
		//ON RECHARGE L'AFFICHAGE DE L'ARBRE
		((DefaultTreeModel)arbreTickets.getModel()).reload();
		for (int i = 0; i < arbreTickets.getRowCount(); i++) {
			arbreTickets.expandRow(i);
		}
		System.out.println("[TERMINE ] Mise a jour de l'arbre");
	}



	public String[] auth() {
		System.out.println("[EN COURS] Pop-up d'authentification");
		//CREATION POPUP
		JLabel ll = new JLabel("Identifiant :");
		JLabel pl = new JLabel("Mot de passe :");
		JTextField lf = new JTextField();
		JPasswordField pf = new JPasswordField();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(ll);
		panel.add(lf);
		panel.add(pl);
		panel.add(pf);
		JOptionPane jop = new JOptionPane();
		//AFFICHAGE POPUP
		int ok = jop.showConfirmDialog(this, panel, "Entrez votre mot de passe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		//TEST RESULTAT
		if (ok == JOptionPane.OK_OPTION) {
			//RETOURNE ID MDP
			String[] st = new String[2];
			st[0] = lf.getText().toString();
			st[1] = new String(pf.getPassword());
			System.out.println("[TERMINE ] L'utilisateur valide ");
			return st;
		}
		System.out.println("[TERMINE ] L'utilisateur annule");
		return null;
	}

	public void erreurAuth() {
		//AFFICHAGE POPUP ID MDP INCORTRECT
		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(this, "Identifiant/Mot de passe incorrect !", "Connexion", JOptionPane.ERROR_MESSAGE);;
		System.out.println("[TERMINE ] Pop-up d'erreur authentification");
	}
	
	

	public void nouveauFil() {
		System.out.println("[EN COURS] Pop-up nouveau fil");
		//CREATION POPUP
		JLabel titreLabel = new JLabel("Titre :");
		JLabel groupeLabel = new JLabel("Groupe :");
		JLabel messageLabel = new JLabel("Message :");
		
		JTextField titreField = new JTextField();
		
		JComboBox<Groupe> groupeCombo = new JComboBox<Groupe>(controleur.getTableGroupe()); //CREATION COMBO DEMANDE SERVEUR LISTE GROUPE
		JTextArea zoneTexte = new JTextArea(); 
		zoneTexte.setRows(3);
		zoneTexte.setBorder(BorderFactory.createLineBorder(Color.black));
		zoneTexte.setEditable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(titreLabel);
		panel.add(titreField);
		panel.add(groupeLabel);
		panel.add(groupeCombo);
		panel.add(messageLabel);
		panel.add(zoneTexte);
		JOptionPane jop = new JOptionPane();
		
		//AFFICHAGE POPUP
		int ok = jop.showConfirmDialog(this, panel, "Création de fil de discussion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		//TEST DU RESULTAT
		
		if (ok == JOptionPane.OK_OPTION) {
			System.out.println("[TERMINE ] L'utilisateur valide");
			//RECUP GROUPE
			Groupe g = (Groupe)groupeCombo.getSelectedItem();
			//CREATION DU FIL
			Fil f =new Fil(0, titreField.getText(), g, controleur.getModeleUtilisateur().getCurrentUser());
			//SI LE GROUPE N'EXISTE PAS DANS L'ARBRE
			if (!listeGroupes.contains(g)) {
				listeGroupes.add(g); //ON L'AJOUTE
			}
			
			controleur.envoyerFil(f); //ON ENVOI LE NOUVEAU FIL AU SERVEUR
			//CREATION DU MESSAGE DE CREATION FIL
			Message m = new Message(0, zoneTexte.getText(), new Date(), controleur.getModeleUtilisateur().getCurrentUser(), g.getListeUtilisateurs());
			//ENVOI AU SERVEUR
			controleur.envoyerMessage(m, f);
			//ON L'AJOUTE AU FIL
			f.getMessages().add(m);
			listeFils.add(f); //ON AJOUTE LE FILS POUR METTRE A JOUR L'ARBRE
			System.out.println("Ajout fil : "+f.getTitre());
			updateArbre(); //MISE A JOUR DE LA STRUCTURE DE L'ARBRE
			//ON CHARGE LE FIL
			chargerFil(f);
		} else {
			System.out.println("[TERMINE ] L'utilisateur annule");
		}
	}
	
	public void nouveauMessage(Message m) {
		System.out.println("[TERMINE ] Ajout nouveau message");
		//ENVOI MESSAGE SERVEUR
		modeleTableau.messages.add(m);
		//AJOUT MESSAGE FIL
		selectedFil.getMessages().add(m);
		//ON RECHARGE LE FIL
		chargerFil(selectedFil);
		updateArbre();
	}

	public void chargerFil(Fil f) {
		System.out.println("[EN COURS] Chargement fil de discussion");
		setMessages(f.getMessages());
		setInfoFil(f.getTitre(), f.getMessages().first().getdCreation().toString(), f.getCreateur().toString(), f.getDestination().getLibelle());
		selectedFil = f;
		System.out.println("[TERMINE ] Chargement fil de discussion");
		System.out.println(listeFils.toString());
	}

	public JTree getArbreTickets() {
		return arbreTickets;
	}
	
	public Fil getSelectedFil() {
		return selectedFil;
	}

	public String getTextArea() {
		System.out.println("[TERMINE ] Recup. text area");
		//RETOURNE LE TEXTE DU TEXT AREA PUIS SUPPRIME LE CONTENU
		String s = zoneTexte.getText();
		zoneTexte.setText("");
		return s;
	}
	
	

	
	
}
