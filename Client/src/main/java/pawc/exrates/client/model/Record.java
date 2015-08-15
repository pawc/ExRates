package pawc.exrates.client.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class representing a single record in database.
 * @author pawc
 * @version 1.0
 */
public class Record {

    private SimpleStringProperty symbol;
    private SimpleDoubleProperty rate;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty name;
    
    public Record(String symbol, Double rate, String date, String time, String name){
        this.symbol = new SimpleStringProperty(symbol);
        this.rate = new SimpleDoubleProperty(rate);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.name = new SimpleStringProperty(name);
    }
    
    //Accessors
    /**
     * @return symbol of the currency
     */
    public String getSymbol(){
        return symbol.get();
    }
    
    /**
     * 
     * @return rate with regards to PLN at the given time
     */
    public Double getRate(){
        return rate.get();
    }
    
    /**
     * 
     * @return date of the record
     */
    public String getDate(){
        return date.get();
    }
    
    /**
     * 
     * @return time of the record
     */
    public String getTime(){
        return time.get();
    }
    
    /**
     * 
     * @return full name of the related currency
     */
    public String getName(){
        return name.get();
    }
    
    //Mutators
    /**
     * 
     * @param symbol setting symbol of the currency
     */
    public void setSymbol(String symbol){
        this.symbol.set(symbol);
    }
    
    /**
     * 
     * @param rate setting rate of the currency against PLN
     */
    public void setRate(Double rate){
        this.rate.set(rate);
    }
    
    /**
     * 
     * @param date setting date of the record
     */
    public void setDate(String date){
        this.date.set(date);
    }
    
    /**
     * 
     * @param time setting time of the record
     */
    public void setTime(String time){
        this.time.set(time);
    }
    
    /**
     * 
     * @param name setting full name of the currency
     */
    public void setName(String name){
        this.name.set(name);
    }
}
