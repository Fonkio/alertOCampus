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
	
	public void sendMessage(String message) {
		output.println(message);
		//TODO adapter le sendMessage en prenant en compte le nom du groupe 
	}
	
  //TODO créer fil
	
	public void closeConnection() {
		output.close();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

