package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public GridPane gridPane;
    public UppercaseTextField text_supplierName;
    public DecimalTextField text_total;
    public Button btn_save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeNotEditable(text_total);
        setupGridPane();
    }

    private void setupGridPane() {
        for (int i = 0; i < 50; i++) addRow();
    }

    private void addRow() {
        int rowNo = gridPane.getRowCount();

        TextField sno = new TextField(rowNo + ".");
        AutoCompleteTextField item = new AutoCompleteTextField(AutoSuggestions.ItemNames);
        DecimalTextField qty = new DecimalTextField();
        DecimalTextField price = new DecimalTextField();
        DecimalTextField total = new DecimalTextField();

        makeNotEditable(sno, total);

        qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
        price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
        total.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));

        gridPane.add(sno, 0, rowNo);
        gridPane.add(item, 1, rowNo);
        gridPane.add(qty, 2, rowNo);
        gridPane.add(price, 3, rowNo);
        gridPane.add(total, 4, rowNo);
    }

    private void updateItemTotal(DecimalTextField tf_qty, DecimalTextField tf_price, DecimalTextField tf_total) {
        String qty = tf_qty.getText();
        String price = tf_price.getText();

        double itemTotal =
                (isDouble(qty) ? Double.parseDouble(qty) : 0) *
                (isDouble(price) ? Double.parseDouble(price) : 0);

        tf_total.setText(
                itemTotal == 0 ? "" : String.format("%.2f", itemTotal)
        );
    }

    private void updateTotal(String oldVal, String newVal) {
        double total =
                (isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0) -
                (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_total.setText(
                total == 0 ? "" : String.format("%.2f", total)
        );
    }

    public void onSave() {
        SupplyInwardRecord supplyInwardRecord =
                new SupplyInwardRecord(
                        -1, LocalData.getInstance().getFirmName(), text_supplierName.getText(),
                        isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        LocalDate.now()
                );

        // TODO: insert supplyInwardRecord in DB and fetch its recordId
        long recordId = -1;

        ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();
        for (int i = 6; i <= 251; i += 5) {
            String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
            String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
            String price = ((DecimalTextField) gridPane.getChildren().get(i + 2)).getText();

            if (isValid(item, qty, price)) {
                SupplyItemDetail supplyItemDetail =
                        new SupplyItemDetail(recordId, item, Double.parseDouble(qty), Double.parseDouble(price));
                supplyItemDetails.add(supplyItemDetail);
            }
        }

        // TODO: add supplyItemDetails to DB
    }

    private boolean isValid(String item, String qty, String price) {
        return !item.isEmpty() && isDouble(qty) && isDouble(price);
    }

    private boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void makeNotEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }
}
