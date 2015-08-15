package pawc.exrates.datafeed;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.*;

public class Main {

	public static Connection conn = DBConnection.connect("xml", "xml");
	
	public static void main(String[] args){
		
		Log log = LogFactory.getLog("Main program");
		
		while(true){
		ReadDocAndSendToSQL.read(FileParser.parse(DownloadData.download()));
	
		try{
			log.info("Done with this batch");
			TimeUnit.HOURS.sleep(12);
			
		}
		catch(Exception e){System.exit(-1);}
		
		}
	}
	
}
