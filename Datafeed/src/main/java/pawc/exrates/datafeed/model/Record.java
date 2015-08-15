package pawc.exrates.datafeed.model;

public class Record {
    
    private String name;
    private String fullName;
    private Date date;
    private String rate;
    
    public Record(String name, String fullName, Date date, String rate){
        this.name=name;
        this.fullName=fullName;
        this.date=date;
        this.rate=rate;
    }
    
    public String getName(){
        return name;
    }
    
    public String getFullName(){
        return fullName;
    }
    
    public String getDateToSQLFormat(){
        return date.getDay()+"-"+date.getMonth()+"-"+date.getYear();
    }
    
    public String getTimeToSQLFormat(){
        return date.getHour()+":"+date.getMinutes()+":"+date.getSeconds();
    }
    
    public String getRate(){
        return rate;
    }
    
}
