package exrates.datafeed;

import org.w3c.dom.*;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ReadDocAndSendToSQL {
	
	public static void read(Document doc){
		
		Log log = LogFactory.getLog("inner class");
		
		NodeList nlista = doc.getDocumentElement().getElementsByTagName("item");
		
		for(int i=0; i<nlista.getLength(); i++){
			
			Double kurs;
			try{
			kurs = Double.parseDouble(nlista.item(i).getLastChild().getPreviousSibling().getTextContent());
			}
			catch(NumberFormatException e){System.out.println("Kurs waluty z przecinkiem dla tysiecy - nie da rady skonwertowac"); continue;}
			
			String name = nlista.item(i).getLastChild().getPreviousSibling()
					.getPreviousSibling().getPreviousSibling().getPreviousSibling()
					.getPreviousSibling().getTextContent();
			
			String fullName = nlista.item(i).getLastChild().getPreviousSibling()
					.getPreviousSibling().getPreviousSibling().getTextContent();
			
			char[] data = nlista.item(i).getFirstChild().getNextSibling()
					.getNextSibling().getNextSibling().getNextSibling()
					.getNextSibling().getNextSibling().getNextSibling().getTextContent().toCharArray();
			
			// w zaleznosci od ilosci cyfr w dniu przesuwa odczyt dla skaldnikow daty
			int dodatek;  
			String dzien;
			
			if(String.valueOf(data,5,2).toCharArray()[1]=='-'){
				//System.out.println("dzie jednocyfrowy");
				dzien = obrobDzien(String.valueOf(data, 5, 1));
				dodatek=0;
			}
			else{
				//System.out.println("dzien dwucyfrowy");
				dzien = obrobDzien(String.valueOf(data, 5, 2));
				dodatek=1;
			}
			
			String miesiac = String.valueOf(data, 7+dodatek, 3);
			String rok = String.valueOf(data, 11+dodatek, 4);
			String godzina = String.valueOf(data, 16+dodatek, 8 );
			String dataDoTabeli = dzien+"-"+miesiac+"-"+rok;
			
			log.info(kurs+" "+name+" "+dzien+"-"+miesiac+"-"+rok+" "+godzina+" "+fullName);
			DBConnection.insert(FloatRates.conn, "PLN", name, kurs.toString(), dataDoTabeli, godzina, fullName);
			
		}
	}
	
	public static String obrobDzien(String dzien){
		if(Integer.parseInt(dzien)<10){
			dzien="0"+dzien;
		}
		return dzien;
	}
	
}
