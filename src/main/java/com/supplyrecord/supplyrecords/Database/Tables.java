package com.supplyrecord.supplyrecords.Database;

public class Tables {
    public static class BANK_ACCOUNTS {
        public static final String TABLE_NAME       = "bank_accounts";
        public static final String COL_BANK_ACCOUNT = "bank_account";
    }

    public static class FIRM_CREDENTIALS {
        public static final String TABLE_NAME        = "firm_credentials";
        public static final String COL_FIRM_NAME     = "firm_name";
        public static final String COL_FIRM_PASSWORD = "firm_password";
    }

    public static class ITEM_NAMES {
        public static final String TABLE_NAME    = "item_names";
        public static final String COL_ITEM_NAME = "item_name";
        public static final String COL_UNIT      = "unit";
    }

    public static class PARTY_NAMES {
        public static final String TABLE_NAME     = "party_names";
        public static final String COL_PARTY_NAME = "party_name";
    }

    public static class PAYMENT_RECORD {
        public static final String TABLE_NAME     = "payment_record";
        public static final String COL_RECORD_ID  = "record_id";
        public static final String COL_FIRM_NAME  = "firm_name";
        public static final String COL_PARTY_NAME = "party_name";
        public static final String COL_AMOUNT     = "amount";
        public static final String COL_BANK_NAME  = "bank_name";
        public static final String COL_DATE_TIME  = "date_time";
        public static final String COL_IS_CREDIT  = "is_credit";
    }

    public static class SUPPLY_ITEM_DETAILS {
        public static final String TABLE_NAME     = "supply_item_details";
        public static final String COL_RECORD_ID  = "record_id";
        public static final String COL_ITEM_NAME  = "item_name";
        public static final String COL_QTY        = "qty";
        public static final String COL_PRICE      = "price";
    }

    public static class SUPPLY_RECORD {
        public static final String TABLE_NAME            = "supply_record";
        public static final String COL_RECORD_ID         = "record_id";
        public static final String COL_FIRM_NAME         = "firm_name";
        public static final String COL_PARTY_NAME        = "party_name";
        public static final String COL_TOTAL_AMOUNT      = "total_amount";
        public static final String COL_DATE_TIME         = "date_time";
        public static final String COL_BILTI_CHARGE      = "bilti_charge";
        public static final String COL_BARDANA           = "bardana";
        public static final String COL_LABOUR_COST       = "labour_cost";
        public static final String COL_COMMISSION        = "commission";
        public static final String COL_POSTAGE           = "postage";
        public static final String COL_BAZAAR_CHARGE     = "bazaar_charge";
        public static final String COL_OTHER_EXPENSES    = "other_expenses";
        public static final String COL_IS_INWARD         = "is_inward";
    }
}
