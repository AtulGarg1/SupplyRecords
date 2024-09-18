package com.supplyrecord.supplyrecords.customComponents;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;
import java.util.stream.Collectors;

public class AutoCompleteTextField extends UppercaseTextField {
    private SortedSet<String> suggestions;
    private ContextMenu suggestionsPopup;

    public AutoCompleteTextField() {
        super();
        init();
    }

    public AutoCompleteTextField(ArrayList<String> suggestions) {
        super();
        init();
        this.suggestions.addAll(suggestions);
    }

    public AutoCompleteTextField(String text, ArrayList<String> suggestions) {
        super(text);
        init();
        this.suggestions.addAll(suggestions);
    }

    private void init() {
        this.suggestions = new TreeSet<>();
        this.suggestionsPopup = new ContextMenu();
        setListener();
        setKeyListener();
    }

    public SortedSet<String> getSuggestions() {
        return suggestions;
    }

    private void setKeyListener() {
        setOnKeyPressed(keyEvent -> {
            String lookup = "";
            if (keyEvent.getCode() == KeyCode.UP) {
                lookup = ".menu-up-arrow";
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                lookup = ".menu-down-arrow";
            }

            if (suggestionsPopup.isShowing() && !lookup.isEmpty()) {
                suggestionsPopup.getSkin().getNode().lookup(lookup).fireEvent(keyEvent);
            }
        });
    }

    private void setListener() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            if (enteredText == null || enteredText.isEmpty()) {
                suggestionsPopup.hide();
            } else {
                List<String> filteredSuggestions = suggestions.stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .collect(Collectors.toList());
                if (filteredSuggestions.isEmpty()) {
                    suggestionsPopup.hide();
                } else {
                    populatePopup(filteredSuggestions, enteredText);
                    if (!suggestionsPopup.isShowing()) {
                        suggestionsPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                }
            }
        });

        focusedProperty().addListener((observableValue, oldValue, newValue) -> suggestionsPopup.hide());
    }

    private void populatePopup(List<String> searchResult, String searchRequest) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);

        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                suggestionsPopup.hide();
            });
        }

        suggestionsPopup.getItems().clear();
        suggestionsPopup.getItems().addAll(menuItems);
    }

    private TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length()));
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
}
