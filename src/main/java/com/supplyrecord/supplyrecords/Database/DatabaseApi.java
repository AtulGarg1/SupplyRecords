package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;

import java.util.ArrayList;

public interface DatabaseApi {
    void dbTest();
    /* ----CREATE---- */
    void createFirm(String firmName, String password);

    void addBankAccount(String bank);
    void addParty(String party);
    void addItem(AutoSuggestions.Item item);

    void addSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord);
    void addSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord);
    void addSupplyInwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId);
    void addSupplyOutwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId);
    void addPaymentRecord(PaymentRecord paymentRecord);

    /* ----READ---- */
    boolean verifyLogin(String firmName, String password);

    ArrayList<String> fetchFirmNames();
    ArrayList<AutoSuggestions.Item> fetchItems();
    ArrayList<String> fetchBankNames();
    ArrayList<String> fetchPartyNames();

    ArrayList<SupplyInwardRecord> fetchSupplyInwardRecordsList(String firmName);
    ArrayList<SupplyOutwardRecord> fetchSupplyOutwardRecordsList(String firmName);
    ArrayList<SupplyItemDetail> fetchSupplyInwardItemDetailsFor(long recordId);
    ArrayList<SupplyItemDetail> fetchSupplyOutwardItemDetailsFor(long recordId);
    ArrayList<PaymentRecord> fetchPaymentsMadeList(String firmName);
    ArrayList<PaymentRecord> fetchPaymentsReceivedList(String firmName);

    long getLatestRecordId();

    /* ----UPDATE---- */
    void updateSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord);
    void updateSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord);
    void updatePaymentRecord(PaymentRecord paymentRecord);

    void deleteSupplyInwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails);
    void deleteSupplyOutwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails);
}
