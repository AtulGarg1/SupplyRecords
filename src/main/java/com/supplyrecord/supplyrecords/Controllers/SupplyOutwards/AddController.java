package com.supplyrecord.supplyrecords.Controllers.SupplyOutwards;

import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public GridPane gridPane;
    private int sno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sno = 1;
        setupGridPane();
    }

    private void setupGridPane() {
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);
        addHeaders();

        for (int i = 0; i < 5; i++) addRow();
    }

    private void addHeaders() {
        gridPane.add(new Text("S.No."), 0, 0);
        gridPane.add(new Text("Item"), 1, 0);
    }

    private void addRow() {
        Text sno = new Text(String.valueOf(this.sno));
        AutoCompleteTextField item = new AutoCompleteTextField(AutoSuggestions.ItemNames);

        int rowNo = gridPane.getRowCount();

        gridPane.add(sno, 0, rowNo);
        gridPane.add(item, 1, rowNo);

        this.sno++;
    }
}
