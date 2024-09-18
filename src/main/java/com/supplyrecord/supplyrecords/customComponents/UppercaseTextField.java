package com.supplyrecord.supplyrecords.customComponents;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class UppercaseTextField extends TextField {
    public UppercaseTextField() {
        super();
        addFormatter();
    }

    public UppercaseTextField(String text) {
        super(text);
        addFormatter();
    }

    private void addFormatter() {
        setTextFormatter(
                new TextFormatter<>(change -> {
                    change.setText(change.getText().toUpperCase());
                    return change;
                })
        );
    }
}
