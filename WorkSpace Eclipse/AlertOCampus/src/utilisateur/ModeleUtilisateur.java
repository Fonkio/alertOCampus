package utilisateur;

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
	
	
}
