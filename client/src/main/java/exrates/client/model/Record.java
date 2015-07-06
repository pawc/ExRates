package exrates.client.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Record {

    SimpleStringProperty symbol;
    SimpleDoubleProperty rate;
    SimpleStringProperty date;
    SimpleStringProperty time;
    SimpleStringProperty name;
    
    public Record(String symbol, Double rate, String date, String time, String name){
        this.symbol = new SimpleStringProperty(symbol);
        this.rate = new SimpleDoubleProperty(rate);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.name = new SimpleStringProperty(name);
    }
    
    //Accessors
    public String getSymbol(){
        return symbol.get();
    }
    
    public Double getRate(){
        return rate.get();
    }
    
    public String getDate(){
        return date.get();
    }
    
    public String getTime(){
        return time.get();
    }
    
    public String getName(){
        return name.get();
    }
    
    //Mutators
    public void setSymbol(String symbol){
        this.symbol.set(symbol);
    }
    
    public void setRate(Double rate){
        this.rate.set(rate);
    }
    
    public void setDate(String date){
        this.date.set(date);
    }
    
    public void setTime(String time){
        this.time.set(time);
    }
    
    public void setName(String name){
        this.name.set(name);
    }
}
