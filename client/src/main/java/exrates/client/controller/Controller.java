package exrates.client.controller;

import java.sql.Statement;
import java.text.DecimalFormat;

import exrates.client.model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class Controller {
    
    public ObservableList<Record> observableList;
    
    public XYChart.Series<String, Number> series1;
  
    @FXML
    public CategoryAxis xAxis;
    
    @FXML
    public NumberAxis yAxis;
    
    @FXML
    public MenuItem delete;
    
    @FXML
    public LineChart<String, Number> lineChart;
    
    
    @FXML
    public TableView table;
    
    @FXML
    public ListView list;
   
    
    public void initialize(){
        
        
        observableList = FXCollections.observableArrayList();
        constructCurrencyList();
        constructTable();
        createLineChart();
        
        
        list.setOnMouseClicked(event->{
            SelectionModel<String> selected = list.getSelectionModel();
            resolveQuery(selected.getSelectedItem());
        });
        
        delete.setOnAction(event->{
            Stage stage = (Stage) list.getScene().getWindow();
            Scene scene = new Scene(lineChart);
            stage.setScene(scene);
            stage.show();
            
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
        series1.getData().clear();

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://pawc.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            
            String Query = "WITH t AS (SELECT * FROM pln WHERE symbol='"+inputSymbol+"' ORDER BY data DESC LIMIT 20) SELECT * FROM t ORDER BY data ASC;";
            
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
                series1.getData().add(new XYChart.Data<String, Number>(data+", "+czas, kursInverted));
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
   

      public void createLineChart(){
          
          xAxis = new CategoryAxis();
          yAxis = new NumberAxis();
          
          series1 = new XYChart.Series<String, Number>();
          
          lineChart.getData().addAll(series1);
      }

    

}
