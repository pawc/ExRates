package pawc.exrates.datafeed;

import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DownloadData {

	public static File download(String symbol){
		
		File file = new File(symbol+".xlm");
		try{
		URL url = new URL("http://www.floatrates.com/daily/"+symbol+".xml");
		URLConnection urlconn = url.openConnection();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
		FileWriter fw = new FileWriter(file);
		String line;
			while((line=bfr.readLine())!=null){
				fw.write(line);
			}
		fw.close();
		bfr.close();
		
		}
		catch(Exception e){
			System.out.println("Error downloading data from the site: "+e.toString());
			System.exit(-1);
		}
		return file;
	}
	
	
}
