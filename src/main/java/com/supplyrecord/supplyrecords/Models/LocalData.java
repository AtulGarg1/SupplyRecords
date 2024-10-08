package com.supplyrecord.supplyrecords.Models;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;

import java.util.ArrayList;

/**
 * <ul>
 *     <li>After login, it will fetch all data for that firm and store it for use.</li>
 *     <li>Any modifications done will be visible here as well as propagated to DB.</li>
 *     <li>It only contains data recognized by a firm_name. Any other data will have to be fetched by the corresponding DB interface at the time of query.</li>
 * </ul>
 * */
public class LocalData {
    private static LocalData localData;
    private static DatabaseApi db;

    private static String firmName;
    private static ArrayList<SupplyRecord> supplyInwardRecordsList;
    private static ArrayList<SupplyRecord> supplyOutwardRecordsList;
    private static ArrayList<PaymentRecord> paymentsMadeList;
    private static ArrayList<PaymentRecord> paymentsReceivedList;

    private LocalData() {
        db = new DatabaseImpl();
        supplyInwardRecordsList = new ArrayList<>();
        supplyOutwardRecordsList = new ArrayList<>();
        paymentsMadeList = new ArrayList<>();
        paymentsReceivedList = new ArrayList<>();
    }

    public static synchronized LocalData getInstance() {
        if (localData == null) {
            localData = new LocalData();
        }
        return localData;
    }

    public static void fetchLists() {
        supplyInwardRecordsList = db.fetchSupplyInwardRecordsList(firmName);
        supplyOutwardRecordsList = db.fetchSupplyOutwardRecordsList(firmName);
        paymentsMadeList = db.fetchPaymentsMadeList(firmName);
        paymentsReceivedList = db.fetchPaymentsReceivedList(firmName);
    }

    public void setFirmName(String firmName) {
        LocalData.firmName = firmName;
    }

    public String getFirmName() {
        return firmName;
    }

    public ArrayList<SupplyRecord> getSupplyInwardRecordsList() {
        return supplyInwardRecordsList;
    }

    public ArrayList<SupplyRecord> getSupplyOutwardRecordsList() {
        return supplyOutwardRecordsList;
    }

    public ArrayList<PaymentRecord> getPaymentsMadeList() {
        return paymentsMadeList;
    }

    public ArrayList<PaymentRecord> getPaymentsReceivedList() {
        return paymentsReceivedList;
    }

    public void insertIntoSupplyInwardRecordsList(SupplyRecord supplyInwardRecord) {
        supplyInwardRecordsList.add(supplyInwardRecord);
    }

    public void insertIntoSupplyOutwardRecordsList(SupplyRecord supplyOutwardRecord) {
        supplyOutwardRecordsList.add(supplyOutwardRecord);
    }

    public void insertIntoPaymentsMadeList(PaymentRecord paymentRecord) {
        paymentsMadeList.add(paymentRecord);
    }

    public void insertIntoPaymentsReceivedList(PaymentRecord paymentRecord) {
        paymentsReceivedList.add(paymentRecord);
    }

    public void updateSupplyInwardRecordsList(SupplyRecord supplyInwardRecord) {
        try {
            for (int i = 0; i < supplyInwardRecordsList.size(); i++) {
                if (supplyInwardRecordsList.get(i).recordId() == supplyInwardRecord.recordId()) {
                    supplyInwardRecordsList.set(i, supplyInwardRecord);
                    break;
                }
            }
        } catch (Exception e) {
            supplyInwardRecordsList = db.fetchSupplyInwardRecordsList(firmName);
        }
    }

    public void updateSupplyOutwardRecordsList(SupplyRecord supplyOutwardRecord) {
        try {
            for (int i = 0; i < supplyOutwardRecordsList.size(); i++) {
                if (supplyOutwardRecordsList.get(i).recordId() == supplyOutwardRecord.recordId()) {
                    supplyOutwardRecordsList.set(i, supplyOutwardRecord);
                    break;
                }
            }
        } catch (Exception e) {
            supplyOutwardRecordsList = db.fetchSupplyOutwardRecordsList(firmName);
        }
    }

    public void updatePaymentsMadeList(PaymentRecord paymentRecord) {
        try {
            for (int i = 0; i < paymentsMadeList.size(); i++) {
                if (paymentsMadeList.get(i).recordId() == paymentRecord.recordId()) {
                    paymentsMadeList.set(i, paymentRecord);
                    break;
                }
            }
        } catch (Exception e) {
            paymentsMadeList = db.fetchPaymentsMadeList(firmName);
        }
    }

    public void updatePaymentsReceivedList(PaymentRecord paymentRecord) {
        try {
            for (int i = 0; i < paymentsReceivedList.size(); i++) {
                if (paymentsReceivedList.get(i).recordId() == paymentRecord.recordId()) {
                    paymentsReceivedList.set(i, paymentRecord);
                    break;
                }
            }
        } catch (Exception e) {
            paymentsReceivedList = db.fetchPaymentsReceivedList(firmName);
        }
    }
}
