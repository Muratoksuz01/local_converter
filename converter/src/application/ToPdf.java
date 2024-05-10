package application;

public class ToPdf<T> extends Converter<T>{
	String TypeName="pdf";
	 @Override
    void run(T inputFile,String outputFile) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
		 String[][] donen=new String[3][];
		 String command=convertPath+" ";
		 //String[] donenpath=new String[5];
		 String parent,text,mp3file = null, mp4file = null,aa;
	     String[] inputFilePath = (String[]) inputFile; // T tipini String dizisine 'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							
	     parent=donen[0][0];
	     text=donen[1][0];
	     aa=donen[2][0];
	     String content = aa.substring(1, aa.length() - 1);
	  // Virgülle ayrılmış parçaları ayırın
	  String[] donenpath = content.split(",");
	     System.out.println(donenpath.length);
	     
	     for(String path:donenpath) {
	    	 path=path.trim();
	    	 if(path.endsWith("jpg") || path.endsWith("png")) {
	    	System.out.println("burada olacak ");
	    		 command += path+" ";
	    	 }
	     }
	 
	    command+=parent+text;
		System.out.println(command);
	    // soruncuz sadece cıkıs kodunu veriyor fakat
	 	String[] customCommand=command.split(" ");
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
