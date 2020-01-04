import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.sound.sampled.BooleanControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VueServeur extends JTabbedPane{
	
	private static final long serialVersionUID = 1L;

	public VueServeur () {
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.constructGroupPane();
		this.constructUserPane();
	}
	
	private void constructUserPane() {
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.setDividerLocation(400);
		
		/* ********************************** */
		/*  Construction de la partie gauche  */
		/* ********************************** */
		// Titre de la section
		JLabel labelPanel = new JLabel("Liste des utilisateurs");

		// Liste des groupes existants
		JScrollPane listScroll = new JScrollPane(new JList<String>());
		
		// Boutons supprimer et ajouter en bas de la liste de groupes
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		JButton buttonAdd = new JButton("Supprimer");
		JButton buttonDelete = new JButton("Nouvel utilisateur");
		buttonsPanel.add(buttonAdd);
		buttonsPanel.add(buttonDelete);
		
		// Ajout des éléments à la partie gauche
		JPanel partieGauche = new JPanel();
		partieGauche.setLayout(new BoxLayout(partieGauche, BoxLayout.PAGE_AXIS));
		partieGauche.add(labelPanel);
		partieGauche.add(listScroll);
		partieGauche.add(buttonsPanel);
		
		panel.add(partieGauche);
		
		
		/* ********************************** */
		/*  Construction de la partie droite  */
		/* ********************************** */
		
		// Création des éléments de la partie droite
		// Champ de saisie du nom
		JLabel nomLabel = new JLabel("Nom");
		JTextField nomTF = new JTextField();
		nomTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, nomTF.getPreferredSize().height));
		
		// Champ de saisie du prénom
		JLabel prenomLabel = new JLabel("Prénom");
		JTextField prenomTF = new JTextField();
		nomTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, nomTF.getPreferredSize().height));
		
		// Sélection de groupes
		JLabel ajoutLabel = new JLabel("Ajouter l'utilisateur à un groupe");
		JComboBox<String> ajoutGroupeCB = new JComboBox<>();
		ajoutGroupeCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, nomTF.getPreferredSize().height));
		
		// Liste des groupes auxquels appartient le membre sélectionné
		JLabel labelListeMembresGroupes = new JLabel("Liste des groupes");
		JScrollPane listeMembresScroll = new JScrollPane(new JList<String>());
		JButton supprimerDuGroupeBtn = new JButton("Supprimer du groupe");
		
		// Bouton pour sauvegarder les modifications
		JButton sauvegarder = new JButton("Sauvegarder les modifications");	
		
		// Mise en forme de chaque compartiment 
		// GroupLayout avec le champ de saisie du nom et du prénom
		JPanel nomprenomPanel = new JPanel();
		GroupLayout grouplayout = new GroupLayout(nomprenomPanel);
		nomprenomPanel.setLayout(grouplayout);
		
		GroupLayout.SequentialGroup vGroup = grouplayout.createSequentialGroup();
		vGroup.addGroup(grouplayout.createParallelGroup(Alignment.BASELINE)
		.addComponent(nomLabel)
		.addComponent(nomTF));
		vGroup.addGroup(grouplayout.createParallelGroup(Alignment.BASELINE)
		.addComponent(prenomLabel)
		.addComponent(prenomTF));	
		grouplayout.setVerticalGroup(vGroup);
		
		GroupLayout.SequentialGroup hGroup = grouplayout.createSequentialGroup();
		hGroup.addGroup(grouplayout.createParallelGroup()
				.addComponent(nomLabel)
				.addComponent(prenomLabel));
		hGroup.addGroup(grouplayout.createParallelGroup()
				.addComponent(nomTF)
				.addComponent(prenomTF));
		grouplayout.setHorizontalGroup(hGroup);
		
		// Liste des membres du groupe sélectionné
		Box box2 = new Box(BoxLayout.PAGE_AXIS);
		box2.add(labelListeMembresGroupes);
		box2.add(listeMembresScroll);
		box2.add(supprimerDuGroupeBtn);
		
		// Bouton sauvegarder placé à droite
		Box box3 = new Box(BoxLayout.LINE_AXIS);
		box3.add(Box.createHorizontalGlue());
		box3.add(sauvegarder);
		
		// Ajout de tous les éléments créés au panneau de droite
		JPanel partieDroite = new JPanel();
		partieDroite.setLayout(new BoxLayout(partieDroite, BoxLayout.PAGE_AXIS));
		partieDroite.add(nomprenomPanel);
		partieDroite.add(Box.createVerticalGlue());
		partieDroite.add(ajoutLabel);
		partieDroite.add(ajoutGroupeCB);
		partieDroite.add(Box.createVerticalGlue());
		partieDroite.add(box2);
		partieDroite.add(Box.createVerticalGlue());
		partieDroite.add(box3);
		
		panel.add(partieDroite);
		
		/* ********************************** */
		/*   Ajout à l'onglet correspondant   */
		/* ********************************** */
		this.add("Utilisateurs", panel);	
	}

	private void constructGroupPane() {
	JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	panel.setDividerLocation(400);
	
	/* ********************************** */
	/*  Construction de la partie gauche  */
	/* ********************************** */
	// Titre de la section
	JLabel labelPanel = new JLabel("Liste des groupes");

	// Liste des groupes existants
	JScrollPane listScroll = new JScrollPane(new JList<String>());
	
	// Boutons supprimer et ajouter en bas de la liste de groupes
	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new FlowLayout());
	JButton buttonAdd = new JButton("Supprimer");
	JButton buttonDelete = new JButton("Nouveau groupe");
	buttonsPanel.add(buttonAdd);
	buttonsPanel.add(buttonDelete);
	
	// Création de la partie gauche et ajout au panneau de groupes principal
	JPanel partieGauche = new JPanel();
	partieGauche.setLayout(new BoxLayout(partieGauche, BoxLayout.PAGE_AXIS));
	partieGauche.add(labelPanel);
	partieGauche.add(listScroll);
	partieGauche.add(buttonsPanel);
	
	panel.add(partieGauche);
	
	
	/* ********************************** */
	/*  Construction de la partie droite  */
	/* ********************************** */
	
	// Création des éléments de la partie droite
	// Champ de saisie du nom
	JLabel nomLabel = new JLabel("Nom du groupe");
	JTextField nomTF = new JTextField();
	nomTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, nomTF.getPreferredSize().height));
	
	
	// Liste des membres du groupe sélectionné
	JLabel labelListeMembresGroupes = new JLabel("Liste des membres");
	JScrollPane listeMembresScroll = new JScrollPane(new JList<String>());
	JButton supprimerDuGroupeBtn = new JButton("Supprimer du groupe");
	
	// Bouton pour sauvegarder les modifications
	JButton sauvegarder = new JButton("Sauvegarder les modifications");	
	
	// Mise en forme de chaque compartiment de la partie sous forme de Box
	// Champ de saisie du nom
	Box box1 = new Box(BoxLayout.PAGE_AXIS);
	box1.add(nomLabel);
	box1.add(nomTF);
	box1.add(Box.createVerticalGlue());
	
	// Liste des membres du groupe sélectionné
	Box box2 = new Box(BoxLayout.PAGE_AXIS);
	box2.add(labelListeMembresGroupes);
	box2.add(listeMembresScroll);
	box2.add(supprimerDuGroupeBtn);
	
	// Bouton sauvegarder placé à droite
	Box box3 = new Box(BoxLayout.LINE_AXIS);
	box3.add(Box.createHorizontalGlue());
	box3.add(sauvegarder);
	
	// Ajout de tous les box au panneau de droite
	JPanel partieDroite = new JPanel();
	partieDroite.setLayout(new BoxLayout(partieDroite, BoxLayout.PAGE_AXIS));
	partieDroite.add(box1);
	partieDroite.add(box2);
	partieDroite.add(Box.createVerticalGlue());
	partieDroite.add(box3);
	
	panel.add(partieDroite);
	
	/* ********************************** */
	/*   Ajout à l'onglet correspondant   */
	/* ********************************** */
	this.add("Groupes", panel);
	}
	
}
