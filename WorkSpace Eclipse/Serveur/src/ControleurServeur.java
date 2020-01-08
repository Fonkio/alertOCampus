import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.swing.JList;
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
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("a");

	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (! e.getValueIsAdjusting() ) {		
			
			switch (panestate) {
			case UTILISATEURS :
				JList<Utilisateur> users = (JList<Utilisateur>) e.getSource();
				Utilisateur currentUser = users.getSelectedValue();
				vue.setNomUserTF(currentUser.getNom());
				vue.setPrenomUserTF(currentUser.getPrenom());
				Set<Groupe> groupsOfUser = this.serveur.getGroupsOfUser(currentUser.getId());
				vue.resetGroupOfMemberList();
				for(Groupe group : groupsOfUser) {
					vue.addGroupOfMember(group);
				}
				break;
			case GROUPES :
				JList<Groupe> groups = (JList<Groupe>) e.getSource();
				Groupe currentGroup = groups.getSelectedValue();
				vue.setNomGroupeTF(currentGroup.getLibelle());
				Set<Utilisateur> members = this.serveur.getGroupMembers(currentGroup.getId());
				vue.resetMemberOfGroupList();
				for(Utilisateur user : members) {
					vue.addMemberOfGroup(user);
				}
				break;
			
			}
				
			
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
	}


}
