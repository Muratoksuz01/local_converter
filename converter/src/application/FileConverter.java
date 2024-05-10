package application;

public class FileConverter <T>{
	T inputFile;
	String outputfile;
	Converter<T> converter;
	
	
	

	
	public FileConverter(T inputFile,  String outputFile, Converter converter) {
		this.inputFile = inputFile;
		this.outputfile = outputFile;
		this.converter = converter;
	}

	
	   public void convert() {
	        converter.run(inputFile,  outputfile);
	    }

	    public static void main(String[] args) {
	        // FileConverter nesnesini burada oluşturarak parametreleri gönderebilirsiniz.
	  //      FileConverter<String> fileConverter = new FileConverter<>("inputfileconverter", "parent", "output", new ToPng<String>());
	 //       fileConverter.convert(); // Dönüşüm işlemini başlat
	    }
	
	
	
	
	

}
