package utilisateur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Fil implements Serializable{
	
	

	

	private int idFil;
	private String titre;
	private NavigableSet<Message> messages = new TreeSet<>();
	private Groupe destination;
	private Utilisateur createur;
	
	public Fil(int idFil, String titre, Groupe destination, Utilisateur expediteur) {
		super();
		this.idFil = idFil;
		this.titre = titre;
		this.destination = destination;
		this.createur = expediteur;
	}
	
	public void setIdFil(int idFil) {
		this.idFil = idFil;
	}

	public int getIdFil() {
		return idFil;
	}

	public String getTitre() {
		return titre;
	}

	public NavigableSet<Message> getMessages() {
		return messages;
	}

	public Groupe getDestination() {
		return destination;
	}

	public Utilisateur getCreateur() {
		return createur;
	}
	
	@Override
	public String toString() {
		return titre + "";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFil;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Fil other = (Fil) obj;
		if (idFil != other.idFil)
			return false;
		return true;
	}
	
	
}
