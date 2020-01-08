import java.util.List;

import javax.swing.JFrame;

public class ApplicationServeur {

	public static void main(String[] args) {
		ServeurModele serv = new ServeurModele();
		List<Utilisateur> users = serv.getUsers();
		System.out.println(users);
		List<Groupe> groups = serv.getGroups();
		System.out.println(groups);
		serv.removeUserFromGroup(users.get(0), groups.get(1));
		System.out.println(serv.getGroups());
		serv.addUserToGroup(users.get(0), groups.get(1));
		System.out.println(serv.getGroups());
		
		JFrame frame = new JFrame("Serveur AlertOCampus");
		VueServeur vue = new VueServeur();
	    frame.add(vue);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
