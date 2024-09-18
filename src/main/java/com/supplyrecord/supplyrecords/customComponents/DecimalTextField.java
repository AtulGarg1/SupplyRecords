package com.supplyrecord.supplyrecords.customComponents;

import javafx.scene.control.TextField;

public class DecimalTextField extends TextField {
    public DecimalTextField() {
        super();
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9]\\.[0-9]")) {
                setText(newValue.replaceAll("[^[0-9].[0-9]]", ""));
            }
        });
    }

    public DecimalTextField(String text) {
        this();
        setText(text);
    }
}
