
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mysql.cj.*;


public class Serveur {
	
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
			ResultSet rst = stmt.executeQuery("SELECT Id_Utilisateur, Nom, Prenom FROM utilisateur");
			while(rst.next()) {
				String nom = rst.getString("Nom");
				String prenom = rst.getString("Prenom");
				int id = rst.getInt("Id_Utilisateur");
				Utilisateur currentuser = new Utilisateur(id, prenom, nom);
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
			Statement stmt =  con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT Id_Utilisateur, Prenom, Nom from Utilisateur"
					+ "WHERE Id_Utilisateur =" + id);
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
	
	public Set<Utilisateur> getGroupMembers(int id) {
		Set<Utilisateur> members = new TreeSet<>();
		try {
			Connection con = this.connectToDatabase();
			Statement stmt =  con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT Id_Utilisateur FROM appartenir"
					+ "WHERE Id_Groupe = " + id);
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
	
	
}
