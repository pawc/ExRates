package pawc.exrates.datafeed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import pawc.exrates.datafeed.model.Record;

public class DBConnection {

	public static Connection connect(String host, String dbname, String user, String pass){
		Connection conn = null;
		try{
			Class.forName("org.postgresql.Driver");
		}
		catch(Exception e){
			System.out.println("Driver not found");
			System.exit(-1);
		}
		try{
			conn = DriverManager.getConnection("jdbc:postgresql://"+host+":5432/"+dbname, user, pass);
		}
		catch(PSQLException e){
			System.out.println("Can't connect to the database"+e.getCause());
			System.exit(-1);
		}
		catch(SQLException e){
		    System.out.println("Can't connect to the database"+e.getCause());
            System.exit(-1);
		}
		return conn;
	}

	public static void insert(Connection conn, String table, Record record){
	    Statement stmt = null;
        try{
            stmt = conn.createStatement();
            String query = "INSERT INTO "+table+" VALUES ('"+record.getName()+"', '"
                    +record.getRate()+"', '"+record.getDateToSQLFormat()+"', '"
                    +record.getTimeToSQLFormat()+"', '"+record.getFullName()+"');";
            stmt.executeUpdate(query);
            stmt.close();
        }
        catch(PSQLException e){
            System.out.println("Error: "+e.toString());
            System.exit(-1);
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.exit(-1);
        }
	}
	
}
