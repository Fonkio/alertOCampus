import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
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
	
	protected DefaultListModel<Groupe> listeGroupes; //Liste du panneau de gauche pour l'onglet Groupes
	protected  DefaultListModel<Utilisateur> listeMembresDeGroupe; //Liste du panneau de droite pour l'onglet Groupes
	protected  DefaultListModel<Utilisateur> listeUsers; //Liste du panneau de gauche pour l'onglet Utilisateurs
	protected  DefaultListModel<Groupe> listeGroupesDeMembre; //Liste du panneau de droite pour l'onglet Utilisateurs
	
	protected JList<Utilisateur> listeGaucheUsers;
	protected JList<Groupe> listeGaucheGroups;
	private JList<Groupe> listeDroiteUsers;
	private JList<Utilisateur> listeDroiteGroups;
	
	protected JComboBox<Groupe> ajoutGroupeCB;
	
	//private 
	protected JTextField nomGroupeTF;
	protected JTextField nomUserTF;
	protected JTextField prenomUserTF;
	protected JButton[] addBtn = new JButton[NB_TABS];
	private JButton[] deleteBtn = new JButton[NB_TABS];
	private JButton[] deleteUserFromGroupBtn = new JButton[NB_TABS];
	private JButton[] saveBtn = new JButton[NB_TABS];
	private JButton ajoutUtilisateurGroupeOKBtn;
	
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
	
	public void disableFormUserBtn() {
		this.addBtn[1].setEnabled(false);
		this.deleteUserFromGroupBtn[1].setEnabled(false);
		this.ajoutGroupeCB.setEnabled(false);
	}
	
	public void enableFormUserBtn() {
		this.addBtn[1].setEnabled(true);
		this.deleteUserFromGroupBtn[1].setEnabled(true);
		this.ajoutGroupeCB.setEnabled(true);
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
	
	public void resetMainGroupList() {
		listeMembresDeGroupe.clear();
	}
	
	public void resetMainUserList() {
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
	
	public void addGroupeToComboBox(Groupe groupe) {
		this.ajoutGroupeCB.addItem(groupe);
	}
	
	public void resetFormUser() {
		this.nomUserTF.setText(null);
		this.prenomUserTF.setText(null);
		this.listeGroupesDeMembre.clear();
	}
	
	public void resetFormGroup() {
		this.nomGroupeTF.setText(null);
		this.listeMembresDeGroupe.clear();
	}

	public void disableSelectionBtn() {
		switch(this.controleur.getPanestate()) {
			case GROUPES :
				this.saveBtn[0].setEnabled(false);
				this.listeGaucheGroups.clearSelection();
				break;
			case UTILISATEURS :
				this.saveBtn[1].setEnabled(false);
				this.ajoutGroupeCB.setEnabled(false);
				this.ajoutUtilisateurGroupeOKBtn.setEnabled(false);
				this.listeGaucheUsers.clearSelection();
				this.nomUserTF.setText("");
				this.nomUserTF.setEditable(false);
				this.prenomUserTF.setText("");
				this.prenomUserTF.setEditable(false);
				this.deleteUserFromGroupBtn[1].setEnabled(false);
		}
	}
	
	public void enableSelectionBtn() {
		switch(this.controleur.getPanestate()) {
			case GROUPES :
				this.saveBtn[0].setEnabled(true);
				break;
			case UTILISATEURS :
				this.saveBtn[1].setEnabled(true);
				this.ajoutGroupeCB.setEnabled(true);
				this.ajoutUtilisateurGroupeOKBtn.setEnabled(true);
				this.nomUserTF.setEditable(true);
				this.prenomUserTF.setEditable(true);
				this.deleteUserFromGroupBtn[1].setEnabled(true);
		}
	}
	
	public boolean areUserTFEmpty() {
		return this.nomUserTF.getText().isEmpty() || this.prenomUserTF.getText().isEmpty();
	}
	
	private void addListeners() {
		// Ecouteur pour changement d'état du controleur quand on change d'onglet 
		this.addChangeListener(this.controleur);
		
		// Ecouter sur les boutons d'ajout et de suppression
		for (int i = 0; i < NB_TABS; i++) {
			this.addBtn[i].addActionListener(controleur);
			this.deleteBtn[i].addActionListener(controleur);
			this.deleteUserFromGroupBtn[i].addActionListener(controleur);
			this.saveBtn[i].addActionListener(controleur);
		}
		
		// Ecouter le bouton d'ajout d'un membre à un groupe 
		this.ajoutUtilisateurGroupeOKBtn.addActionListener(controleur);
		
		// Ecoute les listes de gauche de chaque onglet (en sélectionnant)
		listeGaucheUsers.addListSelectionListener(controleur);
		listeGaucheGroups.addListSelectionListener(controleur);

	}
	
	public Object getSelectedMainElement() {
		switch(controleur.getPanestate()) {
			case GROUPES :
					return this.listeGaucheGroups.getSelectedValue();
			case UTILISATEURS :
					return this.listeGaucheUsers.getSelectedValue();
			default : 
					return null;
		}
	}
	
	public Object getSelectedSecondaryElement() {
		switch(controleur.getPanestate()) {
			case GROUPES :
					return this.listeDroiteGroups.getSelectedValue();
			case UTILISATEURS :
					return this.listeDroiteUsers.getSelectedValue();
			default : 
					return null;
		}
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
		deleteBtn[1] = new JButton("Supprimer");
		buttonsPanel.add(this.addBtn[1]);
		buttonsPanel.add(deleteBtn[1]);
		
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
		this.ajoutGroupeCB = new JComboBox<>();
		this.ajoutGroupeCB.setEnabled(false);
		this.ajoutUtilisateurGroupeOKBtn = new JButton("Ajouter");
		this.ajoutUtilisateurGroupeOKBtn.setEnabled(false);
		ajoutGroupeCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nomUserTF.getPreferredSize().height));
		
		// Liste des groupes auxquels appartient le membre sélectionné
		JLabel labelListeMembresGroupes = new JLabel("Liste des groupes");
		this.listeDroiteUsers = new JList<>(this.listeGroupesDeMembre);
		JScrollPane listeMembresScroll = new JScrollPane(listeDroiteUsers);
		this.deleteUserFromGroupBtn[1] = new JButton("Supprimer du groupe");
		
		// Bouton pour sauvegarder les modifications
		this.saveBtn[1] = new JButton("Sauvegarder les modifications");	
		this.saveBtn[1].setEnabled(false);
		
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
		box2.add(this.deleteUserFromGroupBtn[1]);
		
		// Bouton sauvegarder placé à droite
		Box box3 = new Box(BoxLayout.LINE_AXIS);
		box3.add(Box.createHorizontalGlue());
		box3.add(this.saveBtn[1]);
		
		// ComboBox et Bouton OK pour ajouter l'utilisateur sélectionné à un groupe
		Box box4 = new Box(BoxLayout.LINE_AXIS);
		box4.add(this.ajoutGroupeCB);
		box4.add(this.ajoutUtilisateurGroupeOKBtn);
		
		// Ajout de tous les éléments créés au panneau de droite
		JPanel partieDroite = new JPanel();
		partieDroite.setLayout(new BoxLayout(partieDroite, BoxLayout.PAGE_AXIS));
		partieDroite.add(nomprenomPanel);
		partieDroite.add(Box.createVerticalGlue());
		partieDroite.add(ajoutLabel);
		partieDroite.add(box4);
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
	this.deleteBtn[0] = new JButton("Supprimer");
	buttonsPanel.add(this.addBtn[0]);
	buttonsPanel.add(this.deleteBtn[0]);
	
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
	this.deleteUserFromGroupBtn[0] = new JButton("Supprimer du groupe");
	
	// Bouton pour sauvegarder les modifications
	this.saveBtn[0] = new JButton("Sauvegarder les modifications");	
	this.saveBtn[0].setEnabled(false);

	
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
	box2.add(this.deleteUserFromGroupBtn[0]);
	
	// Bouton sauvegarder placé à droite
	Box box3 = new Box(BoxLayout.LINE_AXIS);
	box3.add(Box.createHorizontalGlue());
	box3.add(this.saveBtn[0]);
	
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
