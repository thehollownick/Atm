package dao;

import dao.executor.Executor;
import entity.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAO implements DAO<Bill, Integer> {
    private final Executor executor;
    private final BankDAO bankDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(BillDAO.class);

    public BillDAO() throws SQLException {
        this.executor = new Executor();
        this.bankDAO = new BankDAO();
    }

    @Override
    public void add(Bill bill) throws SQLException {
        executor.execUpdate("INSERT INTO BILL (" +
                        TableColumns.BillTable.RUB + "," +
                        TableColumns.BillTable.PENNY + " ," +
                        TableColumns.BillTable.BANK_ID +
                        ") VALUES (?,?,?)",
                Integer.toString(bill.getRub()),
                Integer.toString(bill.getPenny()),
                Integer.toString(bill.getBank().getId()));
    }

    @Override
    public Bill get(Integer integer) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            return new Bill(integer, result.getInt(TableColumns.BillTable.RUB),
                    result.getInt(TableColumns.BillTable.PENNY),
                    bankDAO.get(result.getInt(TableColumns.BillTable.BANK_ID)));
        }, "SELECT * FROM BILL WHERE " + TableColumns.BillTable.ID + " = ?", integer.toString());

    }

    @Override
    public void update(Bill bill) throws SQLException {
        LOGGER.error("bill {}", bill);
        executor.execUpdate("UPDATE BILL SET " +
                        TableColumns.BillTable.RUB + " = ?," +
                        TableColumns.BillTable.PENNY + " = ?," +
                        TableColumns.BillTable.BANK_ID + " = ? WHERE " +
                        TableColumns.BillTable.ID + " = ?",
                Integer.toString(bill.getRub()),
                Integer.toString(bill.getPenny()),
                Integer.toString(bill.getBank().getId()),
                Integer.toString(bill.getId()));

    }

    @Override
    public void delete(Bill bill) throws SQLException {
        executor.execUpdate("DELETE FROM BILL WHERE " + TableColumns.BillTable.ID + " = ?",
                Integer.toString(bill.getId()));
    }

    public List<Bill> getAll() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                bills.add(new Bill(
                        result.getInt(TableColumns.BillTable.ID),
                        result.getInt(TableColumns.BillTable.RUB),
                        result.getInt(TableColumns.BillTable.PENNY),
                        bankDAO.get(result.getInt(TableColumns.BillTable.BANK_ID))));
            }
            return bills;
        }, "SELECT * FROM BILL");
    }

    public List<Bill> getBillsOfBank(Integer integer) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                bills.add(new Bill(
                        result.getInt(TableColumns.BillTable.ID),
                        result.getInt(TableColumns.BillTable.RUB),
                        result.getInt(TableColumns.BillTable.PENNY),
                        bankDAO.get(result.getInt(TableColumns.BillTable.BANK_ID))));
            }
            return bills;
        }, "SELECT * FROM BILL WHERE " + TableColumns.BillTable.BANK_ID + " =?", integer.toString());
    }

    public int getBalance(Integer id) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            return result.getInt(TableColumns.BillTable.RUB);
        }, "SELECT " + TableColumns.BillTable.RUB + " FROM BILL WHERE " + TableColumns.BillTable.ID + " = ?", id.toString());
    }
}
