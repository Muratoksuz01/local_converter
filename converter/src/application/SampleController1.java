package application;




import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * mp4 ise silme sesi ozzellik gelsin  					input_file=example.mkv
														output_file=example-nosound.mkv
														
														ffmpeg -i $input_file -c copy -an $output_file
 * 
 * 
 * pdf ten png or jpg gecme 			convert   in.pdf out%d.png
 */



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
    	
	    
	    String type = combo_type.getValue();
	    String name = txt_rename.getText().trim();    
	    if(type.equals("jpg")) {	    	
		    for (String path : TumPaths) {
		        String[] parts = path.split("/"); 
		        String fileName = name.isEmpty() ? parts[parts.length - 1].replaceAll("\\..*$", "") : name;
		        FileConverter<String> aa = new FileConverter<>(path,fileName, new ToJpg<String>());        
		        aa.convert();
		    }
	    }
	    else if(type.equals("png")) {	    	
		    for (String path : TumPaths) {
		        String[] parts = path.split("/"); 
		        String fileName = name.isEmpty() ? parts[parts.length - 1].replaceAll("\\..*$", "") : name;
		        FileConverter<String> aa = new FileConverter<>(path,fileName, new ToPng<String>());        
		        aa.convert();
		    }
	    }
	    else if(type.equals("mp3")){
	    	for (String path : TumPaths) {
		        String[] parts = path.split("/"); 
		        String fileName = name.isEmpty() ? parts[parts.length - 1].replaceAll("\\..*$", "") : name;
		        FileConverter<String> aa = new FileConverter<>(path,fileName, new ToMp3<String>());        
		        aa.convert();
		    }
	    }
	    else if(type.equals("mp4")){
	    	String[] TumPathsArray = new String[TumPaths.size()]; // ArrayList'in boyutu kadar bir dizi oluşturun
	    	TumPaths.toArray(TumPathsArray);
	    	String[] parts = TumPathsArray[0].split("/"); 
	        String fileName = name.isEmpty() ? parts[parts.length - 1].replaceAll("\\..*$", "") : name;
	    	FileConverter<String[]> aa = new FileConverter<>(TumPathsArray,fileName, new ToMp4<String[]>());        
	        aa.convert();
	    }
	    else if(type.equals("pdf")){
	    	String[] TumPathsArray = new String[TumPaths.size()]; // ArrayList'in boyutu kadar bir dizi oluşturun
	    	TumPaths.toArray(TumPathsArray);
	    	String[] parts = TumPathsArray[0].split("/"); 
	        String fileName = name.isEmpty() ? parts[parts.length - 1].replaceAll("\\..*$", "") : name;
	    	FileConverter<String[]> aa = new FileConverter<>(TumPathsArray,fileName, new ToPdf<String[]>());        
	        aa.convert();
	    }
	    
	    else {
	    	System.out.println("suanda ulasılmıyor !!!");
	    }
 
	    
	    
	    
	    
    }
 	
  
    @FXML
    void btn_open_file_click(ActionEvent event) {
    
    	TumPaths.clear();TumNames.clear();
        File initialDirectory = new File("/home/murar/deneme/");
    	combo_type.getItems().clear();
   	  combo_type.getItems().addAll(options);
   	  	String text="";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter filter = new ExtensionFilter("Tüm Dosyalar", "*.*");
        fileChooser.getExtensionFilters().add(filter);

       
        
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        Set<String> uzantilar=new HashSet<String>();
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
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
            /*
            for (String uzantı : uzantilar) {//combobox da secili uzantıları siliyoruz 
            	combo_type.getItems().remove(uzantı);
            }
            */
        }
        
        
        
        
       
    }

    @FXML
    void initialize() {
    	 combo_type.getItems().addAll(options);
    	
    	 /*
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
         */
      //   aa.convert();																			//OOP
    
    //run_command("/home/murar/eclipse-workspace/converter/src/application/convert /home/murar/deneme/yeni.jpg /home/murar/deneme/alilera.pdf");
    }
    
    
    void run_command(String command) {
    
    	try {
    		
    	String komut=command;
    	System.out.println(komut);
        Process process = Runtime.getRuntime().exec(komut);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String satir;
        while ((satir = reader.readLine()) != null) {
            System.out.println(satir);
        }

        int exitCode = process.waitFor();
        System.out.println("Çıkış kodu: " + exitCode);

        reader.close();
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
		
    }
     
    void SetandRun(String path,String name,String type) {
    	/*
    	 * path: dosyanın uzun hali olacak c:vlsnvls/vds/vds.png gibi
    	 * name: yeni olmasını istediginiz isim cıkıs ismi bu olacak ali gibi  
    	 * type: olmasını istediginiz uzantı sekli olacak jpg gibi 
    	 * Return :
    	 * 	dizi uc elemanı birleştirp gondermek icin 
    	 * */
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

