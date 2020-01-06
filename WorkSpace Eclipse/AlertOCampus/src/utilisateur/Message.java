package utilisateur;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Message implements Comparable<Message> {
	private int idMessage;
	private String texte;
	private Date dCreation;
	private Utilisateur expediteur;
	private Map<Utilisateur, Status> etatDestinataire = new HashMap<Utilisateur, Status>();
	private boolean send = false;
	
	public Message(int idMessage, String texte, Date dCreation, Utilisateur expediteur, List<Utilisateur> destinataires) {
		this.idMessage = idMessage;
		this.texte = texte;
		this.dCreation = dCreation;
		this.expediteur = expediteur;
		for(Utilisateur u : destinataires) {
			etatDestinataire.put(u, Status.EN_ATTENTE);
		}
	}
	
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
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

	public boolean isSend() {
		
		return send ;
	}

	@Override
	public int compareTo(Message m) {
		return getdCreation().compareTo(m.getdCreation());
	}
	
	
}
