package com.supplyrecord.supplyrecords;

import com.supplyrecord.supplyrecords.Views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ViewFactory.getInstance().showLoginWindow();
    }
}
