package utilisateur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class ControleurUtilisateur implements Serializable, ActionListener, TreeSelectionListener{
	private static final long serialVersionUID = 1L;
	private VueUtilisateur vueUtilisateur;
	private ModeleUtilisateur modeleUtilisateur;

	public ControleurUtilisateur(VueUtilisateur vu) {
		vueUtilisateur = vu;
		
		String[] userPwd = vu.auth();
		if (userPwd == null) {
			System.exit(0);
		}
		
		modeleUtilisateur = new ModeleUtilisateur(userPwd[0], userPwd[1], this);
		
		while (modeleUtilisateur.getCurrentUser() == null) {
			vu.erreurAuth();
			userPwd = vu.auth();
			if (userPwd == null) {
				System.exit(0);
			}		
			modeleUtilisateur.reconnexion(userPwd[0], userPwd[1]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //EVENT CLIC BOUTON
		if (((JButton)e.getSource()).getText().equals("Nouveau Fil")) {
			vueUtilisateur.nouveauFil();
		}
		if (((JButton)e.getSource()).getText().equals("Envoyer")) {
			//SI UN FIL EST SELECTIONNE
			if(vueUtilisateur.getSelectedFil() != null) {
				Message nvMessage = new Message(0, vueUtilisateur.getTextArea(), new Date(), modeleUtilisateur.getCurrentUser(), vueUtilisateur.getSelectedFil().getDestination().getListeUtilisateurs());
				vueUtilisateur.nouveauMessage(nvMessage);
			}
			
		}
		
	}

	public VueUtilisateur getVueUtilisateur() {
		return vueUtilisateur;
	}

	public ModeleUtilisateur getModeleUtilisateur() {
		return modeleUtilisateur;
	}

	public Groupe[] getTableGroupe() {
		//RECUPECATION LISTE GROUPE
		List<Groupe> lg = new ArrayList<>();
		lg = modeleUtilisateur.getListeGroupe();
		//TRANSFORMATION EN TABLEAU POUR LE COMBO
		Groupe[] tabGroupe = new Groupe[lg.size()];
		for (int i = 0; i < lg.size(); i++) {
			tabGroupe[i] = lg.get(i);
		}
		return tabGroupe;
	}

	public void envoyerFil(Fil f) {
		modeleUtilisateur.envoyerFil(f);
	}

	public void envoyerMessage(Message m, Fil f) {
		modeleUtilisateur.envoyerMessage(m, f);
		
	}
	public void nouveauFilMessage() {
		modeleUtilisateur.nouveauFilMessage();
		
	}
	public void nouveauFil() {
		
	}
	public Set<Fil> getFil() {
		return vueUtilisateur.getListeFils();
	}
	

	@Override
	public void valueChanged(TreeSelectionEvent e) { //EVENT ARBRE CHANGE SELECTION
		System.out.println("[EVENEMNT] Arbre : sélection modifiée !");
		//RECUP DU NOEUD SELECTION
		DefaultMutableTreeNode noeud = (DefaultMutableTreeNode)vueUtilisateur.getArbreTickets().getLastSelectedPathComponent();
		//SI LE NOEUD EST UNE FEUILLE
		if (noeud != null && noeud.isLeaf()) {
			//SI LE NOEUD EST UN FIL
			if (noeud.getUserObject() instanceof Fil) {
				Fil f = (Fil)noeud.getUserObject();
				vueUtilisateur.chargerFil(f);
				modeleUtilisateur.lireFil(f);
			}
				
			
		}
		
	}
	

}
