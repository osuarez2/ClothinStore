import java.sql.Connection;
import java.sql.DriverManager;

class Database {
	
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/clothing_store?serverTimezone=EST", "root", "Molly&Elvis2");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}


