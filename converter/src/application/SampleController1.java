package application;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

/*
 * ffmpep i burada calıstırmıyorsun 
 * caliştirdiktan sonra class yapısına gecersin 
 * sonraki gunlerde merge islemini yaparsın 
 * mp4 de sade sesi cıkarma olayı sade goruntu cıkarma olayı ekle 
 * 
 * 
 * 
 * */

/*tek dosya  txt bos  /home/murar/deneme/yeni.png	/home/murar/deneme//yeni.jpg.jpg
 * tek doya text dolu sıkıntı yok
 * cok dosya text dolu patlıyor 
 * */

public class SampleController1 {
    ObservableList<String> options =FXCollections.observableArrayList("png","jpg","mp4","mp3","pdf");
    List<String> TumPaths=new ArrayList<String>();
    List<String> TumNames=new ArrayList<String>();
	File file;
	String ffmpegPath;
    @FXML    private ResourceBundle resources;
    @FXML    private URL location;
    @FXML    private Button btn_convert;
    @FXML    private Button btn_open_file;
    @FXML    private ComboBox<String> combo_type;
    @FXML    private Label lbl_path;
    @FXML    private TextField txt_rename;

    @FXML
    void btn_convert_click(ActionEvent event) {
    	String command, text, type, name, parent = "";
    	type = combo_type.getValue();
	    String ch = txt_rename.getText();
	    if(!ch.isEmpty() && !ch.equals("")) {//dolu ise 
		    
	    	for (String path : TumPaths) {
	    		//String[] parts = path.split("/"); // burayı düzenle sonra ve name değişkeni kullanılmadı
	    	    name=ch;
	    	    SetandRun(path, name, type);


		    }
	    }
	    else {// bos bıraktı ise 
	    	for (String path : TumPaths) {
	    		String[] parts = path.split("/"); // burayı düzenle sonra ve name değişkeni kullanılmadı
	    		name = parts[parts.length - 1]; // Dosya adı
	    	    name = name.substring(0, name.lastIndexOf('.'));
	    	    SetandRun(path, name, type);
		    }
	    }
    	
    }
    void SetandRun(String path,String name,String type) {
    	String text,command,parent;
		String[] parts = path.split("/"); // burayı düzenle sonra ve name değişkeni kullanılmadı

    	String[] parentArray = Arrays.copyOfRange(parts, 0, parts.length - 1);
  	    parent = String.join("/", parentArray)+"/";
  	    
  	    text=CheckFile(parent, name, type);
  	    text+="."+type;
  	    command = ffmpegPath + "\t" + "-i" + "\t" + path + "\t" + parent + text;
  	    System.out.println("name:"+name);
  	    System.out.println("parent:"+parent);
  	    System.out.println("text:"+text);
  	    System.out.println("command :"+command);
  	    run_command(command);
    }
    	
  
    @FXML
    void btn_open_file_click(ActionEvent event) {
    	/*
    	 * burada dosya yada dosyaları secip uzantısına gore combobox dan onları siliyorum 
    	 * mantıgımız jpg seciyorsam bunu tekrar jpg dosyasına donusturmem herhalde 
    	 * */
    	TumPaths.clear();TumNames.clear();
        File initialDirectory = new File("/home/murar/deneme/");
    	combo_type.getItems().clear();
   	  combo_type.getItems().addAll(options);
   	  	String text="";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter filter = new ExtensionFilter("Tüm Dosyalar", "*.*");
        fileChooser.getExtensionFilters().add(filter);

        // Dosya seçme penceresini aç
        //file = fileChooser.showOpenDialog(new Stage());

        
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        Set<String> uzantilar=new HashSet<String>();
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
            	// Dosya seçildiğinde yapılacak işlemler burada gerçekleştirilir
                System.out.println("Seçilen dosya: " + file.getAbsolutePath());//secilen dosyanın tum path
                System.out.println("parent name: "+file.getParent());
                TumPaths.add(file.getAbsolutePath());//liste ekliyoruz cunku birden fazla secmiş olabilir 
                
                TumNames.add(file.getName());
                text+=file.getAbsolutePath()+"\n";// secilen dosyaları gostermek icin bir duzenlelem ve yazdırma 
                lbl_path.setText(text);

                String filename = file.getName();
                String[] parts = filename.split("\\.");
                String extension=parts[parts.length -1];
                System.out.println("uzantı "+extension);// Dosya uzantısı
                uzantilar.add(extension);
                
                
            }
            
