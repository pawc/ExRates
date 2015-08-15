package pawc.exrates.datafeed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

	public static Connection connect(String user, String pass){
		Connection conn = null;
		try{
			Class.forName("org.postgresql.Driver");
		}
		catch(Exception e){
			System.out.println("Driver not found");
			System.exit(-1);
		}
		try{
			conn = DriverManager.getConnection("jdbc:postgresql://pawc.ddns.net:5432/postgres", user, pass);
		}
		catch(Exception e){
			System.out.println("Can't connect to the database");
			System.exit(-1);
		}
		return conn;
	}
	
	public static void insert(Connection conn, String table, String symbol, String kurs, String data, String czas, String nazwa){
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			String query = "INSERT INTO "+table+" VALUES ('"+symbol+"', '"+kurs+"', '"+data+"', '"+czas+"', '"+nazwa+"');";
			System.out.println("Updated positions: "+stmt.executeUpdate(query));
		}
		catch(Exception e){
			System.out.println(e.toString());
			System.exit(-1);
		}
	}
	
	
}
