
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ModeleUtilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	private Utilisateur currentUser;
	private ControleurUtilisateur controleurUtilisateur;
	public ModeleUtilisateur(String user, String password, ControleurUtilisateur cu) {
		if (user.equals("") || password.equals("")) {
			System.out.println("CHAMP VIDE");
		} else {
			Client c = new Client();
			c.sendMessage("CONNECT "+user+ " "+ password);
			List<String> userStringLines;
			try {
				userStringLines = c.getResponse();
				
				if(!userStringLines.get(1).equals("NOT OK")) {
					String [] str = userStringLines.get(1).split("\\s+");
					currentUser = new Utilisateur(Integer.parseInt(str[0]), str[2], str[1]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		controleurUtilisateur = cu;
		
	}

	public void setCurrentUser(Utilisateur currentUser) {
		this.currentUser = currentUser;
	}

	public Utilisateur getCurrentUser() {
		return currentUser;
	}

	public void reconnexion(String user, String password) {
		if (user.equals("") || password.equals("")) {
			System.out.println("CHAMP VIDE");
		} else {
			Client c = new Client();
			c.sendMessage("CONNECT "+user+ " "+ password);
			List<String> userStringLines;
			try {
				userStringLines = c.getResponse();
				if(!userStringLines.get(1).equals("NOT OK")) {
					String [] str = userStringLines.get(1).split("\\s+");
					currentUser = new Utilisateur(Integer.parseInt(str[0]), str[2], str[1]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}		
	}

	public List<Groupe> getListeGroupe() {
		Client c = new Client();
		c.sendMessage("GET groups");
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			
			
			System.out.println(groupsStringLines.toString());
			
			List<Groupe> listeGroupe = new ArrayList<Groupe>();
			for (int i = 1; i < groupsStringLines.size()-1; i++) {
				String [] str = groupsStringLines.get(i).split("\\s+");
				
				c.sendMessage("GET members "+str[0]);
				List<String> userStringLines;
				userStringLines = c.getResponse();
				System.out.println(userStringLines.toString());
				
				List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
				for (int j = 1; j < userStringLines.size()-1; j++) {
					String [] stru = userStringLines.get(j).split("\\s+");
					
					listeUtilisateur.add(new Utilisateur(Integer.parseInt(stru[0]), stru[1], stru[2]));
				}
				listeGroupe.add(new Groupe(Integer.parseInt(str[0]), str[1], listeUtilisateur));
			}
			
			return listeGroupe;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void envoyerFil(Fil f) {
		Client c = new Client();
		c.sendMessage("NEW fil "+f.getTitre().replace(" ", "£")+" "+f.getCreateur().getId()+" "+f.getDestination().getIdGroupe());
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			
			System.out.println(groupsStringLines.toString());
			
			f.setIdFil(Integer.parseInt(groupsStringLines.get(1)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public void envoyerMessage(Message m, Fil f) {
		Client c = new Client();
		c.sendMessage("NEW message "+m.getTexte().replace(" ", "£")+" "+m.getExpediteur().getId()+" "+f.getIdFil()+" "+ m.getdCreation().getTime());
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			System.out.println(groupsStringLines.toString());
			
			m.setIdMessage(Integer.parseInt(groupsStringLines.get(1)));
			m.setSend(true);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setStatus(Message m, Utilisateur u) {
		Client c = new Client();
		c.sendMessage("GET status "+m.getIdMessage()+" "+u.getId());
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			System.out.println(groupsStringLines.toString());
			switch (groupsStringLines.get(1)) {
				case "EN_ATTENTE":
					m.getEtatDestinataire().put(u, Status.EN_ATTENTE);
					break;
				case "LU":
					m.getEtatDestinataire().put(u, Status.LU);
					break;
				case "RECU":
					m.getEtatDestinataire().put(u, Status.RECU);
					break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void lireFil(Fil f) {
		Client c = new Client();
		c.sendMessage("LIRE "+f.getIdFil()+" "+currentUser.getId());
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			System.out.println(groupsStringLines.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Utilisateur getUser(int id) {
		Client c = new Client();
		c.sendMessage("GET user with id " + id);
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
			String [] stru = groupsStringLines.get(1).split("\\s+");
			System.out.println(groupsStringLines.toString());
			return new Utilisateur(id, stru[2], stru[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void nouveauFilMessage() {
		List<Message> lm = new ArrayList<Message>();
		Client c = new Client();
		c.sendMessage("GET filsmessages "+currentUser.getId());
		List<String> groupsStringLines;
		try {
			groupsStringLines = c.getResponse();
		
			System.out.println(groupsStringLines.toString());
			for (int i = 1; i < groupsStringLines.size()-1; i++) {
				String [] stru = groupsStringLines.get(i).split("\\s+");
				if (stru[0].equals("M")) {
					int idFil = Integer.parseInt(stru[4]);
					int idUtilisateur = Integer.parseInt(stru[5]);
					Fil charge = null;
					for (Iterator<Fil> it = controleurUtilisateur.getVueUtilisateur().getListeFils().iterator(); it.hasNext();) {
						Fil f = it.next();
						if (f.getIdFil() == idFil) {
							if (f.getCreateur().getId() == idUtilisateur) {
								Message ml = new Message(Integer.parseInt(stru[1]), stru[2].replace("£", " "), new Date(Long.parseLong(stru[3])), f.getCreateur(), f.getDestination().getListeUtilisateurs());
								ml.setSend(true);
								f.getMessages().add(ml);
								charge = f;
							} else {
								for (Utilisateur u : f.getDestination().getListeUtilisateurs()) {
									System.out.println("MESSAGE AJOUTE#1 "+u.getId()+" "+idUtilisateur );
									if (u.getId() ==  idUtilisateur) {
										System.out.println("MESSAGE AJOUTE");
										Message ml = new Message(Integer.parseInt(stru[1]), stru[2].replace("£", " "), new Date(Long.parseLong(stru[3])), u, f.getDestination().getListeUtilisateurs());
										ml.setSend(true);
										f.getMessages().add(ml);
										charge = f;
									}
								}
							}
							
							
						}
					}
					
					if (controleurUtilisateur.getVueUtilisateur().getSelectedFil() != null) {
						controleurUtilisateur.getVueUtilisateur().chargerFil(controleurUtilisateur.getVueUtilisateur().getSelectedFil());	
					}
					if (charge != null){
						controleurUtilisateur.getVueUtilisateur().updateFilOrder(charge);
					}
					
					controleurUtilisateur.getVueUtilisateur().updateArbre();
				} else {
					int idFil = Integer.parseInt(stru[1]);
					int idGroupe = Integer.parseInt(stru[4]);
					int idUtilisateur = Integer.parseInt(stru[3]);
					for (Groupe g : getListeGroupe()) {
						if (g.getIdGroupe() == idGroupe) {
							if (!controleurUtilisateur.getVueUtilisateur().getListeGroupes().contains(g)) {
								controleurUtilisateur.getVueUtilisateur().getListeGroupes().add(g);
							}
							controleurUtilisateur.getVueUtilisateur().getListeFils().add(new Fil(idFil, stru[2].replace("£", " "), g, getUser(idUtilisateur)));
							
						}
					}
					
				}
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public class Client {
		private static final long serialVersionUID = 1L;
		private static final int PORT = 8952;
		private Socket socket;
		private PrintWriter output;
		
		public Client() {
			try {
				socket = new Socket(InetAddress.getLocalHost(), PORT);
				output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void sendMessage(String msg){
			System.out.println("Message sent to server " + msg);
			output.println(msg);
			
		}
		
		public List<String> getResponse() throws IOException {
			
				BufferedReader plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input;
				List<String> ret = new ArrayList();
				do {
					 input = plec.readLine();
					 ret.add(input);
				} while (!input.equals("END"));
				//output.println("OUI" + input);
				return ret;
			
		
		}
		
		
		
	}
	
	
}
