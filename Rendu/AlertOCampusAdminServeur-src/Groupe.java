import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Groupe implements Comparable<Groupe>{
	private int id;
	private String libelle;
	private NavigableSet<Utilisateur> membres;
	
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
			return this.libelle.equals(groupToCompare.libelle) && this.id == groupToCompare.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * (id + libelle.hashCode());
	}

	@Override
	public String toString() {
		return this.libelle;
	}

	@Override
	public int compareTo(Groupe o) {
		int comparaison = this.libelle.compareTo(o.libelle);
		if (comparaison == 0) {
			return ((Integer) this.id).compareTo((Integer) o.id);
		}
		return comparaison;
	}
	
	
}
