package utilisateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
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
	
	
	public void closeConnection() {
		output.close();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

