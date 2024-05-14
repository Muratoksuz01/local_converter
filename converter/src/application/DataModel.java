package application;
/*
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class DataModel {
	 private final Integer id;
	    private final String command;
	    private final String date;
	    private final boolean isSuccess;

	    public DataModel(Integer id, String command,String date,boolean isSuccess) {
	        this.id = id;
	        this.command = command;
	        this.date = date;
	        this.isSuccess = isSuccess;
	        //System.out.println("ID: " + id + ", Command: " + command + ", Date: " + date + ", isSuccess: " + isSuccess);

	        
	    }

		public Integer getId() {
			return id;
		}

		public String getCommand() {
			return command;
		}

		public String getDate() {
			return date;
		}

		public String GetSuccess() {
			return  String.valueOf(isSuccess);
		}
		
		

	    
}*/




import javafx.beans.property.*;

public class DataModel {
    private final IntegerProperty id;
    private final StringProperty command;
    private final StringProperty date;
    private final BooleanProperty success;

    public DataModel(int id, String command, String date, boolean success) {
        this.id = new SimpleIntegerProperty(id);
        this.command = new SimpleStringProperty(command);
        this.date = new SimpleStringProperty(date);
        this.success = new SimpleBooleanProperty(success);
    }

    // Getter methods for properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty commandProperty() {
        return command;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public BooleanProperty successProperty() {
        return success;
    }

    // Getter methods for fields (optional)
    public int getId() {
        return id.get();
    }

    public String getCommand() {
        return command.get();
    }

    public String getDate() {
        return date.get();
    }

    public boolean isSuccess() {
        return success.get();
    }
}
