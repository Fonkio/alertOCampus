package utilisateur;

import java.util.Date;
import java.util.Map;

public class Message {
	private int idMessage;
	private String texte;
	private Date dCreation;
	private Utilisateur expediteur;
	private Map<Utilisateur, Status> etatDestinataire;
	
	public Message(int idMessage, String texte, Date dCreation, Utilisateur expediteur) {
		this.idMessage = idMessage;
		this.texte = texte;
		this.dCreation = dCreation;
		this.expediteur = expediteur;
	}

	public int getIdMessage() {
		return idMessage;
	}

	public String getTexte() {
		return texte;
	}

	public Date getdCreation() {
		return dCreation;
	}

	public Utilisateur getExpediteur() {
		return expediteur;
	}

	public Map<Utilisateur, Status> getEtatDestinataire() {
		return etatDestinataire;
	}
	
	
	
	
	
	
}
