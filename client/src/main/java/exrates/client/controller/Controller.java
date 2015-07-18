package exrates.client.controller;

import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;

import exrates.client.Client;
import exrates.client.model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Controller {
	
	private Vector<XYChart.Series<String, Number>> seriesContainer;
    
    private ObservableList<Record> observableList;
    
    @FXML
    public MenuItem clear;
    
    @FXML
    public MenuItem close;
    
    @FXML
    public MenuItem about;
  
    @FXML
    public CategoryAxis xAxis;
    
    @FXML
    public NumberAxis yAxis;
    
    @FXML
    public LineChart<String, Number> lineChart;
    
    
    @FXML
    public TableView table;
    
    @FXML
    public ListView list;

    
    public void initialize(){
        
    	seriesContainer = new Vector<XYChart.Series<String, Number>>() ;
    	
        observableList = FXCollections.observableArrayList();
        constructCurrencyList();
        constructTable();
        createLineChart();
        
        list.setOnMouseClicked(event->{
            SelectionModel<String> selected = list.getSelectionModel();
            resolveQuery(selected.getSelectedItem());
        });
        
        about.setOnAction(event->{
            AnchorPane anchorPane = null;
            try {
                anchorPane = FXMLLoader.load(Client.class.getResource("About.fxml"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setScene(scene);
            stage.show();
        });
        
        close.setOnAction(event->{
            System.exit(0);
        });
        
        clear.setOnAction(event->{
            SelectionModel<String> selected = list.getSelectionModel();
            selected.clearSelection();
            observableList.clear();
            lineChart.setAnimated(false);
            for(XYChart.Series<String, Number> series : seriesContainer){
            	series.getData().clear();
            }
            lineChart.setAnimated(true);
            lineChart.getData().clear();
            seriesContainer.removeAllElements();
            
            
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
        
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://pawc.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            String Query = "SELECT * FROM Pln WHERE symbol='"+inputSymbol+"'ORDER BY data, czas";
            
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
                
                
                series.getData().add(new XYChart.Data<String, Number>(data+" "+czas, kursInverted));
             }
            
            rs.close();
            stmt.close();
            
           series.setName(inputSymbol); 
           seriesContainer.add(series);
            
           lineChart.getData().addAll(series);
            
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
   

      public void createLineChart(){
          
          xAxis = new CategoryAxis();
          yAxis = new NumberAxis();
          
          
          
      }

    

}
