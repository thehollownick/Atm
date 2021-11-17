package dao;

import dao.executor.Executor;
import entity.Bank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDAO implements DAO<Bank, Integer> {

    private final Executor executor;

    public BankDAO() throws SQLException {
        this.executor = new Executor();
    }

    @Override
    public Bank get(Integer id) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            return new Bank(id, result.getString(TableColumns.BankTable.NAME));
        }, "SELECT * FROM BANK WHERE " + TableColumns.BankTable.ID + " = ?", id.toString());
    }

    @Override
    public List<Bank> getAll() throws SQLException {
        List<Bank> banks = new ArrayList<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                banks.add(new Bank(
                        result.getInt(TableColumns.BankTable.ID),
                        result.getString(TableColumns.BankTable.NAME)));
            }
            return banks;
        }, "SELECT * FROM BANK");
    }

    @Override
    public void add(Bank bank) throws SQLException {
        executor.execUpdate("INSERT INTO BANK (" +
                        TableColumns.BankTable.NAME +
                        ") VALUES (?)",
                bank.getName());
    }

    @Override
    public void delete(Bank bank) throws SQLException {
        executor.execUpdate("DELETE FROM BANK WHERE " + TableColumns.BankTable.ID + " = ?",
                Integer.toString(bank.getId()));
    }

    @Override
    public void update(Bank bank) throws SQLException {
        executor.execUpdate("UPDATE BANK SET " +
                        TableColumns.BankTable.NAME + " = ? WHERE " +
                        TableColumns.BankTable.ID + " = ?",
                bank.getName(),
                Integer.toString(bank.getId()));
    }

}
