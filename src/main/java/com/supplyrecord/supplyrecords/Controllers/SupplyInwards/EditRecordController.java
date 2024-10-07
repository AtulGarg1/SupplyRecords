package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.Constants;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public GridPane gridPane;
    public DecimalTextField text_total;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;
    private static final ObjectProperty<SupplyRecord> record = new SimpleObjectProperty<>();
    private ArrayList<SupplyItemDetail> oldSupplyItemDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        makeNotEditable(text_total);
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        SupplyRecord supplyInwardRecord = record.getValue();

        text_partyName.setText(String.valueOf(supplyInwardRecord.partyName()));

        oldSupplyItemDetails =
                db.fetchSupplyInwardItemDetailsFor(supplyInwardRecord.recordId());

        for (SupplyItemDetail supplyItemDetail : oldSupplyItemDetails) {
            int rowNo = gridPane.getRowCount();
            double itemTotal = supplyItemDetail.qty() * supplyItemDetail.price();

            TextField sno = new TextField(rowNo + ".");
            AutoCompleteTextField itemName = new AutoCompleteTextField(supplyItemDetail.itemName(), AutoSuggestions.ItemNames);
            DecimalTextField qty = new DecimalTextField(String.valueOf(supplyItemDetail.qty()));
            UppercaseTextField unit = new UppercaseTextField(AutoSuggestions.getUnit(supplyItemDetail.itemName()));
            DecimalTextField price = new DecimalTextField(String.valueOf(supplyItemDetail.price()));
            DecimalTextField total = new DecimalTextField(String.valueOf(itemTotal));

            makeNotEditable(sno, total);

            qty.setAlignment(Pos.CENTER_RIGHT);
            price.setAlignment(Pos.CENTER_RIGHT);
            total.setAlignment(Pos.CENTER_RIGHT);

            itemName.textProperty().addListener((observableVal, oldVal, newVal) -> changeUnit(unit, newVal));

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(unit, 3, rowNo);
            gridPane.add(price, 4, rowNo);
            gridPane.add(total, 5, rowNo);
        }

        text_total.setText(String.valueOf(supplyInwardRecord.totalAmount()));
        addEmptyRows();
    }

    private void addEmptyRows() {
        for (int rowNo = gridPane.getRowCount(); rowNo <= 50; rowNo++) {
            TextField sno = new TextField(rowNo + ".");
            AutoCompleteTextField itemName = new AutoCompleteTextField(AutoSuggestions.ItemNames);
            DecimalTextField qty = new DecimalTextField();
            UppercaseTextField unit = new UppercaseTextField();
            DecimalTextField price = new DecimalTextField();
            DecimalTextField total = new DecimalTextField();

            makeNotEditable(sno, total);

            qty.setAlignment(Pos.CENTER_RIGHT);
            price.setAlignment(Pos.CENTER_RIGHT);
            total.setAlignment(Pos.CENTER_RIGHT);

            itemName.textProperty().addListener((observableVal, oldVal, newVal) -> changeUnit(unit, newVal));

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(unit, 3, rowNo);
            gridPane.add(price, 4, rowNo);
            gridPane.add(total, 5, rowNo);
        }
    }

    private void changeUnit(TextField tf_unit, String itemName) {
        String unit = AutoSuggestions.getUnit(itemName);
        if (!unit.equals("")) {
            tf_unit.setText(unit);
        }
    }

    public void onSave() {
        label_err.setVisible(false);
        String partyName = text_partyName.getText().trim();

        if (partyName.isEmpty()) {
            displayError("Please enter the Party Name.");
        } else if (!AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party does not exist.");
        } else {
            long recordId = record.getValue().recordId();

            // TODO: add charges fields
            SupplyRecord supplyInwardRecord =
                    new SupplyRecord(
                            recordId, LocalData.getInstance().getFirmName(), partyName,
                            isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                            LocalDate.now(),
                            0, 0, 0, 0, 0, 0, 0, true
                    );

            ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();

            int i = Constants.GRID_EDITABLE_CELL_START;
            for (; i <= Constants.GRID_EDITABLE_CELL_END; i += Constants.GRID_EDITABLE_CELL_INCREMENT) {
                String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
                String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
                String price = ((DecimalTextField) gridPane.getChildren().get(i + 2)).getText();

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
                            new SupplyItemDetail(recordId, item, Double.parseDouble(qty), Double.parseDouble(price));
                    supplyItemDetails.add(supplyItemDetail);
                }
            }

            if (i > Constants.GRID_EDITABLE_CELL_END) {
                LocalData.getInstance().updateSupplyInwardRecordsList(supplyInwardRecord);
                persistToDb(supplyInwardRecord, supplyItemDetails);
                ViewSelected.getInstance().setSelected(ViewSelected.Dashboard);
            }
        }
    }

    private void persistToDb(SupplyRecord supplyInwardRecord, ArrayList<SupplyItemDetail> newList) {
        db.updateSupplyRecord(supplyInwardRecord);
        diff(oldSupplyItemDetails, newList, supplyInwardRecord.recordId());
    }

    private void diff(ArrayList<SupplyItemDetail> oldList, ArrayList<SupplyItemDetail> newList, long recordId) {
        ArrayList<SupplyItemDetail> toBeRemoved = new ArrayList<>();
        ArrayList<SupplyItemDetail> toBeInserted = new ArrayList<>();
        int lim = Math.min(oldList.size(), newList.size());

        for (int i = 0; i < lim; i++) {
            if (oldList.get(i) != newList.get(i)) {
                toBeRemoved.add(oldList.get(i));
                toBeInserted.add(newList.get(i));
            }
        }

        for (int i = lim; i < newList.size(); i++) {
            toBeInserted.add(newList.get(i));
        }

        db.deleteSupplyInwardItemDetails(toBeRemoved);
        db.addSupplyInwardItemDetails(toBeInserted, recordId);
    }

    private void makeNotEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
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

    private void updateTotal(String oldVal, String newVal) {
        double total =
                (isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0) -
                        (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                        (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_total.setText(
                total == 0 ? "" : String.format("%.2f", total)
        );
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

    public static void setRecord(SupplyRecord record) {
        EditRecordController.record.set(record);
    }
}
