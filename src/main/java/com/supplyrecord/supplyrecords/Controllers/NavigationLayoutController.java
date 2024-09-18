package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationLayoutController implements Initializable {
    public BorderPane nav_layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSelected.getInstance().getSelected().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case ViewSelected.AddSupplyInwards -> nav_layout.setCenter(ViewFactory.getInstance().getAddSupplyInwardsView());
                case ViewSelected.EditSupplyInwards -> nav_layout.setCenter(ViewFactory.getInstance().getEditSupplyInwardsView());
                case ViewSelected.EditRecordSupplyInwards -> nav_layout.setCenter(ViewFactory.getInstance().getEditRecordSupplyInwardsView());
                case ViewSelected.ListSupplyInwards -> nav_layout.setCenter(ViewFactory.getInstance().getListSupplyInwardsView());
                case ViewSelected.ListRecordSupplyInwards -> nav_layout.setCenter(ViewFactory.getInstance().getListRecordSupplyInwardsView());
                case ViewSelected.AddSupplyOutwards -> nav_layout.setCenter(ViewFactory.getInstance().getAddSupplyOutwardsView());
                case ViewSelected.EditSupplyOutwards -> nav_layout.setCenter(ViewFactory.getInstance().getEditSupplyOutwardsView());
                case ViewSelected.EditRecordSupplyOutwards -> nav_layout.setCenter(ViewFactory.getInstance().getEditRecordSupplyOutwardsView());
                case ViewSelected.ListSupplyOutwards -> nav_layout.setCenter(ViewFactory.getInstance().getListSupplyOutwardsView());
                case ViewSelected.ListRecordSupplyOutwards -> nav_layout.setCenter(ViewFactory.getInstance().getListRecordSupplyOutwardsView());
                default -> nav_layout.setCenter(ViewFactory.getInstance().getDashboardView());
            }
        }));
    }
}
