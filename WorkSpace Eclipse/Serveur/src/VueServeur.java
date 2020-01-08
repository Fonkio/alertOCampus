import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.List;
import java.util.TreeSet;

import javax.sound.sampled.BooleanControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
import javax.swing.ListModel;

public class VueServeur extends JTabbedPane implements Serializable{
	
	private static final long serialVersionUID = 5827384626815819750L;
	private static final int NB_TABS = 2; // Index 0 = Groupe et Index 1 = Utilisateur
	private ControleurServeur controleur;
	
	private DefaultListModel<Groupe> listeGroupes; //Liste du panneau de gauche pour l'onglet Groupes
	private DefaultListModel<Utilisateur> listeMembresDeGroupe; //Liste du panneau de droite pour l'onglet Groupes
	private DefaultListModel<Utilisateur> listeUsers; //Liste du panneau de gauche pour l'onglet Utilisateurs
	private DefaultListModel<Groupe> listeGroupesDeMembre; //Liste du panneau de droite pour l'onglet Utilisateurs
	
	private JList<Utilisateur> listeGaucheUsers;
	private JList<Groupe> listeGaucheGroups;
	private JList<Groupe> listeDroiteUsers;
	private JList<Utilisateur> listeDroiteGroups;
	
	//private 
	private JTextField nomGroupeTF;
	private JTextField nomUserTF;
	private JTextField prenomUserTF;
	private JButton[] addBtn = new JButton[NB_TABS];
	
	public VueServeur () {
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		listeGroupes = new DefaultListModel<>();
		listeUsers = new DefaultListModel<>();
		listeMembresDeGroupe = new DefaultListModel<>();
		listeGroupesDeMembre = new DefaultListModel<>();
		this.constructGroupPane();
		this.constructUserPane();
		this.controleur = new ControleurServeur(this);
		this.addListeners();
		
	}
	
	public void addUser(Utilisateur user) {
		listeUsers.addElement(user);
	}
	
	public void addGroup(Groupe group) {
		listeGroupes.addElement(group);
	}
	
	public void addMemberOfGroup(Utilisateur user) {
		listeMembresDeGroupe.addElement(user);
	}
	
	public void addGroupOfMember(Groupe group) {
		listeGroupesDeMembre.addElement(group);
	}
	
	public void resetMemberOfGroupList() {
		listeMembresDeGroupe.clear();
	}
	
	public void resetGroupOfMemberList() {
		listeGroupesDeMembre.clear();
	}
	
	
	public void setNomGroupeTF(String newName) {
		this.nomGroupeTF.setText(newName);
	}

	public void setNomUserTF(String newName) {
		this.nomUserTF.setText(newName);
	}

	public void setPrenomUserTF(String newName) {
		this.prenomUserTF.setText(newName);
	}

	private void addListeners() {
		// Ecouteur pour changement d'état du controleur quand on change d'onglet 
		this.addChangeListener(this.controleur);
		
		// Ecouter sur les boutons d'ajout
		for (int i = 0; i < NB_TABS; i++) {
			this.addBtn[i].addActionListener(controleur);
		}
		
		// Ecoute les listes de gauche de chaque onglet (en sélectionnant)
		listeGaucheUsers.addListSelectionListener(controleur);
		listeGaucheGroups.addListSelectionListener(controleur);
		//listeDroiteGroups.addListSelectionListener(controleur);
		//listeDroiteUsers.addListSelectionListener(controleur);
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
		listeGaucheUsers = new JList<>(listeUsers);
		JScrollPane listScroll = new JScrollPane(listeGaucheUsers);


		// Boutons supprimer et ajouter en bas de la liste de groupes
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		this.addBtn[1] = new JButton("Nouvel utilisateur");
		JButton buttonDelete = new JButton("Supprimer");
		buttonsPanel.add(this.addBtn[1]);
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
		this.nomUserTF= new JTextField();
		this.nomUserTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nomUserTF.getPreferredSize().height));
		
		// Champ de saisie du prénom
		JLabel prenomLabel = new JLabel("Prénom");
		this.prenomUserTF = new JTextField();
		this.prenomUserTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.prenomUserTF.getPreferredSize().height));
		
		// Sélection de groupes
		JLabel ajoutLabel = new JLabel("Ajouter l'utilisateur à un groupe");
		JComboBox<String> ajoutGroupeCB = new JComboBox<>();
		ajoutGroupeCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nomUserTF.getPreferredSize().height));
		
		// Liste des groupes auxquels appartient le membre sélectionné
		JLabel labelListeMembresGroupes = new JLabel("Liste des groupes");
		this.listeDroiteUsers = new JList<>(this.listeGroupesDeMembre);
		JScrollPane listeMembresScroll = new JScrollPane(listeDroiteUsers);
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
		.addComponent(this.nomUserTF));
		vGroup.addGroup(grouplayout.createParallelGroup(Alignment.BASELINE)
		.addComponent(prenomLabel)
		.addComponent(this.prenomUserTF));	
		grouplayout.setVerticalGroup(vGroup);
		
		GroupLayout.SequentialGroup hGroup = grouplayout.createSequentialGroup();
		hGroup.addGroup(grouplayout.createParallelGroup()
				.addComponent(nomLabel)
				.addComponent(prenomLabel));
		hGroup.addGroup(grouplayout.createParallelGroup()
				.addComponent(this.nomUserTF)
				.addComponent(this.prenomUserTF));
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
	listeGaucheGroups = new JList<>(listeGroupes);
	JScrollPane listScroll = new JScrollPane(listeGaucheGroups);
	

	
	// Boutons supprimer et ajouter en bas de la liste de groupes
	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new FlowLayout());
	this.addBtn[0]= new JButton("Nouveau groupe");
	JButton buttonDelete = new JButton("Supprimer");
	buttonsPanel.add(this.addBtn[0]);
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
	this.nomGroupeTF = new JTextField();
	this.nomGroupeTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nomGroupeTF.getPreferredSize().height));
	
	
	// Liste des membres du groupe sélectionné
	JLabel labelListeMembresGroupes = new JLabel("Liste des membres");
	listeDroiteGroups = new JList<>(listeMembresDeGroupe);
	JScrollPane listeMembresScroll = new JScrollPane(listeDroiteGroups);
	JButton supprimerDuGroupeBtn = new JButton("Supprimer du groupe");
	
	// Bouton pour sauvegarder les modifications
	JButton sauvegarder = new JButton("Sauvegarder les modifications");	
	
	// Mise en forme de chaque compartiment de la partie sous forme de Box
	// Champ de saisie du nom
	Box box1 = new Box(BoxLayout.PAGE_AXIS);
	box1.add(nomLabel);
	box1.add(this.nomGroupeTF);
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
