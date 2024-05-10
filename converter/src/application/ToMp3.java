package application;



public class ToMp3<T> extends Converter<T>{
	String TypeName="mp3";
	 @Override
    void run(T inputFile,String outputFile) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
		 String[] donen=new String[2];
		 String parent,text;
	     String inputFilePath = (String) inputFile; // T tipini String'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							//kesinlikle bak buraaya
	     parent=donen[0];
	     text=donen[1];
	    
	        
	    String command = ffmpegPath + "\t" + "-i" + "\t" + inputFile + "\t" + parent + text;
	    System.out.println(command);
	    // soruncuz sadece cıkıs kodunu veriyor fakat
	 	String[] customCommand=command.split("\t");
	 	
	 	    
	 	try {
             // Komutu çalıştır
             Process process = Runtime.getRuntime().exec(customCommand);
             int exitCode = process.waitFor();
             System.out.println("Çıkış kodu: " + exitCode);
             if(exitCode!=0) {
            	 ErrorMassage(command);
             }
        } catch (Exception e) {
			System.out.println("run command foksiyonad hata var ");
		}
	 }
	
}