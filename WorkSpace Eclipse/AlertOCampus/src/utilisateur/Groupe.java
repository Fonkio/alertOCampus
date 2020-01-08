package utilisateur;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Groupe implements Comparable<Groupe>, Serializable{
	private int idGroupe;
	private String libelle;
	private List<Utilisateur> listeUtilisateurs;
	
	
	
	public Groupe(int idGroupe, String libelle, List<Utilisateur> listeUtilisateurs) {
		super();
		this.idGroupe = idGroupe;
		this.libelle = libelle;
		this.listeUtilisateurs = listeUtilisateurs;
	}
	
	public List<Utilisateur> getListeUtilisateurs() {
		return listeUtilisateurs;
	}

	public int getIdGroupe() {
		return idGroupe;
	}
	public String getLibelle() {
		return libelle;
	}

	@Override
	public int compareTo(Groupe gtc) {
		return libelle.compareTo(gtc.libelle);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGroupe;
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((listeUtilisateurs == null) ? 0 : listeUtilisateurs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Groupe other = (Groupe) obj;
		if (idGroupe != other.idGroupe)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return libelle + "";
	}
	
	
	
	

	
}
