package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LogController {
    @FXML    private ResourceBundle resources;
    @FXML    private URL location;
    @FXML    private Button btn_back;
    @FXML    private Button btn_delete;
    @FXML    private TableColumn<DataModel, Integer> idColumn;
    @FXML    private TableColumn<DataModel, String> commandColumn;
    @FXML    private TableColumn<DataModel, String> dateColumn;
    @FXML    private TableColumn<DataModel, Boolean> isSuccessColumn;
    @FXML    private TableView<DataModel> tabloview;
    Connection c = null;
	Statement stmt = null;
    ObservableList<DataModel> data = FXCollections.observableArrayList();
    //ObservableList<String> dataString = FXCollections.observableArrayList();

    @FXML
    void btn_back_click(ActionEvent event) {
    	 Stage stage = (Stage) btn_back.getScene().getWindow();
         stage.close(); 
    }

    @FXML
    void btn_delete_click(ActionEvent event) {
    	/* ObservableList<DataModel> data = tabloview.getItems();
    	    for (DataModel item : data) {
    	    //    System.out.println("ID: " + item.getId() + ", command : " + item.getCommand() + " date: "+item.getDate() + " isSuccess: "+item.GetSuccess() );
    	    }
*/
    	
    	
    	DataModel selectedData= tabloview.getSelectionModel().getSelectedItem();
    	 if (selectedData != null) {
    	        // Seçili öğenin ID değerini al ve konsola yazdır
    	        int id = selectedData.getId();
    	        System.out.println("Seçili öğe ID'si: " + id);
    	        try {
    	            connect();
    	            Statement stmt = c.createStatement();
    	            String sql = "DELETE FROM convert WHERE id = " + id + ";";
    	            stmt.executeUpdate(sql);
    	            stmt.close();
    	            c.commit();
    	            c.close();
    	            
    	            // Veritabanından silindikten sonra tabloyu güncelle
    	            tabloview.getItems().remove(selectedData);
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        
    	        
    	        
    	    } else {
    	        // Eğer hiçbir öğe seçilmediyse
    	        System.out.println("Hiçbir öğe seçilmedi.");
    	    }
    }

    
   
    
    @FXML
    void initialize() {
 
    	  connect();
    	    GetAndFillData();

    	    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    	    commandColumn.setCellValueFactory(cellData -> cellData.getValue().commandProperty());
    	    dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    	    isSuccessColumn.setCellValueFactory(cellData -> cellData.getValue().successProperty());

    	    tabloview.setItems(data);
    	 //   listview.setItems(dataString);
    	    tabloview.refresh();
     
    }
    void connect() {
      	 
   		try {
   			//Class.forName("org.sqlite.JDBC");
   		      c = DriverManager.getConnection("jdbc:sqlite:/home/murar/sqllite/Convert.db");
   		      c.setAutoCommit(false);
   		      System.out.println("Opened database successfully");

   		} catch (SQLException e) {
        	System.out.println("baglanmadı");
            System.out.println(e.getMessage());
        }
      	
      	
      }
    void GetAndFillData() {
    	try {
    		
    		stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM convert;" );

		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String idString=String.valueOf(id);
		         String  command = rs.getString("command");
		         String  date = rs.getString("date");
		         String  isSuccessString = rs.getString("isSuccess");
		         boolean issuccess= isSuccessString.equals("0")  ? true : false;  
	            data.add(new DataModel(id, command,date,issuccess));
	       //     dataString.add(idString+"\t"+command+"\t"+date+"\t"+issuccess);
//tabloview.getItems().add(new DataModel(id, command, date, issuccess));
		      }
		  //    tabloview.getItems().addAll(data);
		      System.out.println("burada ");
		      rs.close();
		      stmt.close();
		      c.close();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    }
   
}
