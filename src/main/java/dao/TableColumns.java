package dao;

public class TableColumns {
    public static class ClientTable {
        public static final String ID = "client_id";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
    }

    public static class BankTable {
        public static final String ID = "bank_id";
        public static final String NAME = "name";
    }

    public static class BillTable {
        public static final String ID = "bill_id";
        public static final String RUB = "rub";
        public static final String PENNY = "penny";
        public static final String BANK_ID = "bank_id";
    }

    public static class CardTable {
        public static final String ID = "card_id";
        public static final String NUMBER = "card_number";
        public static final String CLIENT_ID = "client_id";
        public static final String BILL_ID = "bill_id";
        public static final String PIN = "pin";
    }

    public static class AtmTable {
        public static final String ID = "atm_id";
        public static final String BANK_ID = "bank_id";
        public static final String M100 = "money100";
        public static final String M200 = "money200";
        public static final String M500 = "money500";
        public static final String M1000 = "money1000";
        public static final String M2000 = "money2000";
        public static final String M5000 = "money5000";
    }
}
