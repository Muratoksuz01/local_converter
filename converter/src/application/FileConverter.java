package application;

public class FileConverter <T>{
	T inputFile;
	String outputfile;
	Converter<T> converter;
	String tempValue;
	
	

	
	public FileConverter(T inputFile,  String outputFile, Converter converter,String tempValue) {
		this.inputFile = inputFile;
		this.outputfile = outputFile;
		this.converter = converter;
		this.tempValue=tempValue;
	}

	
	   public void convert() {
	        converter.run(inputFile,  outputfile,tempValue);
	    }

	    public static void main(String[] args) {
	        // FileConverter nesnesini burada oluşturarak parametreleri gönderebilirsiniz.
	  //      FileConverter<String> fileConverter = new FileConverter<>("inputfileconverter", "parent", "output", new ToPng<String>());
	 //       fileConverter.convert(); // Dönüşüm işlemini başlat
	    }
	
	
	
	
	

}
