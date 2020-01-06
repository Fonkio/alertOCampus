package utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Fil {
	
	@Override
	public String toString() {
		return titre + "";
	}

	private int idFil;
	public void setIdFil(int idFil) {
		this.idFil = idFil;
	}

	private String titre;
	private List<Message> messages = new ArrayList<>();
	private Groupe destination;
	private Utilisateur createur;
	
	public Fil(int idFil, String titre, Groupe destination, Utilisateur expediteur) {
		super();
		this.idFil = idFil;
		this.titre = titre;
		this.destination = destination;
		this.createur = expediteur;
	}

	public int getIdFil() {
		return idFil;
	}

	public String getTitre() {
		return titre;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Groupe getDestination() {
		return destination;
	}

	public Utilisateur getCreateur() {
		return createur;
	}
	
	
	
}
