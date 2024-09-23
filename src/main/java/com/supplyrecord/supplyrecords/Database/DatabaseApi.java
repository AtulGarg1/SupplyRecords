package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;

import java.util.ArrayList;

public interface DatabaseApi {
    void createFirm(String firmName, String password);
    boolean verifyLogin(String firmName, String password);

    ArrayList<String> fetchFirmNames();
    ArrayList<String> fetchItemNames();
    ArrayList<String> fetchBankNames();
    ArrayList<String> fetchPartyNames();
    ArrayList<String> fetchSupplierNames();

    ArrayList<SupplyInwardRecord> fetchSupplyInwardRecordsList(String firmName);
    ArrayList<SupplyOutwardRecord> fetchSupplyOutwardRecordsList(String firmName);
    ArrayList<PaymentRecord> fetchPaymentsMadeList(String firmName);
    ArrayList<PaymentRecord> fetchPaymentsReceivedList(String firmName);
    ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId);

    void addBankAccount(String bank);
    void addParty(String party);
    void addSupplier(String supplier);
    void addItem(String item);

    void addSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord);
    long getLatestRecordId();
    void addSupplyItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails);

    void updateSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord);
    void deleteSupplyItemDetailsFor(long recordId);

    void addSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord);

    void updateSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord);
}
