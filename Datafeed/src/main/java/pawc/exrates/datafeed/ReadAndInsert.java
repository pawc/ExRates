package pawc.exrates.datafeed;

import org.w3c.dom.*;

import pawc.exrates.datafeed.model.Date;
import pawc.exrates.datafeed.model.Record;

import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;

public class ReadAndInsert {
	
	public static void go(Document doc){
		
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
			String rateDecimalFormat = new DecimalFormat("#0.00000").format(rate);
			rateDecimalFormat = rateDecimalFormat.replace(",", ".");
			String name = nlist.item(i).getChildNodes().item(13).getTextContent();
			String fullName = nlist.item(i).getChildNodes().item(15).getTextContent();
			
			String dateInput = nlist.item(i).getChildNodes().item(7).getTextContent();
			Date date = new Date(dateInput);
			
			Record record = new Record(name, fullName, date, rateDecimalFormat);
			DBConnection.insert(Main.conn, Main.dbtable, record);

		}
	}
	
}
