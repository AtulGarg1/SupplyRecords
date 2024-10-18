package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseImpl implements DatabaseApi {
    private Connection connection;

    private Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:supply-records.db");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public void createFirm(String firmName, String password) {
        String query = String.format(
                "INSERT INTO %s VALUES('%s', '%s')", Tables.FIRM_CREDENTIALS.TABLE_NAME, firmName, password
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> fetchFirmNames() {
        String query = String.format(
                "SELECT %s FROM %s", Tables.FIRM_CREDENTIALS.COL_FIRM_NAME, Tables.FIRM_CREDENTIALS.TABLE_NAME
        );
        ArrayList<String> firmNames = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString(Tables.FIRM_CREDENTIALS.COL_FIRM_NAME);
                firmNames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firmNames;
    }

    @Override
    public ArrayList<AutoSuggestions.Item> fetchItems() {
        String query = String.format(
                "SELECT * FROM %s", Tables.ITEM_NAMES.TABLE_NAME
        );
        ArrayList<AutoSuggestions.Item> items = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString(Tables.ITEM_NAMES.COL_ITEM_NAME);
                String unit = result.getString(Tables.ITEM_NAMES.COL_UNIT);
                items.add(new AutoSuggestions.Item(name, unit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public ArrayList<String> fetchBankNames() {
        String query = String.format(
                "SELECT * FROM %s", Tables.BANK_ACCOUNTS.TABLE_NAME
        );
        ArrayList<String> bankNames = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString(Tables.BANK_ACCOUNTS.COL_BANK_ACCOUNT);
                bankNames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankNames;
    }

    @Override
    public ArrayList<String> fetchPartyNames() {
        String query = String.format(
                "SELECT * FROM %s", Tables.PARTY_NAMES.TABLE_NAME
        );
        ArrayList<String> partyNames = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString(Tables.PARTY_NAMES.COL_PARTY_NAME);
                partyNames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partyNames;
    }

    @Override
    public ArrayList<SupplyRecord> fetchSupplyInwardRecordsList(String firmName) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = 1",
                Tables.SUPPLY_RECORD.TABLE_NAME, Tables.SUPPLY_RECORD.COL_FIRM_NAME,
                firmName, Tables.SUPPLY_RECORD.COL_IS_INWARD
        );
        ArrayList<SupplyRecord> supplyInwardRecords = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                supplyInwardRecords.add(
                        parseSupplyRecord(result, firmName, true)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplyInwardRecords;
    }

    @Override
    public ArrayList<SupplyRecord> fetchSupplyOutwardRecordsList(String firmName) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = 0",
                Tables.SUPPLY_RECORD.TABLE_NAME, Tables.SUPPLY_RECORD.COL_FIRM_NAME,
                firmName, Tables.SUPPLY_RECORD.COL_IS_INWARD
        );
        ArrayList<SupplyRecord> supplyOutwardRecords = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                supplyOutwardRecords.add(
                        parseSupplyRecord(result, firmName, false)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplyOutwardRecords;
    }

    @Override
    public ArrayList<PaymentRecord> fetchPaymentsMadeList(String firmName) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = 0",
                Tables.PAYMENT_RECORD.TABLE_NAME, Tables.PAYMENT_RECORD.COL_FIRM_NAME,
                firmName, Tables.PAYMENT_RECORD.COL_IS_CREDIT
        );
        ArrayList<PaymentRecord> paymentMadeRecords = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                paymentMadeRecords.add(
                        parsePaymentRecord(result, firmName, false)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentMadeRecords;
    }

    @Override
    public ArrayList<PaymentRecord> fetchPaymentsReceivedList(String firmName) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = 1",
                Tables.PAYMENT_RECORD.TABLE_NAME, Tables.PAYMENT_RECORD.COL_FIRM_NAME,
                firmName, Tables.PAYMENT_RECORD.COL_IS_CREDIT
        );
        ArrayList<PaymentRecord> paymentReceivedRecords = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                paymentReceivedRecords.add(
                        parsePaymentRecord(result, firmName, true)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentReceivedRecords;
    }

    @Override
    public ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = %d",
                Tables.SUPPLY_ITEM_DETAILS.TABLE_NAME, Tables.SUPPLY_ITEM_DETAILS.COL_RECORD_ID, recordId
        );
        ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                supplyItemDetails.add(
                        parseSupplyItemDetail(result, recordId)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplyItemDetails;
    }

    @Override
    public void addBankAccount(String bank) {
        String query = String.format(
                "INSERT INTO %s VALUES('%s')", Tables.BANK_ACCOUNTS.TABLE_NAME, bank
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addParty(String party) {
        String query = String.format(
                "INSERT INTO %s VALUES('%s')", Tables.PARTY_NAMES.TABLE_NAME, party
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addItem(AutoSuggestions.Item item) {
        String query = String.format(
                "INSERT INTO %s VALUES('%s', '%s')", Tables.ITEM_NAMES.TABLE_NAME, item.name(), item.unit()
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSupplyRecord(SupplyRecord supplyRecord) {
        String query = String.format(
                "INSERT INTO %s VALUES(NULL, '%s', '%s', %f, '%s', %f, %f, %f, %f, %f, %f, %f, %d)",
                Tables.SUPPLY_RECORD.TABLE_NAME, supplyRecord.firmName(), supplyRecord.partyName(),
                supplyRecord.totalAmount(), supplyRecord.date(), supplyRecord.biltiCharge(),
                supplyRecord.bardana(), supplyRecord.labourCost(), supplyRecord.commission(),
                supplyRecord.postage(), supplyRecord.bazaarCharges(), supplyRecord.otherExpenses(),
                supplyRecord.isInward() ? 1 : 0
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getLatestRecordId() {
        String query = "SELECT last_insert_rowid() as id";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                return result.getLong("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addSupplyItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId) {
        try {
            for (SupplyItemDetail supplyItemDetail: supplyItemDetails) {
                String query = String.format(
                        "INSERT INTO %s VALUES(%d, '%s', %f, %f)",
                        Tables.SUPPLY_ITEM_DETAILS.TABLE_NAME, recordId, supplyItemDetail.itemName(),
                        supplyItemDetail.qty(), supplyItemDetail.price()
                );
                Statement statement = getConnection().createStatement();
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplyRecord(SupplyRecord supplyRecord) {
        String query = String.format(
                "UPDATE %s SET %s = '%s', %s = %f, %s = '%s', %s = %f, %s = %f, %s = %f, %s = %f, %s = %f, %s = %f, %s = %f WHERE %s = %d",
                Tables.SUPPLY_RECORD.TABLE_NAME, Tables.SUPPLY_RECORD.COL_PARTY_NAME, supplyRecord.partyName(),
                Tables.SUPPLY_RECORD.COL_TOTAL_AMOUNT, supplyRecord.totalAmount(),
                Tables.SUPPLY_RECORD.COL_DATE_TIME, supplyRecord.date(),
                Tables.SUPPLY_RECORD.COL_BILTI_CHARGE, supplyRecord.biltiCharge(),
                Tables.SUPPLY_RECORD.COL_BARDANA, supplyRecord.bardana(),
                Tables.SUPPLY_RECORD.COL_LABOUR_COST, supplyRecord.labourCost(),
                Tables.SUPPLY_RECORD.COL_COMMISSION, supplyRecord.commission(),
                Tables.SUPPLY_RECORD.COL_POSTAGE, supplyRecord.postage(),
                Tables.SUPPLY_RECORD.COL_BAZAAR_CHARGE, supplyRecord.bazaarCharges(),
                Tables.SUPPLY_RECORD.COL_OTHER_EXPENSES, supplyRecord.otherExpenses(),
                Tables.SUPPLY_RECORD.COL_RECORD_ID, supplyRecord.recordId()
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPaymentRecord(PaymentRecord paymentRecord) {
        String query = String.format(
                "INSERT INTO %s VALUES(NULL, '%s', '%s', %f, '%s', '%s', %d)",
                Tables.PAYMENT_RECORD.TABLE_NAME, paymentRecord.firmName(), paymentRecord.partyName(),
                paymentRecord.amount(), paymentRecord.bankName(), paymentRecord.date(),
                paymentRecord.isCredit() ? 1 : 0
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePaymentRecord(PaymentRecord paymentRecord) {
        String query = String.format(
                "UPDATE %s SET %s = '%s', %s = %f, %s = '%s', %s = '%s' WHERE %s = %d",
                Tables.PAYMENT_RECORD.TABLE_NAME, Tables.PAYMENT_RECORD.COL_PARTY_NAME, paymentRecord.partyName(),
                Tables.PAYMENT_RECORD.COL_AMOUNT, paymentRecord.amount(),
                Tables.PAYMENT_RECORD.COL_BANK_NAME, paymentRecord.bankName(),
                Tables.PAYMENT_RECORD.COL_DATE_TIME, paymentRecord.date(),
                Tables.PAYMENT_RECORD.COL_RECORD_ID, paymentRecord.recordId()
        );
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSupplyItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails) {
        try {
            for (SupplyItemDetail supplyItemDetail: supplyItemDetails) {
                String query = String.format(
                        "DELETE FROM %s WHERE %s = %d AND %s = '%s' AND %s = %f AND %s = %f",
                        Tables.SUPPLY_ITEM_DETAILS.TABLE_NAME,
                        Tables.SUPPLY_ITEM_DETAILS.COL_RECORD_ID, supplyItemDetail.recordId(),
                        Tables.SUPPLY_ITEM_DETAILS.COL_ITEM_NAME, supplyItemDetail.itemName(),
                        Tables.SUPPLY_ITEM_DETAILS.COL_QTY, supplyItemDetail.qty(),
                        Tables.SUPPLY_ITEM_DETAILS.COL_PRICE, supplyItemDetail.price()
                );
                Statement statement = getConnection().createStatement();
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyLogin(String firmName, String password) {
        String query = String.format(
                "SELECT %s FROM %s WHERE %s = '%s'",
                Tables.FIRM_CREDENTIALS.COL_FIRM_PASSWORD, Tables.FIRM_CREDENTIALS.TABLE_NAME,
                Tables.FIRM_CREDENTIALS.COL_FIRM_NAME, firmName
        );
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                if (result.getString(Tables.FIRM_CREDENTIALS.COL_FIRM_PASSWORD).equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private SupplyRecord parseSupplyRecord(ResultSet result, String firmName, boolean isInward) throws SQLException {
        long recordId = result.getLong(Tables.SUPPLY_RECORD.COL_RECORD_ID);
        String partyName = result.getString(Tables.SUPPLY_RECORD.COL_PARTY_NAME);
        double totalAmount = result.getDouble(Tables.SUPPLY_RECORD.COL_TOTAL_AMOUNT);
        LocalDateTime dateTime = LocalDateTime.parse(result.getString(Tables.SUPPLY_RECORD.COL_DATE_TIME));
        double biltiCharge = result.getDouble(Tables.SUPPLY_RECORD.COL_BILTI_CHARGE);
        double bardana = result.getDouble(Tables.SUPPLY_RECORD.COL_BARDANA);
        double labourCost = result.getDouble(Tables.SUPPLY_RECORD.COL_LABOUR_COST);
        double commission = result.getDouble(Tables.SUPPLY_RECORD.COL_COMMISSION);
        double postage = result.getDouble(Tables.SUPPLY_RECORD.COL_POSTAGE);
        double bazaarCharge = result.getDouble(Tables.SUPPLY_RECORD.COL_BAZAAR_CHARGE);
        double otherExpenses = result.getDouble(Tables.SUPPLY_RECORD.COL_OTHER_EXPENSES);
        return new SupplyRecord(
                recordId, firmName, partyName, totalAmount, dateTime, biltiCharge, bardana,
                labourCost, commission, postage, bazaarCharge, otherExpenses, isInward
        );
    }

    private PaymentRecord parsePaymentRecord(ResultSet result, String firmName, boolean isCredit) throws SQLException {
        long recordId = result.getLong(Tables.PAYMENT_RECORD.COL_RECORD_ID);
        String partyName = result.getString(Tables.PAYMENT_RECORD.COL_PARTY_NAME);
        double amount = result.getDouble(Tables.PAYMENT_RECORD.COL_AMOUNT);
        String bankName = result.getString(Tables.PAYMENT_RECORD.COL_BANK_NAME);
        LocalDateTime dateTime = LocalDateTime.parse(result.getString(Tables.PAYMENT_RECORD.COL_DATE_TIME));
        return new PaymentRecord(
                recordId, firmName, partyName, amount, bankName, dateTime, isCredit
        );
    }

    private SupplyItemDetail parseSupplyItemDetail(ResultSet result, long recordId) throws SQLException {
        String itemName = result.getString(Tables.SUPPLY_ITEM_DETAILS.COL_ITEM_NAME);
        double qty = result.getDouble(Tables.SUPPLY_ITEM_DETAILS.COL_QTY);
        double price = result.getDouble(Tables.SUPPLY_ITEM_DETAILS.COL_PRICE);
        return new SupplyItemDetail(recordId, itemName, qty, price);
    }
}
