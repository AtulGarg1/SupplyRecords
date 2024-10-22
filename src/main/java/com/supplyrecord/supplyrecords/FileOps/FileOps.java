package com.supplyrecord.supplyrecords.FileOps;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class FileOps {
    public static String getFileLocation(Window window) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("PDF file (*.pdf)", "*.pdf");

        fileChooser.setInitialFileName("Invoice");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(window);
        return file == null ? "" : file.getAbsolutePath();
    }

    public static void openFile(String path) {
        new Application() {
            @Override public void start(Stage stage) {}
        }.getHostServices().showDocument(path);
    }
}
