package utilisateur;

import java.util.ArrayList;
import java.util.List;

public class ModeleUtilisateur {
	private Utilisateur currentUser;

	public ModeleUtilisateur(String user, String passworld) {
		//Pour tester
		currentUser = new Utilisateur(1, "FABRE", "Maxime");
	}

	public void setCurrentUser(Utilisateur currentUser) {
		this.currentUser = currentUser;
	}

	public Utilisateur getCurrentUser() {
		return currentUser;
	}

	public void reconnexion(String utilisateur, String motDePasse) {
		// TODO Auto-generated method stub
		
	}

	public List<Groupe> getListeGroupe() {
		//Pour Tester
		Utilisateur u1 = new Utilisateur(1, "FABRE", "Maxime");
		Utilisateur u2 = new Utilisateur(2, "LOUAHADJ", "Inès");
		Utilisateur u3 = new Utilisateur(3, "SALVAGNAC", "Maxime");
		
		List<Utilisateur> tda3 = new ArrayList<Utilisateur>();
		List<Utilisateur> tda4 = new ArrayList<Utilisateur>();
		
		tda3.add(u1);
		tda3.add(u2);
		tda4.add(u3);
		
		List<Groupe> listeTest = new ArrayList<Groupe>();
		listeTest.add(new Groupe(1, "TDA3", tda3));
		listeTest.add(new Groupe(2, "TDA4", tda4));
		return listeTest;
	}
	//test
	private int i = 1;
	public void envoyerFil(Fil f) {
		f.setIdFil(i);
		i ++;
	}
	//test
	private int j = 1;
	public void envoyerMessage(Message m, Fil f) {
		m.setIdMessage(j);
		j++;
		
	}
	
	
}
