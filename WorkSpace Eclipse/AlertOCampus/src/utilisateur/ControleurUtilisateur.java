package utilisateur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
			List<Groupe> lg = new ArrayList<>();
			Fil newFil = vueUtilisateur.nouveauFil(lg);
		}
		
	}

	public VueUtilisateur getVueUtilisateur() {
		return vueUtilisateur;
	}

	public ModeleUtilisateur getModeleUtilisateur() {
		return modeleUtilisateur;
	}
	

}
