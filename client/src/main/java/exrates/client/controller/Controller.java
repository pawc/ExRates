package exrates.client.controller;

import java.sql.Statement;

import exrates.client.model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    
    @FXML
    public TableView table;
    
    @FXML
    public ListView list;
    
    
    public void initialize(){
        constructCurrencyList();
        constructTable();
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
        /*
        //powiązanie kolumn z modelem
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("firstName")
            );
            lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("lastName")
            );
            /*emailCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("email")
            );
        */
        //stworzenie i wypełnienie obserable list
        ObservableList<Record> data = FXCollections.observableArrayList();
        
        //powiązanie tabeli z observable list
        table.setItems(data);
      
        
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

}
