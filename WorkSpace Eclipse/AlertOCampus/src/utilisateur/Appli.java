package utilisateur;

public class Appli {
	public static void main(String[] args) {
		// new Fenetre();
		Client client = new Client();
		client.sendMessage("GET members 1");
		client.sendMessage("GET user 3");
		client.sendMessage("GET login slm");
		client.sendMessage("CONNECT slm pinpix");
		client.closeConnection();


		
	}
}
