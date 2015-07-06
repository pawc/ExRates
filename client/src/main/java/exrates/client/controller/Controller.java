package exrates.client.controller;

import java.sql.Statement;

import exrates.client.model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    
    public ObservableList<Record> observableList;
    
    @FXML
    public TableView table;
    
    @FXML
    public ListView list;
   
    
    public void initialize(){
        
        observableList = FXCollections.observableArrayList();
        constructCurrencyList();
        constructTable();
        
        
        list.setOnMouseClicked(event->{
            SelectionModel<String> selected = list.getSelectionModel();
            resolveQuery(selected.getSelectedItem());
        });
      
    }
    
    
    public void constructTable(){
        
      //tworzenie kolumn
        TableColumn symbolCol = new TableColumn("Symbol");
        TableColumn dateCol = new TableColumn("Date");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn rateCol = new TableColumn("Rate");
        TableColumn nameCol = new TableColumn("Name");
        
        
        //dodawanie kolumn do tabeli
        table.getColumns().addAll(symbolCol, dateCol, timeCol, rateCol, nameCol);
        
        //powiązanie kolumn z modelem
        symbolCol.setCellValueFactory( new PropertyValueFactory<Record,String>("Symbol"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Time"));
        rateCol.setCellValueFactory(new PropertyValueFactory<Record,Double>("Rate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Name"));
        
        //stworzenie i wypełnienie obserable list
       
       //powiązanie tabeli z observable list
        table.setItems(observableList);
        
    }
    
    public void constructCurrencyList(){
        ObservableList<String> currencyList = FXCollections.observableArrayList();
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://pawc.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            String Query = "SELECT DISTINCT SYMBOL FROM PLN ORDER BY SYMBOL";
            ResultSet rs = stmt.executeQuery(Query);
            while(rs.next()){
                String symbol = rs.getString(1);
                currencyList.add(symbol);
            }
        }
        catch(Exception e){
            System.out.println("SQL error: "+e.toString() );
       }
        
        list.setItems(currencyList);
    }
    
    public void resolveQuery(String inputSymbol){
        
        observableList.clear();

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://pawc.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            
            String Query = "SELECT * FROM pln WHERE symbol='"+inputSymbol+"' ORDER BY DATA;";
            
            ResultSet rs = stmt.executeQuery(Query);
            while(rs.next()){
                String symbol = rs.getString(1);
                String kurs = rs.getString(2);
                String data = rs.getString(3);
                String czas = rs.getString(4);
                String nazwa = rs.getString(5);
                
                Double kursInverted = Double.parseDouble(kurs);
                kursInverted = 1/kursInverted;
                
                Record record = new Record(symbol, kursInverted, data, czas, nazwa);
                observableList.add(record);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

}
