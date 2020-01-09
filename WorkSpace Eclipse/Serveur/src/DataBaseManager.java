

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
					Groupe currentgroup = new Groupe(id, libelle, new TreeSet<Utilisateur>(getGroupMembers(id)));
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
	
	//Nouveau fil
	public int newFil(String titre, int idUtilisateur, int idGroupe) {
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO fil_de_discussion (Titre, Id_Utilisateur, Id_Groupe)"
					+ " VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, titre);
			stmt.setInt(2, idUtilisateur);
			stmt.setInt(3, idGroupe);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		    rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
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

	public int newMessage(String text, int idExp, int idFil, Long date) {
		Date d = new Date(date);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sd.format(d));
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO message (Texte, dCreation, Id_Fil_de_discussion, Id_Utilisateur)"
		+ " VALUES (?, \""+sd.format(d)+"\", ?, ?)",Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, text);
			System.out.println(date);
			stmt.setInt(2, idFil);
			stmt.setInt(3, idExp);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		    rs.next();
		    int idMessage = rs.getInt(1);
		  
		    PreparedStatement stmt2 = con.prepareStatement("INSERT INTO status(Id_Utilisateur, Id_Message, Status) VALUES (?, ?, ?)");
		    stmt2.setInt(1, idExp);
		    stmt2.setInt(2, idMessage);
		    stmt2.setString(3, "LU");
		    stmt2.executeUpdate();
		    
			return idMessage;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
	}
}
