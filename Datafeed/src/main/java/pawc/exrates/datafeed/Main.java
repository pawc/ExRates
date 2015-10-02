package pawc.exrates.datafeed;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.*;

// args[0] - db host
// args[1] - db name
// args[2] - db user
// args[3] - db user's pass
// args[4] - db table
// args[5] - currency symbol

public class Main {

	public static Connection conn;
	public static String dbtable;
	
	public static void main(String[] args){
	    
	    Log log = LogFactory.getLog("Main program");
	    
	    if(args.length!=6){
	        log.error("Pass valid parameters - check readme");
	        System.exit(-1);
	    }
	    
		conn = DBConnection.connect(args[0], args[1], args[2], args[3]);
		dbtable = args[4];
		
//	while(true){
		ReadAndInsert.go(FileParser.parse(DownloadData.download(args[5])));
	
//		try{
//			log.info("Done with this batch");
//			TimeUnit.HOURS.sleep(12);
			
//		}
//		catch(Exception e){
//		    System.exit(-1);}
		}
//	}
	
}
