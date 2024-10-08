package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.Constants;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public GridPane gridPane;
    public AutoCompleteTextField text_partyName;
    public DecimalTextField text_subTotal;
    public DecimalTextField text_biltiCharge;
    public DecimalTextField text_bardana;
    public DecimalTextField text_labourCost;
    public DecimalTextField text_commission;
    public DecimalTextField text_postage;
    public DecimalTextField text_bazaarCharges;
    public DecimalTextField text_otherExpenses;
    public DecimalTextField text_total;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        makeNotEditable(text_total);
        attachUpdateTotal();
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
        UppercaseTextField unit = new UppercaseTextField();
        DecimalTextField price = new DecimalTextField();
        DecimalTextField total = new DecimalTextField();

        makeNotEditable(sno, total);

        qty.setAlignment(Pos.CENTER_RIGHT);
        price.setAlignment(Pos.CENTER_RIGHT);
        total.setAlignment(Pos.CENTER_RIGHT);

        item.textProperty().addListener((observableVal, oldVal, newVal) -> changeUnit(unit, newVal));

        qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
        price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
        total.textProperty().addListener((observableVal, oldVal, newVal) -> updateSubTotal(oldVal, newVal));

        gridPane.add(sno, 0, rowNo);
        gridPane.add(item, 1, rowNo);
        gridPane.add(qty, 2, rowNo);
        gridPane.add(unit, 3, rowNo);
        gridPane.add(price, 4, rowNo);
        gridPane.add(total, 5, rowNo);
    }

    private void changeUnit(TextField tf_unit, String itemName) {
        String unit = AutoSuggestions.getUnit(itemName);
        if (!unit.equals("")) {
            tf_unit.setText(unit);
        }
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

    private void updateSubTotal(String oldVal, String newVal) {
        double subTotal =
                (isDouble(text_subTotal.getText()) ? Double.parseDouble(text_subTotal.getText()) : 0) -
                        (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                        (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_subTotal.setText(
                subTotal == 0 ? "" : String.format("%.2f", subTotal)
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
            SupplyRecord supplyInwardRecord =
                    new SupplyRecord(
                            -1, LocalData.getInstance().getFirmName(), partyName,
                            isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                            LocalDateTime.now(),
                            isDouble(text_biltiCharge.getText()) ? Double.parseDouble(text_biltiCharge.getText()) : 0,
                            isDouble(text_bardana.getText()) ? Double.parseDouble(text_bardana.getText()) : 0,
                            isDouble(text_labourCost.getText()) ? Double.parseDouble(text_labourCost.getText()) : 0,
                            isDouble(text_commission.getText()) ? Double.parseDouble(text_commission.getText()) : 0,
                            isDouble(text_postage.getText()) ? Double.parseDouble(text_postage.getText()) : 0,
                            isDouble(text_bazaarCharges.getText()) ? Double.parseDouble(text_bazaarCharges.getText()) : 0,
                            isDouble(text_otherExpenses.getText()) ? Double.parseDouble(text_otherExpenses.getText()) : 0,
                            true
                    );

            ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();

            int i = Constants.GRID_EDITABLE_CELL_START;
            for (; i <= Constants.GRID_EDITABLE_CELL_END; i += Constants.GRID_EDITABLE_CELL_INCREMENT) {
                String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
                String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
                String price = ((DecimalTextField) gridPane.getChildren().get(i + 3)).getText();

                if (item.isEmpty() && qty.isEmpty() && price.isEmpty()) {
                    continue;
                } else if (item.isEmpty()) {
                    displayError("An Item Name is missing");
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

            if (i > Constants.GRID_EDITABLE_CELL_END) {
                LocalData.getInstance().insertIntoSupplyInwardRecordsList(supplyInwardRecord);
                persistToDb(supplyInwardRecord, supplyItemDetails);
                ViewSelected.getInstance().setSelected(ViewSelected.Dashboard);
            }
        }
    }

    private void persistToDb(SupplyRecord supplyInwardRecord, ArrayList<SupplyItemDetail> supplyItemDetails) {
        db.addSupplyRecord(supplyInwardRecord);
        long recordId = db.getLatestRecordId();
        db.addSupplyItemDetails(supplyItemDetails, recordId);
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

    private void attachUpdateTotal() {
        text_subTotal.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_biltiCharge.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_bardana.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_labourCost.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_commission.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_postage.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_bazaarCharges.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_otherExpenses.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
    }
}
