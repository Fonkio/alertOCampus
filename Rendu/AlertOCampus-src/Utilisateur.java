
import java.io.Serializable;

public class Utilisateur implements Comparable<Utilisateur>, Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String prenom;
	private String nom;
	
	public Utilisateur(int id, String prenom, String nom) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
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
		return prenom + " " + nom;
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
