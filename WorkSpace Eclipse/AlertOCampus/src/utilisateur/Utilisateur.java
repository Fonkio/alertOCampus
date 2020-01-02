package utilisateur;

public class Utilisateur {
	private int idUtilisateur;
	private String nom;
	private String prenom;
	
	public int getIdUtilisateur() {
		return idUtilisateur;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	@Override
	public String toString() {
		return nom + " " + prenom;
	}
	public Utilisateur(int idUtilisateur, String nom, String prenom) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	
}
