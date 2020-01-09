
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


public class ServeurModele {
	
	
	public Connection connectToDatabase() throws SQLException {
		return DriverManager.getConnection(
					"jdbc:mysql://localhost/alertocampus"
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root",
					"");
	}
	
	public List<Utilisateur> getUsers() {
		List<Utilisateur> users = new ArrayList<>();
		try {

			Connection con = this.connectToDatabase();
			Statement stmt =  con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT Id_Utilisateur, Nom, Prenom , Login , motDePasse FROM utilisateur");
			while(rst.next()) {
				String nom = rst.getString("Nom");
				String prenom = rst.getString("Prenom");
				String login = rst.getString("Login");
				String motDePasse = rst.getString("motDePasse");
				int id = rst.getInt("Id_Utilisateur");
				Utilisateur currentuser = new Utilisateur(id, prenom, nom, login, motDePasse);
				users.add(currentuser);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return users;
	}
	
	public Utilisateur getUser(int id) {
		Utilisateur user = null;
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur, Prenom, Nom, Login, MotDePasse FROM Utilisateur"
					+ " WHERE Id_Utilisateur = ?");
			stmt.setInt(1, id);
			ResultSet rst = stmt.executeQuery();
			while(rst.next()) {
				int idUser = rst.getInt("Id_Utilisateur");
				String nom = rst.getString("Nom");
				String prenom = rst.getString("Prenom");
				String login = rst.getString("Login");
				String mdp = rst.getString("motDePasse");
				user = new Utilisateur(idUser, prenom, nom, login, mdp);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	
	//Récupérer les membres d'un groupes triés dans l'ordre alphabétique
	public NavigableSet<Utilisateur> getGroupMembers(int id) {
		NavigableSet<Utilisateur> members = new TreeSet<>();
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt = con.prepareStatement("SELECT Id_Utilisateur FROM appartenir WHERE Id_Groupe = ?");
			stmt.setInt(1, id);	
			ResultSet rst = stmt.executeQuery();
			while(rst.next()) {
				int idUser = rst.getInt("Id_Utilisateur");
				Utilisateur currentUser = this.getUser(idUser);
				if (currentUser != null) { members.add(currentUser);}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return members;
	}
	
	public void updateUser(Utilisateur user) {
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt =  con.prepareStatement("UPDATE utilisateur SET Nom = ? , Prenom = ? , login = ? , motDePasse = ? WHERE Id_Utilisateur = ?")){
				stmt.setString(1, user.getNom());
				stmt.setString(2, user.getPrenom());
				stmt.setString(3, user.getLogin());
				stmt.setString(4, user.getMdp());
				stmt.setInt(5, user.getId());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
	
	public void updateGroup(Groupe group) {
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt =  con.prepareStatement("UPDATE groupe SET libelle = ?")){
				stmt.setString(1, group.getLibelle());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
	
	public NavigableSet<Groupe> getGroupsOfUser(int id) {
		NavigableSet<Groupe> groups = new TreeSet<>();
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt = con.prepareStatement("SELECT Id_Groupe FROM appartenir WHERE Id_Utilisateur = ?");){
			stmt.setInt(1, id);	
			try(ResultSet rst = stmt.executeQuery();) {
				while(rst.next())  {
					int idGroup = rst.getInt("Id_Groupe");
					Groupe currentGroup = this.getGroup(idGroup);
					if (currentGroup != null) { groups.add(currentGroup);}
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return groups;
	}
	
	private Groupe getGroup(int id) {
		Groupe group = null;
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt =  con.prepareStatement("SELECT Id_Groupe, Libelle FROM Groupe"
						+ " WHERE Id_Groupe = ?");){
			stmt.setInt(1, id);
			try(ResultSet rst = stmt.executeQuery();) {
				while(rst.next()) {
					int idGroup = rst.getInt("Id_Groupe");
					String libelle = rst.getString("Libelle");
					group = new Groupe(idGroup, libelle, new TreeSet<Utilisateur>());
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return group;
	}

	public List<Groupe> getGroups() {
		List<Groupe> groups = new ArrayList<>();
		try {
			Connection con = this.connectToDatabase();
			Statement stmt =  con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT Id_Groupe, Libelle from Groupe");
			while(rst.next()) {
				int id = rst.getInt("Id_Groupe");
				String libelle = rst.getString("Libelle");
				Groupe currentgroup = new Groupe(id, libelle, getGroupMembers(id));
				groups.add(currentgroup);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return groups;
	}
	
	public void removeUserFromGroup(Utilisateur user, Groupe group) {
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("DELETE FROM appartenir WHERE Id_Utilisateur = ? AND Id_Groupe = ?");
			stmt.setInt(1, user.getId());
			stmt.setInt(2, group.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void addUserToGroup(Utilisateur user, Groupe group) {
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("INSERT INTO appartenir VALUES ( ? , ? )");
			stmt.setInt(1, user.getId());
			stmt.setInt(2, group.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void deleteUser(Utilisateur user) {
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("DELETE FROM Utilisateur WHERE Id_Utilisateur = ?");
			stmt.setInt(1, user.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public Utilisateur addUser(String nom, String prenom) {
		Utilisateur user = null;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("INSERT INTO utilisateur(Nom, Prenom)  VALUES (? , ?)", Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				rs.last();
				user = new Utilisateur(rs.getInt(1), nom, prenom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	public Utilisateur addUser(String nom, String prenom, String login, String mdp) {
		Utilisateur user = null;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("INSERT INTO utilisateur(Nom, Prenom, Login, motDePasse)  VALUES (? , ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.setString(3, login);
			stmt.setString(4, mdp);
			stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				rs.last();
				user = new Utilisateur(rs.getInt(1), nom, prenom, login, mdp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	public void deleteGroup(Groupe group) {
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("DELETE FROM Groupe WHERE Id_Groupe = ?");
			stmt.setInt(1, group.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public Groupe addGroup (String libelle) {
		Groupe group = null;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("INSERT INTO Groupe(Libelle)  VALUES (?)", Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, libelle);
			stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				rs.last();
				group = new Groupe(rs.getInt(1), libelle, this.getGroupMembers(rs.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return group;
	}
	
	public boolean isMember(Groupe group, Utilisateur user) {
		boolean ismember = false;
		if (group != null && user != null) {
			try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur FROM appartenir WHERE id_Groupe = ? AND id_Utilisateur = ?")) {
			   stmt.setInt(1, group.getId());
			   stmt.setInt(2, user.getId());
			   try(ResultSet rst = stmt.executeQuery();) {
				   rst.last();
					ismember = rst.getRow() == 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return ismember;
	}
	
	public void deleteMemberFromGroup(Groupe group, Utilisateur user) {
		if (group != null && user != null) {
			try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("DELETE FROM appartenir WHERE id_Groupe = ? AND id_Utilisateur = ?")) {
			   stmt.setInt(1, group.getId());
			   stmt.setInt(2, user.getId());
			   stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
