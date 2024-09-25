package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public GridPane gridPane;
    public AutoCompleteTextField text_partyName;
    public DecimalTextField text_total;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
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
        label_err.setVisible(false);
        String partyName = text_partyName.getText().trim();

        if (partyName.isEmpty()) {
            displayError("Please enter the Party Name.");
        } else if (!AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party does not exist.");
        } else {
            SupplyInwardRecord supplyInwardRecord =
                    new SupplyInwardRecord(
                            -1, LocalData.getInstance().getFirmName(), partyName,
                            isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                            LocalDate.now()
                    );

            ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();

            int i = 6;
            for (; i <= 251; i += 5) {
                String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
                String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
                String price = ((DecimalTextField) gridPane.getChildren().get(i + 2)).getText();

                if (item.isEmpty() && qty.isEmpty() && price.isEmpty()) {
                    continue;
                } else if (item.isEmpty()) {
                    displayError("An Item Name is missing");
                    break;
                } else if (!AutoSuggestions.ItemNames.contains(item)) {
                    displayError("Item does not exist.");
                    break;
                } else if (!isDouble(qty)) {
                    displayError("A Quantity is missing or invalid.");
                    break;
                } else if (!isDouble(price)) {
                    displayError("A Price is missing or invalid.");
                    break;
                } else {
                    SupplyItemDetail supplyItemDetail =
                            new SupplyItemDetail(-1, item, Double.parseDouble(qty), Double.parseDouble(price));
                    supplyItemDetails.add(supplyItemDetail);
                }
            }

            if (i > 251) {
                db.addSupplyInwardRecord(supplyInwardRecord);
                long recordId = db.getLatestRecordId(); //TODO: figure out how to set recordId
                db.addSupplyItemDetails(supplyItemDetails);
                ViewSelected.getInstance().setSelected(ViewSelected.Dashboard);
            }
        }
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
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
