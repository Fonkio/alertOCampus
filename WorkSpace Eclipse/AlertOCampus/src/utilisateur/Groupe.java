package utilisateur;

import java.util.List;
import java.util.Set;

public class Groupe implements Comparable<Groupe> {
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
	
	

	
}
