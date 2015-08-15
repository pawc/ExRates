package pawc.exrates.datafeed;

import org.w3c.dom.*;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;

public class ReadDocAndSendToSQL {
	
	public static void read(Document doc){
		
		Log log = LogFactory.getLog("inner class");
		
		NodeList nlist = doc.getDocumentElement().getElementsByTagName("item");
		
		for(int i=0; i<nlist.getLength(); i++){
			
			Double rate;
			try{
			rate = Double.parseDouble(nlist.item(i).getChildNodes().item(17).getTextContent());
			}
			catch(NumberFormatException e){
			    System.out.println("Kurs waluty z przecinkiem dla tysiecy - nie da rady skonwertowac");
			    continue;
			}
			rate=1/rate;
			String rateDecimalFormat = new DecimalFormat("#0.0000").format(rate);
			String name = nlist.item(i).getChildNodes().item(13).getTextContent();
			String fullName = nlist.item(i).getChildNodes().item(15).getTextContent();
			
			char[] data = nlist.item(i).getFirstChild().getNextSibling()
					.getNextSibling().getNextSibling().getNextSibling()
					.getNextSibling().getNextSibling().getNextSibling().getTextContent().toCharArray();
			
			log.info(name+", "+fullName+", "+rateDecimalFormat);
			
			//log.info(kurs+" "+name+" "+dzien+"-"+miesiac+"-"+rok+" "+godzina+" "+fullName);
			//DBConnection.insert(Main.conn, "PLN", name, kurs.toString(), dataDoTabeli, godzina, fullName);
			
		}
	}
	
	public static String obrobDzien(String dzien){
		if(Integer.parseInt(dzien)<10){
			dzien="0"+dzien;
		}
		return dzien;
	}
	
}
