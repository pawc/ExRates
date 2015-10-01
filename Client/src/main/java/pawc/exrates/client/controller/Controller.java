package pawc.exrates.client.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.util.Vector;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pawc.exrates.client.model.Record;

public class Controller {
	
    private String baseCurrency = "PLN";
	private Vector<XYChart.Series<String, Number>> seriesContainer;
	private FilteredList<Record> filteredList;
	private ObservableList<String> currencyList;
    private ObservableList<Record> observableList;
    @FXML private MenuItem clear;
    @FXML private MenuItem close;
    @FXML private MenuItem about;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private LineChart<String, Number> lineChart;
    @FXML private TableView table;
    @FXML private ListView list;
    @FXML private ComboBox filterCombobox;
    @FXML private ComboBox baseCombobox;
    @FXML private Button clearButton;

    public void initialize(){
        
        seriesContainer = new Vector<XYChart.Series<String, Number>>() ;
    	observableList = FXCollections.observableArrayList();
        
        constructCurrencyList();
        constructTable();
        createLineChart();
        
        initBaseCmb();
        
        list.setOnMouseClicked(event->{
            currencyListAction();
        });
        
        about.setOnAction(event->{
            showAboutPane();
        });
        
        clearButton.setOnAction(event->{
            clear();
        });
        
        baseCombobox.setOnAction(event->{
            baseCurrencyCmbAction();
        });
        
        close.setOnAction(event->{
            System.exit(0);
        });
        
        clear.setOnAction(event->{
            clear();
        });
    }

    private void initBaseCmb() {
        baseCombobox.getItems().add("PLN");
        baseCombobox.getItems().add("EUR");
        baseCombobox.getItems().add("USD");
        baseCombobox.getItems().add("GBP");
        baseCombobox.getItems().add("SEK");
        baseCombobox.getItems().add("CHF");
        baseCombobox.getItems().add("CNY");
        baseCombobox.getItems().add("RUB");
        baseCombobox.setValue("PLN");
    }

    private void currencyListAction() {
        SelectionModel<String> selected = list.getSelectionModel();
        if(filterCombobox.getItems().contains(selected.getSelectedItem())) return;
        if(!filterCombobox.getItems().contains("ALL")) filterCombobox.getItems().add("ALL");
        filterCombobox.setValue("ALL");
        filterCombobox.getItems().add(selected.getSelectedItem());
        resolveQuery(selected.getSelectedItem());
    }

    private void baseCurrencyCmbAction() {
        currencyList.clear();
        clear();
        baseCurrency = baseCombobox.getValue().toString();
        constructCurrencyList();
    }

    private void showAboutPane() {
        AnchorPane anchorPane = null;
        try {
            anchorPane = (AnchorPane) FXMLLoader.load(ClassLoader.getSystemResource("exrates/client/About.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
    }

    private void clear() {
        filterCombobox.getItems().clear();
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
        symbolCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Symbol"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Time"));
        rateCol.setCellValueFactory(new PropertyValueFactory<Record,Double>("Rate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Record,String>("Name"));
        
        filteredList = new FilteredList<Record>(observableList);
        
        filterCombobox.valueProperty().addListener((observable, oldValue, newValue)->{
           
            filteredList.setPredicate(record -> {
                // If filter text is empty, display all
                if (newValue == null) {
                    return true;
                }
                
                if(newValue.toString().contains("ALL")){
                    return true;
                }

                if (record.getSymbol().contains(newValue.toString())) {
                    return true; 
                }
                return false; 
            });
           });    
      
        SortedList<Record> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    
    public void constructCurrencyList(){
        currencyList = FXCollections.observableArrayList();
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://kritsit.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            String Query = "SELECT DISTINCT SYMBOL FROM "+baseCurrency+" ORDER BY SYMBOL";
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
            Connection conn = DriverManager.getConnection("jdbc:postgresql://kritsit.ddns.net:5432/postgres", "xml", "xml");
            Statement stmt = conn.createStatement();
            String Query = "SELECT * FROM "+baseCurrency+" WHERE symbol='"+inputSymbol+"'ORDER BY date, time";
            
            ResultSet rs = stmt.executeQuery(Query);
            
            while(rs.next()){
                String symbol = rs.getString(1);
                String kurs = rs.getString(2);
                String data = rs.getString(3);
                String czas = rs.getString(4);
                String nazwa = rs.getString(5);
                
                Double rate = Double.parseDouble(kurs);
                
                Record record = new Record(symbol, rate, data, czas, nazwa);
                observableList.add(record);
                
                series.getData().add(new XYChart.Data<String, Number>(data+" "+czas, rate));
             }
            
            rs.close();
            stmt.close();
            
           series.setName(inputSymbol); 
           seriesContainer.add(series);
            
           lineChart.getData().addAll(series);
            
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace(System.out);
        }
    }
    
    public void createLineChart(){
          xAxis = new CategoryAxis();
          yAxis = new NumberAxis();
    }

}