            for (String uzantı : uzantilar) {//combobox da secili uzantıları siliyoruz 
            	combo_type.getItems().remove(uzantı);
            }
        }
        
        
        
        
       
    }

    @FXML
    void initialize() {
    	 combo_type.getItems().addAll(options);
    	 
    	 String projectDir = System.getProperty("user.dir");
         
         // ffmpeg klasörünün yolu
         String ffmpegDir = Paths.get(projectDir, "ffmpeg").toString();
         
         // ffmpeg dosyasının tam yolu
         ffmpegPath = Paths.get(ffmpegDir, "ffmpeg").toString();
         
         // ffmpeg dosyasının varlığını kontrol edin
         java.io.File ffmpegFile = new java.io.File(ffmpegPath);
         if (ffmpegFile.exists()) {
             System.out.println("ffmpeg dosyası bulundu: " + ffmpegPath);
           
         } else {
             System.out.println("ffmpeg dosyası bulunamadı: " + ffmpegPath);
         }
        //System.out.println(CheckFile("/home/murar/deneme/","soru micro","png" ));
    }
    
    
    void run_command(String command) {
    
    	// soruncuz sadece cıkıs kodunu veriyor fakat
    	String[] customCommand=command.split("\t");
    	
    	/*
    	  String[] customCommand = {
                "/home/murar/eclipse-workspace/converter/ffmpeg/ffmpeg",
                "-i",
                "/home/murar/deneme/soru micro.png",
                "/home/murar/deneme/yeni resim.jpg"
    	  };
    	  */
   	
    	  
    	  //System.out.println("Komut Dizisi:");
          //for (String command1 : customCommand) {
          //    System.out.println(command1);
          //}         
    	try {
                // Komutu çalıştır
                Process process = Runtime.getRuntime().exec(customCommand);

                // İşlem çıktısını oku
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
               
                
                //String line;
                //while ((line = reader.readLine()) != null) {
                //    System.out.println(line);
                //}

                // İşlemi beklet ve çıkış kodunu al
                int exitCode = process.waitFor();
                System.out.println("Çıkış kodu: " + exitCode);

            } catch (Exception e) {
				System.out.println("run command foksiyonad hata var ");
			}

    }


	String CheckFile(String parent,String path,String type) {
		int i=0;
		String fullname,temp;
		temp=path;
		
		boolean check=true;
		do {
			fullname=parent+temp+"."+type;
			File file=new File(fullname);
			check=file.exists();
			if(check) {
				temp=path+String.valueOf(i);
				i++;
			}
		}while(check);
	return temp;
	}
	
	

}
/*//ls de calişiyor fakat cıktıyı vermıyor 
///String customCommand = command;
//String customCommand = "/home/murar/eclipse-workspace/converter/ffmpeg/ffmpeg -i '/home/murar/deneme/soru.png' -o /home/murar/deneme/yeni.jpg";
String customCommand="ls";
try {
	
	Process process = Runtime.getRuntime().exec(customCommand);
	int exitCode = process.waitFor();
	System.out.println(exitCode);
}catch (Exception e) {
	e.printStackTrace();
}
*/

/* // sorunsuz calişiyor
String customCommand = "ls";
try {
    Process process = Runtime.getRuntime().exec(customCommand);
    
    // İşlem çıktısını okumak için BufferedReader kullanın
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
        // Her bir satırı yazdırın veya işlem çıktısını başka bir şekilde işleyin
        System.out.println(line);
    }
    
    int exitCode = process.waitFor();
    System.out.println("Exit code: " + exitCode);
} catch (Exception e) {
    e.printStackTrace();
}
*/
