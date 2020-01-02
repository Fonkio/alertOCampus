package utilisateur;

import java.util.Set;
import java.util.TreeSet;

public class Fil {
	private int idFil;
	private String titre;
	private Set<Message> messages;
	private Groupe destination;
	private Utilisateur expediteur;
	
	public Fil(int idFil, String titre, Groupe destination, Utilisateur expediteur) {
		super();
		this.idFil = idFil;
		this.titre = titre;
		this.destination = destination;
		this.expediteur = expediteur;
	}

	public int getIdFil() {
		return idFil;
	}

	public String getTitre() {
		return titre;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public Groupe getDestination() {
		return destination;
	}

	public Utilisateur getExpediteur() {
		return expediteur;
	}
	
	
	
}
