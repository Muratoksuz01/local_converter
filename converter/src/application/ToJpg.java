package application;

import java.time.LocalDate;

public class ToJpg<T> extends Converter<T>{
	String TypeName="jpg";
	 @Override
    void run(T inputFile,String outputFile,String tempValue) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
		 String[] donen=new String[2];
		 String parent,text,command;
	     String inputFilePath = (String) inputFile; // T tipini String'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							
	     parent=donen[0];
	     text=donen[1];
	    
	    if(tempValue.equals("pdf")) {
	    	System.out.println("text name:"+text);
	    	System.out.println("parent name:"+parent);
	    	command=convertPath+"\t"+inputFile+"\t"+parent+text+"%d."+TypeName;
	    }
	    else {
	    	
	    	command = ffmpegPath + "\t" + "-i" + "\t" + inputFile + "\t" + parent + text+"."+TypeName;
	    }
	    System.out.println(command);
	    // soruncuz sadece cıkıs kodunu veriyor fakat
	 	String[] customCommand=command.split("\t");
	     
	 	try {
             // Komutu çalıştır
             Process process = Runtime.getRuntime().exec(customCommand);
             int exitCode = process.waitFor();
             if(exitCode!=0) {
            	 ErrorMassage(command);
            	 SaveLog(command,String.valueOf(LocalDate.now()) , String.valueOf(exitCode));

             }else {
            	 SaveLog(command,String.valueOf(LocalDate.now()) , String.valueOf(exitCode));
             }
            
             System.out.println("Çıkış kodu: " + exitCode);
        } catch (Exception e) {
			System.out.println("run command foksiyonad hata var ");
		}
	 }
	
}
