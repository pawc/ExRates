package exrates.datafeed;

import org.w3c.dom.Document;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FileParser {

	public static Document parse(File file){
		
		Document doc = null;
		
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//factory.isIgnoringElementContentWhitespace();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(file);
		//doc.normalize();
		}
		catch(Exception e){
			System.out.println("Blad przy parsowaniu pliku: "+e.toString());
			System.exit(-1);
		}
		
		return doc;
		
	}
	
}
