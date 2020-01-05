import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Groupe {
	private int id;
	private String libelle;
	private Set<Utilisateur> membres;
	
	public Groupe(int id, String libelle, Set<Utilisateur> membres) {
		this.id = id;
		this.libelle = libelle;
		this.membres = new TreeSet<>();
		this.membres.addAll(membres);
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getId() {
		return id;
	}

	public Set<Utilisateur> getMembres() {
		return membres;
	}
	
	public void addUser(Utilisateur user) {
		membres.add(user);
	}
	
	public void removeUser(Utilisateur user) {
		membres.remove(user);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Groupe) {
			Groupe groupToCompare = (Groupe) o;
			return groupToCompare.id == id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public String toString() {
		return "Groupe [id=" + id + ", libelle=" + libelle + ", membres=" + membres + "]";
	}
	
	
}
