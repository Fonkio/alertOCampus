package utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Serveur implements Runnable {
	private static final int PORT = 8952;
	Socket socket;
	ServerSocket server;
	DataBaseManager dbmanager;
	
	public Serveur(){
		try {
			server = new ServerSocket(PORT);
			dbmanager = new DataBaseManager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void run() {
		try{
			while(!server.isClosed()){
				socket = server.accept();
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							BufferedReader plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
							boolean socketOuvert = true;
							while (socketOuvert) {
								try{
									String input = plec.readLine();
									if(input != null)
									{
										output.println(parseMessage(input));
									}
								}catch(SocketException se){
									socketOuvert=false;
								}
							}
							socket.close();
						} catch (IOException e) {e.printStackTrace();}
					}
				});
				t.start();
			}
		}catch (IOException e) {e.printStackTrace();}
	}
	
	/* R�cup�re l'ensemble des groupes et construit une cha�ne r�ponse au client */
	private String responseGroups() {
		StringBuilder sb = new StringBuilder("GET groups response \n");
		List<Groupe> groups = dbmanager.getGroups(); 
		for(Groupe group : groups) {
			sb.append(group.getIdGroupe() + " " + group.getLibelle() + "\n");
		}
		return sb.toString();
	}
	
	/* R�cup�re les membres du groupe identifi� par son id
	 * La requ�te doit �tre de la forme "GET members <id>"
	 */
	private String responseMembers(String message) {
		String[] msgSplit = message.split("\\s+");
		int id = Integer.parseInt(msgSplit[2]);
		NavigableSet<Utilisateur> members = dbmanager.getGroupMembers(id);
		StringBuilder sb = new StringBuilder(message + "\n");
		for(Utilisateur user : members) {
			sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
		}
		return sb.toString();
	}
	
	/*
	 * Envoie les informations relatives � l'utilisateur demand�
	 * Requ�te de la forme : "GET user with login <login>" OU
	 *  "GET user with id <id>"
	 *  Retourne la r�ponse sous la forme 
	 *  "<requ�te>
	 *  <id> <nom> <pr�nom>"
	 */
	private String responseUser(String message) {
		StringBuilder sb = new StringBuilder(message + "\n");
		// D�composer la requ�te
		String[] msgSplit = message.split("\\s+");
		// Savoir si on filtre par id ou par login de l'utilisateur
		String filtrage = msgSplit[3];
		if (filtrage.equals("id")) {
			int id = Integer.parseInt(msgSplit[4]);
			NavigableSet<Utilisateur> members = dbmanager.getGroupMembers(id);
			for(Utilisateur user : members) {
				sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
			}
		} else if (filtrage.equals("login")) {
			String login = msgSplit[4];
			Utilisateur user = dbmanager.getUser(login);
			sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
		}
		return sb.toString();
	}
	
	/* R�pond au client sur la validit� des identifiants de l'utilisateur 
	 * Requ�te de la forme "CONNECT <login> <mdp>"
	 * R�ponse de la forme 
	 * " <requ�te>
	 * OK " Si la connexion est autoris�e
	 * " <requ�te>
	 * NOT OK" Sinon
	 */
	private String responseToConnexion(String message) {
		StringBuilder sb = new StringBuilder(message + "\n");
		String[] msgSplit = message.split("\\s+");
		String login = msgSplit[1];
		String password = msgSplit[2];
		if (dbmanager.isPasswordValid(login, password)) {
			sb.append("Ok");
		} else {
			sb.append("NOT OK");
		}
		return sb.toString();
	}

	/* Construit une r�ponse adapt�es � chaque cas de message que le client peut envoyer */
	private String parseMessage(String message) {
		String response = null;
		if (message.startsWith("GET members")) {
			response = responseMembers(message);
		} else if (message.startsWith("GET user")) {
			response = responseUser(message);
		} else if (message.startsWith("CONNECT ")) {
			response = responseToConnexion(message);
		} else {
			response = "Message re�u : " + message ;	
		}
		System.out.println(response);
		return response;
	}
	
	public static void main(String[] args){
		Serveur s = new Serveur();
		Thread t = new Thread(s);
		t.start();
		s.run();
	}
}