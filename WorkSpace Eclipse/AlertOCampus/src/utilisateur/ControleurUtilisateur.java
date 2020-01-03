package utilisateur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class ControleurUtilisateur implements Serializable, ActionListener{
	private VueUtilisateur vueUtilisateur;
	private ModeleUtilisateur modeleUtilisateur;

	public ControleurUtilisateur(VueUtilisateur vu) {
		vueUtilisateur = vu;
		modeleUtilisateur = new ModeleUtilisateur(vu.utilisateur(), vu.motDePasse());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
