import java.util.List;

public class ApplicationServeur {

	public static void main(String[] args) {
		Serveur serv = new Serveur();
		List<Utilisateur> users = serv.getUsers();
		System.out.println(users);
		List<Groupe> groups = serv.getGroups();
		System.out.println(groups);
		serv.removeUserFromGroup(users.get(0), groups.get(1));
		System.out.println(serv.getGroups());
		serv.addUserToGroup(users.get(0), groups.get(1));
		System.out.println(serv.getGroups());
	}

}
