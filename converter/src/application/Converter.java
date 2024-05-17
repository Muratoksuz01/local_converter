package application;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Converter<T>{
	abstract void run(T inputFile,  String outputFile,String tempValue);
	  Connection c = null;
		Statement stmt = null;
    String ffmpegPath;
    String convertPath;
    String databasePath;
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
        databasePath=Paths.get(projectDir,"Convert.db").toString();
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
  	    //text+="."+type;
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
	   	 alert.setContentText(command+ " Anlayısınız için tesekkur ederiz ");
	   	 alert.showAndWait().ifPresent(rs -> {
	   	     if (rs == ButtonType.OK) {
	   	         System.out.println("Pressed OK.");
	   	     }
	   	 });
	 }
	
	void SaveLog(String command,String date,String isSuccess) {
		try {
            connect();
            Statement stmt = c.createStatement();
            //String sql = "INSERT INTO convert VALUES("+command+","+date+","+isSuccess+");";
            String sql="INSERT INTO convert (command, date, isSuccess)  VALUES('%s','%s','%s');".formatted(command,date,isSuccess);
            System.out.println( sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            
            // Veritabanından silindikten sonra tabloyu güncelle
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	protected void connect() {
     	 
   		try {
   			//Class.forName("org.sqlite.JDBC");
   		      c = DriverManager.getConnection("jdbc:sqlite:"+databasePath);
   		      c.setAutoCommit(false);
   		      System.out.println("Opened database successfully");

   		} catch (SQLException e) {
        	System.out.println("baglanmadı");
            System.out.println(e.getMessage());
        }
      	
      	
      }
	
	  // Dosya yolundan uzantıyı almak için yardımcı metot
	private  String getFileExtension(String dosyaYolu) {
        File file = new File(dosyaYolu);
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // Uzantı yoksa
        }
        return fileName.substring(lastIndexOfDot + 1);
    }
    
 // Uzantının kontrol listesinde olup olmadığını kontrol etmek için yardımcı metot
     private boolean isExtensionInList(String uzanti, String[] kontrolUzantilari) {
        for (String kontrolUzantisi : kontrolUzantilari) {
            if (uzanti.equalsIgnoreCase(kontrolUzantisi)) {
                return true;
            }
        }
        return false;
    }
    
     boolean isAllExtenssionCorrect(String[] kontrolUzantilari,String[] dosyaYolları) {
    	boolean allCorrect=true;
    	for (String dosyaYolu : dosyaYolları) {
            String uzanti = getFileExtension(dosyaYolu);
            if (isExtensionInList(uzanti, kontrolUzantilari)) {
                System.out.println(dosyaYolu + " dosyasının uzantısı (" + uzanti + ") kontrol listesinde var.");
            } else {
                System.out.println(dosyaYolu + " dosyasının uzantısı (" + uzanti + ") kontrol listesinde yok.");
                allCorrect=false;
                return allCorrect;
            }
        }
    	return allCorrect;
    }
     boolean isAllExtenssionCorrect(String[] kontrolUzantilari,String dosyaYolları) {
     	boolean allCorrect=true;
             String uzanti = getFileExtension(dosyaYolları);
             if (isExtensionInList(uzanti, kontrolUzantilari)) {
                 System.out.println(dosyaYolları + " dosyasının uzantısı (" + uzanti + ") kontrol listesinde var.");
             } else {
                 System.out.println(dosyaYolları + " dosyasının uzantısı (" + uzanti + ") kontrol listesinde yok.");
                 allCorrect=false;
                 return allCorrect;
             }
       
     	return allCorrect;
     }
	
	
}
 
