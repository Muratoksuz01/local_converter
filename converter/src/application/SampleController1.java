package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SampleController1 {
    ObservableList<String> options =FXCollections.observableArrayList("pdf","png","jpg","mp4","mp3");
    List<String> TumPaths=new ArrayList<String>();
    List<String> TumNames=new ArrayList<String>();
	File file;
    @FXML    private ResourceBundle resources;
    @FXML    private URL location;
    @FXML    private Button btn_convert;
    @FXML    private Button btn_open_file;
    @FXML    private ComboBox<String> combo_type;
    @FXML    private Label lbl_path;
    @FXML    private TextField txt_rename;

    @FXML
    void btn_convert_click(ActionEvent event) {
    	
    	
    	
    	/*
    	 * bir fok yaz paths, secili turu ve isimleri alacak path ve isimler T G seklinde olabilr 
    	 * 
    	 * */
    	String text="";
    	if(txt_rename.getText() !=null) {
    		text=txt_rename.getText();
    	}
    	if (text !=null) {
    		
    		
    		
    		
    		combo_type.getValue();
    	}
    }

    @FXML
    void btn_open_file_click(ActionEvent event) {
    	/*
    	 * burada dosya yada dosyaları secip uzantısına gore combobox dan onları siliyorum 
    	 * mantıgımız jpg seciyorsam bunu tekrar jpg dosyasına donusturmem herhalde 
    	 * */
    	combo_type.getItems().clear();
   	  combo_type.getItems().addAll(options);
   	  	String text="";
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new ExtensionFilter("Tüm Dosyalar", "*.*");
        fileChooser.getExtensionFilters().add(filter);

        // Dosya seçme penceresini aç
        //file = fileChooser.showOpenDialog(new Stage());

        
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        List<String> uzantilar= new ArrayList<>();
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
            	// Dosya seçildiğinde yapılacak işlemler burada gerçekleştirilir
                System.out.println("Seçilen dosya: " + file.getAbsolutePath());
                TumPaths.add(file.getAbsolutePath());
                TumNames.add(file.getName());
                text+=file.getAbsolutePath()+"\n";
                lbl_path.setText(text);

                // Dosya adından uzantıyı alarak ComboBox'ı gerekli seçeneğe getir
                String filename = file.getName();
                String[] parcalar = filename.split("\\.");
                String extension = parcalar[parcalar.length - 1];
                System.out.println("uzantı "+extension);// Dosya uzantısı
            
                
                if(!uzantilar.contains(extension)) {
                	System.out.println(" ıcınde yokmu :"+!uzantilar.contains(extension));
                	uzantilar.add(extension);
                } 
            }
            for (String uzantı : uzantilar) {
            	combo_type.getItems().remove(uzantı);
            }
        }
        
        
        
        
       
    }

    @FXML
    void initialize() {
    	 combo_type.getItems().addAll(options);
    	 
    }

}
