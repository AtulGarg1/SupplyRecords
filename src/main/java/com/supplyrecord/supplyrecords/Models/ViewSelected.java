package com.supplyrecord.supplyrecords.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewSelected {
    private static ViewSelected viewSelected;
    private final StringProperty selected;

    private ViewSelected() {
        selected = new SimpleStringProperty("");
    }

    public static synchronized ViewSelected getInstance() {
        if (viewSelected == null) {
            viewSelected = new ViewSelected();
        }
        return viewSelected;
    }

    public StringProperty getSelected() {
        return selected;
    }

    public void setSelected(String selectedViewName) {
        selected.setValue(selectedViewName);
    }

    public static final String Dashboard = "dashboard";

    public static final String AddSupplyInwards = "add_supply_inwards";
    public static final String EditSupplyInwards = "edit_supply_inwards";
    public static final String EditRecordSupplyInwards = "edit_record_supply_inwards";
    public static final String ListSupplyInwards = "list_supply_inwards";
    public static final String ListRecordSupplyInwards = "list_record_supply_inwards";

    public static final String AddSupplyOutwards = "add_supply_outwards";
    public static final String EditSupplyOutwards = "edit_supply_outwards";
    public static final String EditRecordSupplyOutwards = "edit_record_supply_outwards";
    public static final String ListSupplyOutwards = "list_supply_outwards";
    public static final String ListRecordSupplyOutwards = "list_record_supply_outwards";

    public static final String EditPaymentsMade = "edit_payments_made";
    public static final String ListPaymentsMade = "list_payments_made";

    public static final String EditPaymentsReceived = "edit_payments_received";
    public static final String ListPaymentsReceived = "list_payments_received";

    public static final String ListLedger = "list_ledger";
}
