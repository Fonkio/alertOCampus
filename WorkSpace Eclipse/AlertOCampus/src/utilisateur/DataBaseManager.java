package utilisateur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class DataBaseManager {
	
	Connection connection;
	
	public Connection connectToDatabase() throws SQLException {
		return DriverManager.getConnection(
					"jdbc:mysql://localhost/alertocampus" 
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root",
					"");
	}
	
	//Renvoie la liste de tous les groupes enregistr�s dans l'ordre alphab�tique
	public List<Groupe> getGroups() {
		List<Groupe> groups = new ArrayList<>();
		try (Connection con = this.connectToDatabase(); 
			Statement stmt =  con.createStatement()) {
			try (ResultSet rst = stmt.executeQuery("SELECT Id_Groupe, Libelle from Groupe ORDER BY Libelle")) {
				while(rst.next()) {
					int id = rst.getInt("Id_Groupe");
					String libelle = rst.getString("Libelle");
					Groupe currentgroup = new Groupe(id, libelle, new ArrayList<Utilisateur>(getGroupMembers(id)));
					groups.add(currentgroup);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return groups;
	}
	
	//R�cup�rer les membres d'un groupes tri�s dans l'ordre alphab�tique
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
				members.add(currentUser);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return members;
	}
	
	public Utilisateur getUser(int id) {
		Utilisateur user = null;
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur, Prenom, Nom FROM Utilisateur"
					+ " WHERE Id_Utilisateur = ?");
			stmt.setInt(1, id);
			ResultSet rst = stmt.executeQuery();
			while(rst.next()) {
				int idUser = rst.getInt("Id_Utilisateur");
				String nom = rst.getString("Nom");
				String prenom = rst.getString("Prenom");
				user = new Utilisateur(idUser, prenom, nom);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	public Utilisateur getUser(String login) {
		Utilisateur user = null;
		try {
			Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur, Prenom, Nom FROM Utilisateur"
					+ " WHERE login = ?");
			stmt.setString(1, login);
			ResultSet rst = stmt.executeQuery();
			while(rst.next()) {
				int idUser = rst.getInt("Id_Utilisateur");
				String nom = rst.getString("Nom");
				String prenom = rst.getString("Prenom");
				user = new Utilisateur(idUser, prenom, nom);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		System.out.println(user);
		return user;
	}
	
	boolean isPasswordValid(String login, String password) {
		boolean isValid = false;
		try {
			Connection con = this.connectToDatabase();
			String realPassword = null;
			PreparedStatement stmt =  con.prepareStatement("SELECT motDePasse FROM Utilisateur"
					+ " WHERE login = ?");
			stmt.setString(1, login);
			ResultSet rst = stmt.executeQuery();
			while(rst.next()) {
				realPassword = rst.getString("motDePasse");
			}	
			isValid = password != null && realPassword.equals(password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return isValid;
	}
}
