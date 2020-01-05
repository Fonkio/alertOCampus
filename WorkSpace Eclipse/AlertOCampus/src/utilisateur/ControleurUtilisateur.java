package utilisateur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;

public class ControleurUtilisateur implements Serializable, ActionListener{
	private VueUtilisateur vueUtilisateur;
	private ModeleUtilisateur modeleUtilisateur;

	public ControleurUtilisateur(VueUtilisateur vu) {
		vueUtilisateur = vu;
		
		String[] userPwd = vu.auth();
		if (userPwd == null) {
			System.exit(0);
		}
		
		modeleUtilisateur = new ModeleUtilisateur(userPwd[0], userPwd[1]);
		
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
	public void actionPerformed(ActionEvent e) {
		if (((JButton)e.getSource()).getText().equals("Nouveau Fil")) {
			vueUtilisateur.nouveauFil();
		}
		
	}

	public VueUtilisateur getVueUtilisateur() {
		return vueUtilisateur;
	}

	public ModeleUtilisateur getModeleUtilisateur() {
		return modeleUtilisateur;
	}

	public Groupe getListeGroupe() {
		// TODO Auto-generated method stub
		return null;
	}

	public Groupe[] getTableGroupe() {
		List<Groupe> lg = new ArrayList<>();
		lg = modeleUtilisateur.getListeGroupe();
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
	

}
