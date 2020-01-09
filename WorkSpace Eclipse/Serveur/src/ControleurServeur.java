import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public  class ControleurServeur implements ActionListener, Serializable, ListSelectionListener, ChangeListener {

	private static final long serialVersionUID = -1668949420193198587L;
	private VueServeur vue;
	private ServeurModele serveur;
	private PaneState panestate;
	private final Utilisateur defaultUser = new Utilisateur(0, "utilisateur", "Nouvel");
	private final Groupe defaultGroup = new Groupe(0, "Nouveau groupe", new TreeSet<>());
	
	
	public PaneState getPanestate() {
		return panestate;
	}

	public ControleurServeur(VueServeur vue) {
		this.vue = vue;
		this.serveur = new ServeurModele();
		this.panestate = PaneState.GROUPES;
		afficherListeUtilisateurs();
		afficherListeGroupes();
	}
	
	public void afficherListeUtilisateurs() {
		List<Utilisateur> users = serveur.getUsers();
		for (Utilisateur user : users) {
			vue.addUser(user);
		}
	}
	
	public void afficherListeGroupes() {
		List<Groupe> groups = serveur.getGroups();
		for (Groupe group : groups) {
			vue.addGroup(group);
			vue.addGroupeToComboBox(group);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("a");
		JButton btn = (JButton) e.getSource();
		String btnText = btn.getText();
		switch(btnText) {
		case "Supprimer" :
				deleteSelectionFromMainList();
			break;
		case "Supprimer du groupe" :
				deleteSelectionFromSecondaryList();
			break;
				
		case "Nouveau groupe":
			addGroup();
			break;
		case "Nouvel utilisateur":
			addUser();
			break;
		case "Ajouter" :
			addUserToGroup();
			break;
		case "Sauvegarder les modifications" :
			this.sauvegarder();
		}

	}

	private void addUser() {
		this.vue.listeUsers.addElement(this.defaultUser);
		int lastElementIndex = this.vue.listeUsers.getSize() - 1;
		this.vue.listeGaucheUsers.setSelectedIndex(lastElementIndex);
		this.vue.resetFormUser();
		this.vue.addBtn[1].setEnabled(false);
		this.vue.ajoutGroupeCB.setEnabled(false);
		this.vue.deleteUserFromGroupBtn[1].setEnabled(false);
		this.vue.ajoutUtilisateurGroupeOKBtn.setEnabled(false);
	}
	
	private void addGroup() {
		this.vue.listeGroupes.addElement(this.defaultGroup);
		int lastElementIndex = this.vue.listeGroupes.getSize() - 1;
		this.vue.listeGaucheGroups.setSelectedIndex(lastElementIndex);
		this.vue.resetFormGroup();
		this.vue.addBtn[0].setEnabled(false);
		this.vue.deleteUserFromGroupBtn[0].setEnabled(false);
	}
	
	
	private void sauvegarder() {
		switch(panestate) {
		case GROUPES :
			if (this.vue.listeGaucheGroups.getSelectedValue().equals(this.defaultGroup)) {
				
				if (this.vue.areGroupTFEmpty()) {
					int input = JOptionPane.showConfirmDialog(this.vue, "Vous n'avez entré aucune information, voulez-vous continuer ?", "Erreur", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(input == 0) {
						this.vue.listeGroupes.removeElement(this.defaultGroup);
						this.vue.disableSelectionBtn();
						this.vue.resetFormGroup();
						this.vue.addBtn[0].setEnabled(true);
						
					}
				
				} else {
					Groupe group = this.serveur.addGroup(this.vue.nomGroupeTF.getText());
					this.vue.listeGroupes.removeElement(this.defaultGroup);
					this.vue.listeGroupes.addElement(group);
					this.vue.disableSelectionBtn();
					this.vue.addBtn[0].setEnabled(true);
		
				}
				
			} else {
				Groupe selectedGroup = this.vue.listeGaucheGroups.getSelectedValue();
				String newLibelle = this.vue.nomGroupeTF.getText();
					if (!(newLibelle.equals(selectedGroup.getLibelle()) )) {
						selectedGroup.setLibelle(newLibelle);
						this.serveur.updateGroup(selectedGroup);
						this.vue.listeGaucheGroups.repaint();
					}
			}
			break;
			
			
		case UTILISATEURS :
			if (this.vue.listeGaucheUsers.getSelectedValue().equals(this.defaultUser)) {
				
				if (this.vue.areUserTFEmpty()) {
					int input = JOptionPane.showConfirmDialog(this.vue, "Vous n'avez entré aucune information, voulez-vous continuer ?", "Erreur", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(input == 0) {
						this.vue.listeUsers.removeElement(this.defaultUser);
						this.vue.disableSelectionBtn();
						this.vue.resetFormUser();
						this.vue.addBtn[1].setEnabled(true);
						
					}
				
				} else {
					Utilisateur user = this.serveur.addUser(this.vue.nomUserTF.getText(), this.vue.prenomUserTF.getText());
					this.vue.listeUsers.removeElement(this.defaultUser);
					this.vue.listeUsers.addElement(user);
					this.vue.disableSelectionBtn();
					this.vue.addBtn[1].setEnabled(true);
		
				}
				
			} else {
				Utilisateur selectedUser = this.vue.listeGaucheUsers.getSelectedValue();
				String newNom = this.vue.nomUserTF.getText();
				String newPrenom = this.vue.prenomUserTF.getText();
					if (!(newNom.equals(selectedUser.getNom()) && newPrenom.equals(selectedUser.getPrenom()))) {
						selectedUser.setNom(newNom);
						selectedUser.setPrenom(newPrenom);
						this.serveur.updateUser(selectedUser);
						this.vue.listeGaucheUsers.repaint();
					}
			}
	
		}
	}
	
	
	
	private void addUserToGroup() {
		Groupe selectedGroup = (Groupe) this.vue.ajoutGroupeCB.getSelectedItem();
		Utilisateur selectedUser = (Utilisateur) vue.getSelectedMainElement();
		if (! this.serveur.isMember(selectedGroup, selectedUser)) {
			this.serveur.addUserToGroup(selectedUser, selectedGroup);
			this.vue.addGroupOfMember(selectedGroup);
		}
	}
	
	private void deleteSelectionFromSecondaryList() {
		switch(panestate) {
			case UTILISATEURS :
				Utilisateur selectedMainUser = (Utilisateur) vue.getSelectedMainElement();
				Groupe selectedSecondaryGroup = (Groupe) vue.getSelectedSecondaryElement();
				this.serveur.deleteMemberFromGroup(selectedSecondaryGroup, selectedMainUser);
				this.vue.listeGroupesDeMembre.removeElement(selectedSecondaryGroup);
				break;
			case GROUPES :
				Groupe selectedMainGroup = (Groupe) vue.getSelectedMainElement();
				Utilisateur selectedSecondaryUser = (Utilisateur) vue.getSelectedSecondaryElement();
				this.serveur.deleteMemberFromGroup(selectedMainGroup, selectedSecondaryUser);
				this.vue.listeMembresDeGroupe.removeElement(selectedSecondaryUser);
				break;
		}
	}

	private void deleteSelectionFromMainList() {
		switch(panestate) {
			case UTILISATEURS :
				Utilisateur selectedUser = (Utilisateur) vue.getSelectedMainElement();
				if (selectedUser != null) {
					this.serveur.deleteUser(selectedUser);
					this.vue.listeUsers.removeElement(selectedUser);
					this.vue.resetFormUser();
					this.vue.disableSelectionBtn();
					this.vue.addBtn[1].setEnabled(true);
				}
				break;
			case GROUPES :
				Groupe selectedGroup = (Groupe) vue.getSelectedMainElement();
				if (selectedGroup != null) {
					this.serveur.deleteGroup(selectedGroup);
					this.vue.listeGroupes.removeElement(selectedGroup);
					this.vue.ajoutGroupeCB.removeItem(selectedGroup);
					this.vue.resetFormGroup();
					this.vue.disableSelectionBtn();
					this.vue.addBtn[0].setEnabled(true);
				}
				break;
		}

		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (! e.getValueIsAdjusting() ) {		
			switch (panestate) {
			case UTILISATEURS :
				
				JList<Utilisateur> users = (JList<Utilisateur>) e.getSource();
				Utilisateur currentUser = users.getSelectedValue();
				if (currentUser != null) {
					if (! currentUser.equals(this.defaultUser)) {
						this.vue.listeUsers.removeElement(this.defaultUser);
					}
					vue.setNomUserTF(currentUser.getNom());
					vue.setPrenomUserTF(currentUser.getPrenom());
					Set<Groupe> groupsOfUser = this.serveur.getGroupsOfUser(currentUser.getId());
					vue.resetGroupOfMemberList();
					for(Groupe group : groupsOfUser) {
						vue.addGroupOfMember(group);
					}
				}	
				this.vue.addBtn[1].setEnabled(true);
				break;
			case GROUPES :
				JList<Groupe> groups = (JList<Groupe>) e.getSource();
				Groupe currentGroup = groups.getSelectedValue();
				if (currentGroup != null) {
					if (! currentGroup.equals(this.defaultGroup)) {
						this.vue.listeGroupes.removeElement(this.defaultGroup);
					}
					vue.setNomGroupeTF(currentGroup.getLibelle());
					Set<Utilisateur> members = this.serveur.getGroupMembers(currentGroup.getId());
					vue.resetMemberOfGroupList();
					for(Utilisateur user : members) {
						vue.addMemberOfGroup(user);
					}
				}
				this.vue.addBtn[0].setEnabled(true);
			
			}
		this.vue.enableSelectionBtn();
		
			
		}
	}

	private void changePaneState() {
		if (this.panestate == PaneState.GROUPES) {
			this.panestate = PaneState.UTILISATEURS;
		} else {
			this.panestate = PaneState.GROUPES;
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		changePaneState();
		this.vue.disableSelectionBtn();
	}


}
