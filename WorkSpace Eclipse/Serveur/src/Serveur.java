import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Serveur {
	
	public void connectToDatabase() throws SQLException {
		
		Connection connexion = DriverManager.getConnection(
					"jdbc:myDriver:myDatabase",
					"root",
					""
				);
	}
	
}
