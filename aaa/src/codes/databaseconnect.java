package codes;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class databaseconnect {
	
	public static Connection connect() {
		Connection conn = null;
		
		try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/itproject","root", "");
            System.out.print("Connected !");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection failed. Try again !");
        }
		return conn;
	}
	

}
