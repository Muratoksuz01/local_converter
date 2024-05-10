package application;



public class ToMp4<T> extends Converter<T>{
	String TypeName="mp4";
	 @Override
    void run(T inputFile,String outputFile) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
		 String[][] donen=new String[3][];
		 String[] donenpath=new String[2];
		 String parent,text,mp3file = null, mp4file = null;
	     String[] inputFilePath = (String[]) inputFile; // T tipini String dizisine 'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							
	     parent=donen[0][0];
	     text=donen[1][0];
	     donenpath=donen[2];
	     
	     if (donenpath.length!=2) {
																					//hata fırlat
	     }else {
				if(donenpath[0].endsWith("mp4")) {
					mp4file=donenpath[0];
					mp3file=donenpath[1];
				}else {
					mp3file=donenpath[0];
					mp4file=donenpath[1];
				}
	     
		        //ffmpeg -i video.mp4 -i audio.mp3 -c:v copy -c:a aac output.mp4   //ornek kod 
			    String command = ffmpegPath + "\t" + "-i" + "\t" + mp4file + "\t" +"-i" + "\t"+mp3file +
			    "\t" +"-c:v"+ "\t" +"copy" + "\t" + "-c:a"+ "\t" +"aac"+ "\t" + parent + text;
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
	 
}
