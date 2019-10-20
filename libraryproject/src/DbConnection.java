
import java.sql.*;
import javax.swing.*;
public class DbConnection{
	static Connection conn=null;
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn=DriverManager.getConnection("jdbc:sqlite:/home/abhishek/eclipse-workspace/Library/DB/Libdata");
			//JOptionPane.showMessageDialog(null, "Connection Successful");
			return conn;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
