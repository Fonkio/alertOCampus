package utilisateur;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.Messager;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class VueUtilisateur extends JPanel {
	Client client;
	ControleurUtilisateur controleur;
	private JLabel titreFil = new JLabel("Titre");
	private JLabel dateFil = new JLabel("Date");
	private JLabel createur = new JLabel("Cr�ateur");
	private JLabel groupe = new JLabel("Groupe");
	private ModeleTableau modeleTableau = new ModeleTableau();
	private JTable messages = new JTable(modeleTableau);
	private DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Groupes");
	private JTree arbreTickets = new JTree(racine) ;
	private List<Groupe> listeGroupes = new ArrayList<Groupe>();
	private List<Fil> listeFils = new ArrayList<Fil>();
	private Fil selectedFil;
	
	
	public VueUtilisateur() {
		
		this.controleur = new ControleurUtilisateur(this);
	
		// Création panel
		this.setLayout(new BorderLayout());
		
		
			/* Création du panneau de gauche */
			/* Composé de l'arbre des tickets et le bouton de cr�ation des fils de discussion */
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
				JTextArea zoneTexte = new JTextArea(); 
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
		
		

		
		/*TEST */
		
	
		
	}
	
	public void setInfoFil(String titre, String date, String createur, String groupe) {
		this.titreFil.setText(titre);
		this.dateFil.setText(date);
		this.createur.setText(createur);
		this.groupe.setText(groupe);
	}
	
	public void SetMessages(List<Message> m) {
		modeleTableau.messages = new ArrayList<Message>(m);
		modeleTableau.fireTableDataChanged();
	}
	
	public void updateArbre() {
		racine.removeAllChildren();
		for (Groupe g : listeGroupes) {
			racine.add(new DefaultMutableTreeNode(g));
			System.out.println("groupe");
		}
		for (int i = 0; i < listeGroupes.size(); i++) {
			for (Fil f : listeFils) {
				if (f.getDestination().equals(listeGroupes.get(i))) {
					System.out.println("fil");
					DefaultMutableTreeNode tn = (DefaultMutableTreeNode) racine.getChildAt(i);
					tn.add(new DefaultMutableTreeNode(f));
				}
			}
		}
		System.out.println("Hey");
		((DefaultTreeModel)arbreTickets.getModel()).reload();
	}



	public String[] auth() {
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
		int ok = jop.showConfirmDialog(this, panel, "Entrez votre mot de passe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (ok == JOptionPane.OK_OPTION) {
			String[] st = new String[2];
			st[0] = lf.getText().toString();
			st[1] = pf.getPassword().toString();
			return st;
		}
		return null;
	}

	public void erreurAuth() {
		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(this, "Identifiant/Mot de passe incorrect !", "Connexion", JOptionPane.ERROR_MESSAGE);;
	}

	public void nouveauFil() {
		JLabel titreLabel = new JLabel("Titre :");
		JLabel groupeLabel = new JLabel("Groupe :");
		JLabel messageLabel = new JLabel("Message :");
		
		JTextField titreField = new JTextField();
		
		JComboBox<Groupe> groupeCombo = new JComboBox<Groupe>(controleur.getTableGroupe()); //TODO
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
		int ok = jop.showConfirmDialog(this, panel, "Cr�ation de fil de discussion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (ok == JOptionPane.OK_OPTION) {
			Groupe g = (Groupe)groupeCombo.getSelectedItem();
			Fil f =new Fil(0, titreField.getText(), g, controleur.getModeleUtilisateur().getCurrentUser());
			if (!listeGroupes.contains(g)) {
				listeGroupes.add(g);
			}
		
			listeFils.add(f);
			controleur.envoyerFil(f);
			updateArbre();
			Message m = new Message(0, zoneTexte.getText(), new Date(), controleur.getModeleUtilisateur().getCurrentUser(), g.getListeUtilisateurs());
			controleur.envoyerMessage(m, f);
			f.getMessages().add(m);
			//List<Message> messages = new ArrayList<Message>(f.getMessages());
			chargerFil(f);
			
		}
	}

	public void chargerFil(Fil f) {
		SetMessages(f.getMessages());
		setInfoFil(f.getTitre(), f.getMessages().get(0).getdCreation().toString(), f.getCreateur().toString(), f.getDestination().getLibelle());
	}

	public JTree getArbreTickets() {
		return arbreTickets;
	}
	
	

	
	
}
