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
import java.util.ArrayList;
import java.util.List;

public class ModeleUtilisateur implements Serializable {
	
	private Utilisateur currentUser;
	private static final int PORT = 8952;
	Socket socket;
	PrintWriter output;
	Client c = new Client();
	
	public ModeleUtilisateur(String user, String password) {
		currentUser = new Utilisateur(1, "FABRE", "Maxime"); //Ligne pour test
		if (user.equals("") || password.equals("")) {
			System.out.println("CHAMP VIDE");
		} else {
			c.sendMessage("CONNECT "+user+ " "+ password);
			String userString;
			do {
				userString = c.getResponse();
				userString = c.getResponse();
			} while (userString == null);
			System.out.println(userString);
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
			c.sendMessage("CONNECT "+user+ " "+ password);
			String userString;
			do {
				userString = c.getResponse();
				String tuserString = c.getResponse();
				System.out.println("s");
			} while (userString == null);
			System.out.println("s" +userString);
		}		
	}

	public List<Groupe> getListeGroupe() {
		//Pour Tester
		Utilisateur u1 = new Utilisateur(1, "FABRE", "Maxime");
		Utilisateur u2 = new Utilisateur(2, "LOUAHADJ", "Inès");
		Utilisateur u3 = new Utilisateur(3, "SALVAGNAC", "Maxime");
		
		List<Utilisateur> tda3 = new ArrayList<Utilisateur>();
		List<Utilisateur> tda4 = new ArrayList<Utilisateur>();
		
		tda3.add(u1);
		tda3.add(u2);
		tda4.add(u3);
		
		List<Groupe> listeTest = new ArrayList<Groupe>();
		listeTest.add(new Groupe(1, "TDA3", tda3));
		listeTest.add(new Groupe(2, "TDA4", tda4));
		return listeTest;
	}
	//test
	private int i = 1;
	public void envoyerFil(Fil f) {
		f.setIdFil(i);
		i ++;
	}
	//test
	private int j = 1;
	public void envoyerMessage(Message m, Fil f) {
		m.setIdMessage(j);
		j++;
		
	}
	
	
	public class Client implements Serializable{
		private static final int PORT = 8952;
		Socket socket;
		PrintWriter output;
		
		public Client() {
			try {
				socket = new Socket(InetAddress.getLocalHost(), PORT);
				output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			} catch (IOException e) {
				e.printStackTrace();	 
			}
		}
		
		public void sendMessage(String msg){
			System.out.println("Message sent to server " + msg);
			output.println(msg);
		}
		
		public String getResponse() {
			try(BufferedReader plec = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				String input = plec.readLine();
				//output.println("OUI" + input);
				return input;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		
	}
	
	
}
