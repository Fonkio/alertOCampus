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
	
	public Connection connectToDatabase() throws SQLException {
		return DriverManager.getConnection(
					"jdbc:mysql://localhost/alertocampus" 
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root",
					"");
	}
	
	//Renvoie la liste de tous les groupes enregistrés dans l'ordre alphabétique
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
	
	//Récupérer les membres d'un groupes triés dans l'ordre alphabétique
	public NavigableSet<Utilisateur> getGroupMembers(int id) {
		NavigableSet<Utilisateur> members = new TreeSet<>();
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt = con.prepareStatement("SELECT Id_Utilisateur FROM appartenir WHERE Id_Groupe = ?");){
			stmt.setInt(1, id);	
			try(ResultSet rst = stmt.executeQuery()) {
				while(rst.next()) {
					int idUser = rst.getInt("Id_Utilisateur");
					Utilisateur currentUser = this.getUser(idUser);
					members.add(currentUser);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return members;
	}
	
	/* Récupérer un utilisateur(son nom et son prénom) à partir de l'id
		Renvoie null s'il n'y a rien */
	public Utilisateur getUser(int id) {
		Utilisateur user = null;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur, Prenom, Nom FROM Utilisateur"
						+ " WHERE Id_Utilisateur = ?");){
			stmt.setInt(1, id);
			try(ResultSet rst = stmt.executeQuery()){
				while(rst.next()) {
					int idUser = rst.getInt("Id_Utilisateur");
					String nom = rst.getString("Nom");
					String prenom = rst.getString("Prenom");
					user = new Utilisateur(idUser, prenom, nom);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	/* Récupérer un utilisateur(son nom et son prénom) à partir du login
	Renvoie null s'il n'y a rien */
	public Utilisateur getUser(String login) {
		Utilisateur user = null;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT Id_Utilisateur, Prenom, Nom FROM Utilisateur"
						+ " WHERE login = ?");) {
			stmt.setString(1, login);
			try (ResultSet rst = stmt.executeQuery()) {
				while(rst.next()) {
					int idUser = rst.getInt("Id_Utilisateur");
					String nom = rst.getString("Nom");
					String prenom = rst.getString("Prenom");
					user = new Utilisateur(idUser, prenom, nom);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return user;
	}
	
	/* Cherche l'utilisateur relatif à un login et s'il existe, vérifie que le mdp est le bon
	 * retourne vrai s'il les logs sont bons, faux sinon */
	boolean isPasswordValid(String login, String password) {
		boolean isValid = false;
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt =  con.prepareStatement("SELECT motDePasse FROM Utilisateur"
						+ " WHERE login = ?");){
			String realPassword = null;
			stmt.setString(1, login);
			try(ResultSet rst = stmt.executeQuery()){
				while(rst.next()) {
					realPassword = rst.getString("motDePasse");
				}	
			}
			isValid = realPassword != null && realPassword.equals(password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return isValid;
	}
}
