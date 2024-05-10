package application;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Converter<T>{
	abstract void run(T inputFile,  String outputFile);
	
    String ffmpegPath;
    String convertPath;
     public Converter(){
    	 /*
    	  * ilk calişacak foksiyon buradan ffpegpath belirleyip sonraki foks.kullanılacak  
    	  * */
    	 String projectDir = System.getProperty("user.dir");
    	 // ffmpeg klasörünün yolu
        //String ffmpegDir = Paths.get(projectDir, "FFmpeg").toString();
        // ffmpeg dosyasının tam yolu
        ffmpegPath = Paths.get(projectDir, "ffmpeg").toString();
        // ffmpeg dosyasının varlığını kontrol edin
        java.io.File ffmpegFile = new java.io.File(ffmpegPath);
        if (ffmpegFile.exists()) {
            System.out.println("ffmpeg dosyası bulundu: " + ffmpegPath);
        } else {
            System.out.println("ffmpeg dosyası bulunamadı: " + ffmpegPath);
        }
        convertPath=Paths.get(projectDir,"convert").toString();
        
        java.io.File converterP = new java.io.File(ffmpegPath);
        if (converterP.exists()) {
            System.out.println("ffmpeg dosyası bulundu: " + convertPath);
        } else {
            System.out.println("ffmpeg dosyası bulunamadı: " + convertPath);
        }
	 }
	
    
	String[][] SetandRun(String[] path,String output,String type) {
    	/*
    	 * path: dosyanın uzun hali olacak c:vlsnvls/vds/vds.png gibi 
    	 * output: yeni olmasını istediginiz isim cıkıs ismi bu olacak ali gibi  
    	 * type: olmasını istediginiz uzantı sekli olacak jpg gibi 
    	 * Return :
    	 * 	dizi uc elemanı birleştirp gondermek icin 
    	 * path 2 elemanlaı dizi lack kesin 
    	 * output olacak degişken 
    	 * type kontrol icin 
    	 * */
		String[][] donecek=new String[3][1];
		String  parent, text; 	
	    String[] parts = ((String) path[0]).split("/");	
		String[] parentArray = Arrays.copyOfRange(parts, 0, parts.length - 1);
  	    parent = String.join("/", parentArray)+"/";
  	    text=CheckFile(parent, output, type);
	    text+="."+type;
	    System.out.println("parent:"+parent);
	    System.out.println("text:"+text);
	    System.out.println("array:"+Arrays.toString(path));
	    donecek[0][0] = parent;
        donecek[1][0] = text;
        donecek[2][0] =Arrays.toString(path); 
		
        return donecek;
		}
	String[] SetandRun(String path,String output,String type) {// ffmegPath, name, path  gonderecek sekilde ayarlanacak 
    	/*
    	 * path: dosyanın uzun hali olacak c:vlsnvls/vds/vds.png gibi
    	 * output: yeni olmasını istediginiz isim cıkıs ismi bu olacak ali gibi  
    	 * type: olmasını istediginiz uzantı sekli olacak jpg gibi 
    	 * Return :
    	 * 	dizi uc elemanı birleştirp gondermek icin 
    	 * */
		System.out.println("burasu set and run in string hali");
		String text,parent;
		// buraya liste geldiginde farklı string geldiginde farklı calişacak 
	    String[] parts = ((String) path).split("/");	
    	String[] parentArray = Arrays.copyOfRange(parts, 0, parts.length - 1);
  	    parent = String.join("/", parentArray)+"/";
		
	    text=CheckFile(parent, output, type);
  	    text+="."+type;
  	    //command = ffmpegPath + "\t" + "-i" + "\t" + path + "\t" + parent + text;
  	    System.out.println("name:"+output);
  	    System.out.println("parent:"+parent);
  	    System.out.println("text:"+text);
  	   // System.out.println("command :"+command);
    	String[] donecek = new String[2];
        donecek[0] = parent;
        donecek[1] = text;
  
      return donecek;
		
		}
  
	String CheckFile(String parent,String path,String type) {
		/*
		 * parent: uzun halii dosyanın vhslvs/vdnskj/vbsk gibi
		 * pathyeni olcak isim 
		 * type: uzantı olacak olanın
		 * return :
		 * sadece uzantısiz bir isim gonderecek 
		 * */
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
	
	void ErrorMassage(String command) {
		 Alert alert = new Alert(AlertType.INFORMATION);
	   	 alert.setTitle("Kücük bir problemimiz var...");
	   	 alert.setHeaderText("Yaptıgın işlemi kontrol edermisin :):)");
	   	 alert.setContentText("bu kodu kendi terminalinizden caliştirimisiniz :\n "+command +"\n sende hata alıyorsan işlemine takrar bak lütfen ;\n"
	   	 		+ "yoksa uzgunuz suanlık bu işlemı gerceklestıremıyoruz Anlayısınız için tesekkur ederiz ");
	   	 alert.showAndWait().ifPresent(rs -> {
	   	     if (rs == ButtonType.OK) {
	   	         System.out.println("Pressed OK.");
	   	     }
	   	 });
	 }
	
	
	
}
 
