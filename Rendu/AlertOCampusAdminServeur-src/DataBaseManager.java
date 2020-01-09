

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
		    
		    
		    PreparedStatement stmt2 = con.prepareStatement("INSERT INTO status_fil(Id_Utilisateur, Id_Fil_de_discussion, Status) VALUES (?, ?, ?)");
		    stmt2.setInt(1, idUtilisateur);
		    stmt2.setInt(2, rs.getInt(1));
		    stmt2.setString(3, "RECU");
		    stmt2.executeUpdate();
		    
		    
		    
		    
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
		try (Connection con = this.connectToDatabase();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO message (Texte, dCreation, Id_Fil_de_discussion, Id_Utilisateur)"
		+ " VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, text);
			stmt.setInt(3, idFil);
			stmt.setLong(2, date);
			stmt.setInt(4, idExp);
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

	public List<String> getFilMessage(int idUser) {
		List<String> filsMessages = new ArrayList<>();
		try (Connection con = this.connectToDatabase(); 
				PreparedStatement stmt =  con.prepareStatement("SELECT f.Id_Fil_de_discussion, f.Titre, f.Id_Utilisateur, f.Id_Groupe "
						+ "FROM fil_de_discussion as f "
						+ "WHERE (EXISTS (SELECT * FROM appartenir WHERE Id_Utilisateur = ? AND Id_Groupe = f.Id_Groupe) OR f.Id_Utilisateur = ?) "
						+ "AND NOT EXISTS (SELECT * FROM status_fil WHERE Id_Utilisateur = ? AND Id_Fil_de_discussion = f.Id_Fil_de_discussion) ")) {
				stmt.setInt(1, idUser);
				stmt.setInt(2, idUser);
				stmt.setInt(3, idUser);
			try (ResultSet rst = stmt.executeQuery()) {
				while(rst.next()) {
					int id = rst.getInt("Id_Fil_de_discussion");
					String titre = rst.getString("Titre");
					int idGroupe = rst.getInt("Id_Groupe");
					int idUtilisateur = rst.getInt("Id_Utilisateur");
					String s = "F "+id +" " + titre.replace(" ", "£") +" " + idUtilisateur + " "+ idGroupe;
					filsMessages.add(s);

					PreparedStatement stmt1 = con.prepareStatement("SELECT COUNT(*) as c FROM status_fil WHERE Id_Utilisateur = ? AND Id_Fil_de_discussion = ?");
					stmt1.setInt(1, idUser);
				    stmt1.setInt(2, id);
				    ResultSet rst1 = stmt1.executeQuery();
				    
				    rst1.next();
				    System.out.println(rst1.getInt("c"));
				    if (rst1.getInt("c") == 0) {
						PreparedStatement stmt2 = con.prepareStatement("INSERT INTO status_fil(Id_Utilisateur, Id_Fil_de_discussion, Status) VALUES (?, ?, ?)");
					    stmt2.setInt(1, idUser);
					    stmt2.setInt(2, id);
					    stmt2.setString(3, "RECU");
					    stmt2.executeUpdate();
				    }
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		try (Connection con = this.connectToDatabase(); 
			PreparedStatement stmt =  con.prepareStatement("SELECT m.Id_Message, m.Texte, m.dCreation, m.Id_Fil_de_discussion, m.Id_Utilisateur "
					+ "FROM message as m, fil_de_discussion as f "
					+ "WHERE m.Id_Fil_de_discussion = f.Id_Fil_de_discussion "
					+ "AND (EXISTS (SELECT * FROM appartenir WHERE Id_Utilisateur = ? AND Id_Groupe = f.Id_Groupe) OR f.Id_Utilisateur = ?) "
					+ "AND NOT EXISTS (SELECT * FROM status WHERE Id_Utilisateur = ? AND Id_Message = m.Id_Message) ")) {
			stmt.setInt(1, idUser);
			stmt.setInt(2, idUser);
			stmt.setInt(3, idUser);
			try (ResultSet rst = stmt.executeQuery()){
				while(rst.next()) {
					int id = rst.getInt("Id_Message");
					String text = rst.getString("Texte");
					Date dc = new Date(rst.getLong("dCreation"));
					int idFil = rst.getInt("Id_Fil_de_discussion");
					int idUtilisateur = rst.getInt("Id_Utilisateur");
					String s = "M "+id +" " + text.replace(" ", "£") +" " + dc.getTime() + " " + idFil + " "+ idUtilisateur;
					filsMessages.add(s);

					PreparedStatement stmt1 = con.prepareStatement("SELECT COUNT(*) as c FROM status WHERE Id_Utilisateur = ? AND Id_Message = ?");
					stmt1.setInt(1, idUser);
				    stmt1.setInt(2, id);
				    ResultSet rst1 = stmt1.executeQuery();
				    
				    rst1.next();
				    if (rst1.getInt("c") == 0) {
				    	PreparedStatement stmt2 = con.prepareStatement("INSERT INTO status(Id_Utilisateur, Id_Message, Status) VALUES (?, ?, ?)");
					    stmt2.setInt(1, idUser);
					    stmt2.setInt(2, id);
					    stmt2.setString(3, "RECU");
					    stmt2.executeUpdate();
				    }
				    
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filsMessages;
	}

	public String getStatus(int idMessage, int idUser) {
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt =  con.prepareStatement("SELECT Status FROM status"
							+ " WHERE Id_Utilisateur = ? AND Id_Message = ?");){
				stmt.setInt(1, idUser);
				stmt.setInt(2, idMessage);
				String status = "EN_ATTENTE";
				try(ResultSet rst = stmt.executeQuery()){
					while(rst.next()) {
						status = rst.getString("Status");
					}
					return status;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void LireStatus(int idFil, int idUser) {
		try (Connection con = this.connectToDatabase();
				PreparedStatement stmt =  con.prepareStatement("UPDATE status as s, message as m SET s.Status = \"LU\" WHERE m.Id_Message = s.Id_Message AND m.Id_Fil_de_discussion = ? AND s.Id_Utilisateur = ?");){
				stmt.setInt(1, idFil);
				stmt.setInt(2, idUser);
				stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
