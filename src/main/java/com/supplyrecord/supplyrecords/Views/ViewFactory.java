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

    private AnchorPane editPaymentsMadeView;
    private AnchorPane listPaymentsMadeView;

    private AnchorPane editPaymentsReceivedView;
    private AnchorPane listPaymentsReceivedView;

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
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/Add.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AnchorPane getEditSupplyInwardsView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/Edit.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyInwards/List.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/Add.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AnchorPane getEditSupplyOutwardsView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/Edit.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/SupplyOutwards/List.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public void showAddPaymentsMadeWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsMade/Add.fxml"));
        Stage stage = setStage(fxmlLoader, "Add Payment Made");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showAddPaymentsReceivedWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsReceived/Add.fxml"));
        Stage stage = setStage(fxmlLoader, "Add Payment Received");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public AnchorPane getEditPaymentsMadeView() {
        if (editPaymentsMadeView == null) {
            try {
                editPaymentsMadeView =
                        new FXMLLoader(getClass().getResource("/Fxml/PaymentsMade/Edit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editPaymentsMadeView;
    }

    public void showEditRecordPaymentsMadeWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsMade/EditRecord.fxml"));
        Stage stage = setStage(fxmlLoader, "Edit Payment Made");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public AnchorPane getListPaymentsMadeView() {
        if (listPaymentsMadeView == null) {
            try {
                listPaymentsMadeView =
                        new FXMLLoader(getClass().getResource("/Fxml/PaymentsMade/List.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPaymentsMadeView;
    }

    public void showListRecordPaymentsMadeWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsMade/ListRecord.fxml"));
        Stage stage = setStage(fxmlLoader, "Payment Made");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public AnchorPane getEditPaymentsReceivedView() {
        if (editPaymentsReceivedView == null) {
            try {
                editPaymentsReceivedView =
                        new FXMLLoader(getClass().getResource("/Fxml/PaymentsReceived/Edit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editPaymentsReceivedView;
    }

    public void showEditRecordPaymentsReceivedWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsReceived/EditRecord.fxml"));
        Stage stage = setStage(fxmlLoader, "Edit Payment Received");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public AnchorPane getListPaymentsReceivedView() {
        if (listPaymentsReceivedView == null) {
            try {
                listPaymentsReceivedView =
                        new FXMLLoader(getClass().getResource("/Fxml/PaymentsReceived/List.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPaymentsReceivedView;
    }

    public void showListRecordPaymentsReceivedWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/PaymentsReceived/ListRecord.fxml"));
        Stage stage = setStage(fxmlLoader, "Payment Received");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public AnchorPane getListLedgerView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/Ledger/List.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        Stage stage = setStage(fxmlLoader, "Login");
        stage.show();
    }

    public void showCreateFirmWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/CreateFirm.fxml"));
        Stage stage = setStage(fxmlLoader, "Create Firm");
        stage.show();
    }

    public void showNavigationLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/NavigationLayout.fxml"));
        setStage(fxmlLoader, "Supply Records").show();
    }

    public void showAddItemWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddItem.fxml"));
        Stage stage = setStage(fxmlLoader, "Add Item");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showAddPartyWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddParty.fxml"));
        Stage stage = setStage(fxmlLoader, "Add Party");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showAddBankAccountWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Add/AddBankAccount.fxml"));
        Stage stage = setStage(fxmlLoader, "Add Bank Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private Stage setStage(FXMLLoader fxmlLoader, String title) {
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stage;
    }
}
