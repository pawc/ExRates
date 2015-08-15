package pawc.exrates.datafeed;

import org.w3c.dom.Document;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FileParser {

	public static Document parse(File file){
		
		Document doc = null;
		
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(file);
		}
		catch(Exception e){
			System.out.println("Error parsing file: "+e.toString());
			System.exit(-1);
		}
		
		return doc;
		
	}
	
}
