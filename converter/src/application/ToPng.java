package application;



public class ToPng<T> extends Converter<T>{
	String TypeName="png";
	@Override
    void run(T inputFile,String outputFile,String tempValue) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
	
		 String[] donen=new String[2];
		 String parent,text,command;
	     String inputFilePath = (String) inputFile; // T tipini String'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							
	     parent=donen[0];
	     text=donen[1];
	    
	    if(tempValue.equals("pdf")) {
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
             System.out.println("Çıkış kodu: " + exitCode);
             if(exitCode!=0) {
            	 ErrorMassage(command);
             }
         } catch (Exception e) {
			System.out.println("run command foksiyonad hata var ");
		 }
			
	 }
	
}
