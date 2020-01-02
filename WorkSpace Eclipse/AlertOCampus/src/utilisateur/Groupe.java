package utilisateur;

import java.util.Set;

public class Groupe {
	private int idGroupe;
	private String libelle;
	private Set<Utilisateur> listeUtilisateurs;
	
	
	
	public Groupe(int idGroupe, String libelle, Set<Utilisateur> listeUtilisateurs) {
		super();
		this.idGroupe = idGroupe;
		this.libelle = libelle;
		this.listeUtilisateurs = listeUtilisateurs;
	}
	
	public void setListeUtilisateurs(Set<Utilisateur> listeUtilisateurs) {
		this.listeUtilisateurs = listeUtilisateurs;
	}
	public int getIdGroupe() {
		return idGroupe;
	}
	public String getLibelle() {
		return libelle;
	}

	
}
