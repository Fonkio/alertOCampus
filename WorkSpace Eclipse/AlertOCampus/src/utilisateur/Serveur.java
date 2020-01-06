package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Serveur implements Runnable {
	private static final int PORT = 8952;
	Socket socket;
	ServerSocket server;
	//TODO ajouter BDD 
	
	public Serveur(){
		try {
			server = new ServerSocket(PORT);
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
										System.out.println("Message reçu : "+input);	
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
		}catch (IOException e){e.printStackTrace();}
	}
	
	public static void main(String[] args){
		Serveur c = new Serveur();
		Thread t = new Thread(c);
		t.start();
	}
}
