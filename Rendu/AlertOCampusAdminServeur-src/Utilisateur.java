
public class Utilisateur implements Comparable<Utilisateur> {
	private int id;
	private String prenom;
	private String nom;
	private String login;
	private String mdp;
	
	public Utilisateur(int id, String prenom, String nom, String login, String mdp) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.login = login;
		this.mdp = mdp;
	}
	
	public Utilisateur(int id, String prenom, String nom) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Utilisateur) {
			Utilisateur userToCompare = (Utilisateur) o;
			return userToCompare.id == id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public String toString() {
		return this.nom + " " + this.prenom;
	}

	@Override
	public int compareTo(Utilisateur userToCompare) {
		int comparaisonNom = nom.compareTo(userToCompare.nom);
		if (comparaisonNom == 0) {
			return prenom.compareTo(userToCompare.prenom);
		} 
		return comparaisonNom;
	}
	
	
}
