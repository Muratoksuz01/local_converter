package application;
/*
 * png to jpg ffmpeg -i input.png output.jpg
 * jpg to png ffmpeg -i input.jpg output.png
 * pdf to jpg basşka bir tool bul   		ImageMagick
 * pdf to png basşka bir tool bul			ImageMagick
 * mp4 to mp3 ffmpeg -i input.mp4 -vn -acodec libmp3lame -qscale:a 2 output.mp3   // -gscale:a 1-5 arasında bir rakam 1 en iyisi
 * png or jpg to pdf     convert image1.jpg image2.jpg image3.jpg output.pdf     ImageMagick
 * */
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Button;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
/*
 * suan convert icin hazırız terminl kod lazım 
 * output path varsa uzerine yazılacaktır diye bir mesajbox
 * jpg to mp3 olmaz bunun onlemi   //bir arraylist jpgAvailable diye buna gecerli item lari ekleriz 
 * birden cok dosya secilmesi durumu özellikle jpg to pdf yaparken 
 * özel komut yzacagımız bir yer 
 * 
 * son olarak uzzntı ları iceren bir class yeni geldiginde sadece bir nesne uretecegimiz bir class
 * */
public class SampleController {
    @FXML    private Button openfile_btn;
    @FXML    private Button convert_btn;
    @FXML    private Button btn2;
    @FXML    private TextField textfield_txt;
    @FXML    private Label filepath_lbl;
    @FXML    private ContextMenu contextmenu;
    @FXML    private MenuItem sec1;
    @FXML    private MenuItem sec2;
    @FXML    private MenuItem sec3;
    @FXML    private MenuItem sec4;
    @FXML    private MenuItem sec5;
    
    
    
    
    File file ;
    ArrayList<MenuItem> items=new ArrayList<>();//global degisken 
    @FXML
    private void initialize() {
    	items.add(sec1);
    	items.add(sec2);
    	items.add(sec3);
    	items.add(sec4);
    	items.add(sec5);
    	/*
    	{ //burası sadece buton var ona hereyeyi burada ekledigimiz sistem 
    		
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("option1");
        MenuItem item2 = new MenuItem("option2");
        contextMenu.getItems().addAll(item1, item2);
        conbtn.setContextMenu(contextMenu);
        item1.setOnAction(event -> {
        	conbtn.setText("option1");
            System.out.println("Option 1 selected");
            // Add your custom logic here
        });
        item2.setOnAction(event -> {
        	conbtn.setText("option2");

            System.out.println("Option 2 selected");
            // Add your custom logic here
        });
    	}
    	 */
        
    	{// burası ise hereyi scenebuilder da tasarladimiz uygulama
    		
	        sec1.setOnAction(event ->{
	        	btn2.setText("png");            
	        });
	        sec2.setOnAction(event ->{
	        	btn2.setText("jpg");            
	        });
	        sec3.setOnAction(event ->{
	        	btn2.setText("mp4");            
	        });
	        sec4.setOnAction(event ->{
	        	btn2.setText("mp3");            
	        });
	        sec5.setOnAction(event ->{
	        	btn2.setText("pdf");            
	        });
    	}
    
    	
    	
    
    }
   
	

    @FXML
    private void openfile_btn_func() {// open file basınca file chocier acılacak 
        openfile_btn.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            //FileChooser.ExtensionFilter filter = new ExtensionFilter("Metin Dosyaları (*.txt)", "*.txt");
            FileChooser.ExtensionFilter filter2 = new ExtensionFilter("Tüm Dosyalar", "*.*");
            FileChooser.ExtensionFilter[] filters = {/*filter,*/ filter2};
            fileChooser.getExtensionFilters().addAll(filters);

            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                // Seçilen dosyayla işlem yapın...
                System.out.println("Seçilen dosya: " + file.getAbsolutePath());
                filepath_lbl.setText(file.getAbsolutePath());
                String filename = file.getName();
        		String[] parcalar = filename.split("\\.");
        		textfield_txt.setText(parcalar[0]);// sadece isim geliyor 
        		System.out.println("uzantı:"+parcalar[1]);
        		
                
        		hidemenuitem(parcalar[1]);
            }
        });
    }

    @FXML
    private void converter_btn_func() {						//burası kodlanacak 
    	System.out.println("-----------------");
    	//System.out.println(textfield_txt.getText());
    	//System.out.println(btn2.getText());
    	//System.out.println(filepath_lbl.getText());
    	//System.out.println(file.getParentFile());
    	
    	
    	
    }
    
    private void hidemenuitem(String uzanti) {
    	for (MenuItem item:items) {
    		if(item.getText().equals(uzanti)) {
    			item.setVisible(false);
    		}
    	}
    }
}
