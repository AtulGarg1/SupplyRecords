package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {
    public MenuItem menu_addBankAccount;
    public MenuItem menu_addItem;
    public MenuItem menu_addParty;
    public MenuItem menu_addPaymentsMade;
    public MenuItem menu_addPaymentsReceived;
    public MenuItem menu_addSupplyInwards;
    public MenuItem menu_addSupplyOutwards;
    public MenuItem menu_editPaymentsMade;
    public MenuItem menu_editPaymentsReceived;
    public MenuItem menu_editSupplyInwards;
    public MenuItem menu_editSupplyOutwards;
    public MenuItem menu_listPaymentsMade;
    public MenuItem menu_listPaymentsReceived;
    public MenuItem menu_listSupplyInwards;
    public MenuItem menu_listSupplyOutwards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu_addItem.setOnAction(event -> ViewFactory.getInstance().showAddItemWindow());
        menu_addParty.setOnAction(event -> ViewFactory.getInstance().showAddPartyWindow());
        menu_addBankAccount.setOnAction(event -> ViewFactory.getInstance().showAddBankAccountWindow());

        menu_addSupplyInwards.setOnAction(event -> setMenuItemSelected(ViewSelected.AddSupplyInwards));
        menu_editSupplyInwards.setOnAction(event -> setMenuItemSelected(ViewSelected.EditSupplyInwards));
        menu_listSupplyInwards.setOnAction(event -> setMenuItemSelected(ViewSelected.ListSupplyInwards));

        menu_addSupplyOutwards.setOnAction(event -> setMenuItemSelected(ViewSelected.AddSupplyOutwards));
        menu_editSupplyOutwards.setOnAction(event -> setMenuItemSelected(ViewSelected.EditSupplyOutwards));
        menu_listSupplyOutwards.setOnAction(event -> setMenuItemSelected(ViewSelected.ListSupplyOutwards));

        menu_addPaymentsMade.setOnAction(event -> ViewFactory.getInstance().showAddPaymentsMadeWindow());
        menu_editPaymentsMade.setOnAction(event -> setMenuItemSelected(ViewSelected.EditPaymentsMade));
        menu_listPaymentsMade.setOnAction(event -> setMenuItemSelected(ViewSelected.ListPaymentsMade));

        menu_addPaymentsReceived.setOnAction(event -> ViewFactory.getInstance().showAddPaymentsReceivedWindow());
        menu_editPaymentsReceived.setOnAction(event -> setMenuItemSelected(ViewSelected.EditPaymentsReceived));
        menu_listPaymentsReceived.setOnAction(event -> setMenuItemSelected(ViewSelected.ListPaymentsReceived));
    }

    private void setMenuItemSelected(String itemSelected) {
        ViewSelected.getInstance().setSelected(itemSelected);
    }
}
