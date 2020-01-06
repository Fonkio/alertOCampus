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
							boolean socketOuvert = true;
							while (socketOuvert) {
								try{
									String input = plec.readLine();
									if(input != null)
									{
										parseMessage(input);
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
	
	private String messageGroups() {
		StringBuilder sb = new StringBuilder("GET groups : \n");
		List<Groupe> groups = dbmanager.getGroups();
		for(Groupe group : groups) {
			sb.append(group.getIdGroupe() + " " + group.getLibelle() + "\n");
		}
		return sb.toString();
	}
	

	private String parseMessage(String message) {
		if (message.startsWith("GET members")) {
			String[] msgSplit = message.split("\\s+");
			int id = Integer.parseInt(msgSplit[2]);
			NavigableSet<Utilisateur> members = dbmanager.getGroupMembers(id);
			StringBuilder sb = new StringBuilder("GET members : \n");
			for(Utilisateur user : members) {
				sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
			}
			System.out.println(sb.toString());
			return sb.toString();
		}
		 
		if (message.startsWith("GET user")) {
			String[] msgSplit = message.split("\\s+");
			int id = Integer.parseInt(msgSplit[2]);
			Utilisateur user = this.getUser(id);
			StringBuilder sb = new StringBuilder("GET user : \n");
				sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
			System.out.println(sb.toString());
			return sb.toString();
		}
		
		if (message.startsWith("CONNECT ")) {
			String[] msgSplit = message.split("\\s+");
			String login = msgSplit[1];
			String password = msgSplit[2];
			StringBuilder sb = new StringBuilder("CONNECT " + login +  "\n");
			if (isPasswordValid(login, password)) {
				sb.append("Ok");
			} else {
				sb.append("Not OK");
			}
			System.out.println(sb.toString());
			return sb.toString();
		}
		
		
		if (message.startsWith("GET login")) {
			String[] msgSplit = message.split("\\s+");
			String login = msgSplit[2];
			Utilisateur user = this.getUser(login);
			StringBuilder sb = new StringBuilder("GET user : \n");
			sb.append(user.getId() + " " + user.getNom() + " " + user.getPrenom() +  "\n");
			System.out.println(sb.toString());
			return sb.toString();
		}
		
		
		switch(message) {
		case "GET groups" :
				return messageGroups();
		default :
					System.out.println("Message reçu : " + message);	
					return message;
					
		}
		server.
	}
	
	public static void main(String[] args){
		Serveur s = new Serveur();
		Thread t = new Thread(s);
		t.start();
		s.run();
	}
}
