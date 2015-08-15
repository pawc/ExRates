package pawc.exrates.datafeed.model;

public class Date {
    
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minutes;
    private String seconds;

    public Date(String input){
        year = input.split(" ")[3];
        month = input.split(" ")[2];
        day = input.split(" ")[1];
        hour = input.split(" ")[4].split(":")[0];
        minutes = input.split(" ")[4].split(":")[1];
        seconds = input.split(" ")[4].split(":")[2];
    }
    
    public String getYear(){
        return year;
    }
    
    public String getMonth(){
        return month;
    }
    
    public String getDay(){
        return day;
    }
    
    public String getHour(){
        return hour;
    }
    
    public String getMinutes(){
        return minutes;
    }
    
    public String getSeconds(){
        return seconds;
    }
    
}
