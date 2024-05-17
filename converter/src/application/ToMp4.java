package application;


import java.time.LocalDate;

//ffmpeg -i $input_file -c copy -an $output_file

public class ToMp4<T> extends Converter<T>{
	String TypeName="mp4";
    String[] kontrolUzantilari = {"mp4", "mp3"};

	 @Override
    void run(T inputFile,String outputFile,String tempValue) {// dosyaları setup run foksiınndan glecek commandı hazırla sonra run 
		 String[][] donen=new String[3][];
		 //String[] donenpath=new String[2];
		 String parent,command="",text,mp3file = null, mp4file = null,aa;
	     String[] inputFilePath = (String[]) inputFile; // T tipini String dizisine 'e dönüştür
		 donen=SetandRun(inputFilePath,outputFile,TypeName);							
	     parent=donen[0][0];
	     text=donen[1][0];
	     aa=donen[2][0];
	     String content = aa.substring(1, aa.length() - 1);//bastakı ev sondaki '[ ]' işaretlerini kaldırdım 
	     // Virgülle ayrılmış parçaları ayırın
	     String[] donenpath = content.split(",");
	     if(isAllExtenssionCorrect(kontrolUzantilari,donenpath)) {
		  
	  
		     System.out.println(donenpath.length);
		     
		     if(tempValue.equals("true")) {	
		    	if(inputFilePath.length==1)
		    		command=ffmpegPath+"\t"+"-i"+"\t"+inputFilePath[0]+"\t-c\tcopy\t-an\t"+parent+text;
		    	else {
		    		ErrorMassage("galiba mp4 dosyasından sesi silmeye calişiyorsunuz bunun icin sadece mp4 dosyasının secili olması gerekli !!!");
		    	}
		     }else {
		    	 System.out.println("else kısmında ");
			     if (donenpath.length!=2) {
					System.out.println("if kısmında ");		
					System.out.println(donenpath.length);
					ErrorMassage("galiba mp3 ile mp4 u birleştirmke istiyorusunuz anladım dakat sadece 2 degişken olmalı ve bunlar mp3 ve mp4 dosyası olmalı ");														//hata fırlat
			     }else {
						if(donenpath[0].endsWith("mp4")) {
							mp4file=donenpath[0].trim();
							mp3file=donenpath[1].trim();
						}else {
							mp3file=donenpath[0].trim();
							mp4file=donenpath[1].trim();
						}
						System.out.println("burada");
				        //ffmpeg -i video.mp4 -i audio.mp3 -c:v copy -c:a aac output.mp4   //ornek kod 
					     command = ffmpegPath + "\t" + "-i" + "\t" + mp4file + "\t" +"-i" + "\t"+mp3file +
					    "\t" +"-c:v"+ "\t" +"copy" + "\t" + "-c:a"+ "\t" +"aac"+ "\t" + parent + text;
					   
			       }
		     }
	
		     System.out.println("komut işlemi:"+command);
			 // soruncuz sadece cıkıs kodunu veriyor fakat
			 String[] customCommand=command.split("\t");
			 	
			 	    
		 	try {
	             // Komutu çalıştır
	             Process process = Runtime.getRuntime().exec(customCommand);
	             int exitCode = process.waitFor();
	             System.out.println("Çıkış kodu: " + exitCode);
	             if(exitCode!=0) {
	            	// SaveLog(command,String.valueOf(LocalDate.now()) , String.valueOf(exitCode));
	
	            	 ErrorMassage(command);
	             }else {
	            	 SaveLog(command,String.valueOf(LocalDate.now()) , String.valueOf(exitCode));
	             }
	        } catch (Exception e) {
	       	 	//ErrorMassage(command);
	
				System.out.println("run command foksiyonad hata var ");
			}
	     
	  }else {
		  ErrorMassage("uzgunuz suan bu cevirme işlemini yapamıyoruz ileriki hayatınızda belki kullanırsınız ");
	  }
	     
	     
	     
	     
	     

	 }
	
	 
	 
	 
	 
}
