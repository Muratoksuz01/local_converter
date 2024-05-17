package application;

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
