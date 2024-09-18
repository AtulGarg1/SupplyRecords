package com.supplyrecord.supplyrecords.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewFactory {
    private static ViewFactory viewFactory;

    private AnchorPane dashboardView;

    private AnchorPane addSupplyInwardsView;
    private AnchorPane editSupplyInwardsView;
    private AnchorPane editRecordSupplyInwardsView;
    private AnchorPane listSupplyInwardsView;
    private AnchorPane listRecordSupplyInwardsView;

    private AnchorPane addSupplyOutwardsView;
    private AnchorPane editSupplyOutwardsView;
    private AnchorPane editRecordSupplyOutwardsView;
    private AnchorPane listSupplyOutwardsView;
    private AnchorPane listRecordSupplyOutwardsView;

    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }
        return viewFactory;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getAddSupplyInwardsView() {
        if (addSupplyInwardsView == null) {
            try {
                addSupplyInwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/Add.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addSupplyInwardsView;
    }

    public AnchorPane getEditSupplyInwardsView() {
        if (editSupplyInwardsView == null) {
            try {
                editSupplyInwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/Edit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editSupplyInwardsView;
    }

    public AnchorPane getEditRecordSupplyInwardsView() {
        if (editRecordSupplyInwardsView == null) {
            try {
                editRecordSupplyInwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/EditRecord.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editRecordSupplyInwardsView;
    }

    public AnchorPane getListSupplyInwardsView() {
        if (listSupplyInwardsView == null) {
            try {
                listSupplyInwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/List.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listSupplyInwardsView;
    }

    public AnchorPane getListRecordSupplyInwardsView() {
        if (listRecordSupplyInwardsView == null) {
            try {
                listRecordSupplyInwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/ListRecord.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listRecordSupplyInwardsView;
    }

    public AnchorPane getAddSupplyOutwardsView() {
        if (addSupplyOutwardsView == null) {
            try {
                addSupplyOutwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/Add.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addSupplyOutwardsView;
    }

    public AnchorPane getEditSupplyOutwardsView() {
        if (editSupplyOutwardsView == null) {
            try {
                editSupplyOutwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/Edit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editSupplyOutwardsView;
    }

    public AnchorPane getEditRecordSupplyOutwardsView() {
        if (editRecordSupplyOutwardsView == null) {
            try {
                editRecordSupplyOutwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/EditRecord.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editRecordSupplyOutwardsView;
    }

    public AnchorPane getListSupplyOutwardsView() {
        if (listSupplyOutwardsView == null) {
            try {
                listSupplyOutwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/List.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listSupplyOutwardsView;
    }

    public AnchorPane getListRecordSupplyOutwardsView() {
        if (listRecordSupplyOutwardsView == null) {
            try {
                listRecordSupplyOutwardsView =
                        new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/ListRecord.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listRecordSupplyOutwardsView;
    }

    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        Stage stage = setStage(fxmlLoader);
        stage.setResizable(false);
        stage.show();
    }

    public void showCreateFirmWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/CreateFirm.fxml"));
        Stage stage = setStage(fxmlLoader);
        stage.setResizable(false);
        stage.show();
    }

    public void showNavigationLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/NavigationLayout.fxml"));
        setStage(fxmlLoader).show();
    }

    public void showAddItemWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddItem.fxml"));
        Stage stage = setStage(fxmlLoader);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showAddPartyWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddParty.fxml"));
        Stage stage = setStage(fxmlLoader);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showAddBankAccountWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddBankAccount.fxml"));
        Stage stage = setStage(fxmlLoader);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private Stage setStage(FXMLLoader fxmlLoader) {
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Supply Records");
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stage;
    }
}
