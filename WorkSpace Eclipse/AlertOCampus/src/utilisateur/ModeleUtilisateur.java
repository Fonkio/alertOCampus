package utilisateur;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ModeleUtilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	private Utilisateur currentUser;
	public ModeleUtilisateur(String user, String password) {
		if (user.equals("") || password.equals("")) {
			System.out.println("CHAMP VIDE");
		} else {
			Client c = new Client();
			c.sendMessage("CONNECT "+user+ " "+ password);
			List<String> userStringLines;
			userStringLines = c.getResponse();
			
			if(!userStringLines.get(1).equals("NOT OK")) {
				String [] str = userStringLines.get(1).split("\\s+");
				currentUser = new Utilisateur(Integer.parseInt(str[0]), str[2], str[1]);
			}
		}
		
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
			userStringLines = c.getResponse();
			
			if(!userStringLines.get(1).equals("NOT OK")) {
				String [] str = userStringLines.get(1).split("\\s+");
				currentUser = new Utilisateur(Integer.parseInt(str[0]), str[2], str[1]);
			}
		}		
	}

	public List<Groupe> getListeGroupe() {
		Client c = new Client();
		c.sendMessage("GET groups");
		List<String> groupsStringLines;
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
	}
	public void envoyerFil(Fil f) {
		Client c = new Client();
		c.sendMessage("NEW fil "+f.getTitre().replace(" ", "£")+" "+f.getCreateur().getId()+" "+f.getDestination().getIdGroupe());
		List<String> groupsStringLines;
		groupsStringLines = c.getResponse();
		System.out.println(groupsStringLines.toString());
		
		f.setIdFil(Integer.parseInt(groupsStringLines.get(1)));

	}
	//test
	private int j = 1;
	public void envoyerMessage(Message m, Fil f) {
		Client c = new Client();
		c.sendMessage("NEW message "+m.getTexte().replace(" ", "£")+" "+m.getExpediteur().getId()+" "+f.getIdFil()+" "+ m.getdCreation().getTime());
		List<String> groupsStringLines;
		groupsStringLines = c.getResponse();
		System.out.println(groupsStringLines.toString());
		
		m.setIdMessage(Integer.parseInt(groupsStringLines.get(1)));
		
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
		
		public List<String> getResponse() {
			try {
				BufferedReader plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input;
				List<String> ret = new ArrayList();
				do {
					 input = plec.readLine();
					 ret.add(input);
				} while (!input.equals("END"));
				//output.println("OUI" + input);
				return ret;
			} catch (IOException e) {
				e.printStackTrace();	 
			}
			return null;
		}
		
		
		
	}
	
	
}
