package dao;

import dao.executor.Executor;
import entity.ATMMachine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AtmDAO implements DAO<ATMMachine,Integer> {

    private final Executor executor;
    private final BankDAO bankDAO;

    public AtmDAO() throws SQLException {
        this.executor = new Executor();
        this.bankDAO = new BankDAO();
    }

    @Override
    public void add(ATMMachine atmMachine) throws SQLException {
        executor.execUpdate("INSERT INTO atm (" +
                        TableColumns.AtmTable.BANK_ID + "," +
                        TableColumns.AtmTable.M100 + " ," +
                        TableColumns.AtmTable.M200 + " ," +
                        TableColumns.AtmTable.M500 + " ," +
                        TableColumns.AtmTable.M1000 + " ," +
                        TableColumns.AtmTable.M2000 + " ," +
                        TableColumns.AtmTable.M5000 +
                        ") VALUES (?,?,?,?,?,?,?)",
                Integer.toString(atmMachine.getBank().getId()),
                atmMachine.getMoneys().get(100).toString(),
                atmMachine.getMoneys().get(200).toString(),
                atmMachine.getMoneys().get(500).toString(),
                atmMachine.getMoneys().get(1000).toString(),
                atmMachine.getMoneys().get(2000).toString(),
                atmMachine.getMoneys().get(5000).toString());
    }

    @Override
    public ATMMachine get(Integer integer) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            HashMap<Integer,Integer> temp = new HashMap<>();
            temp.put(100,result.getInt(TableColumns.AtmTable.M100));
            temp.put(200,result.getInt(TableColumns.AtmTable.M200));
            temp.put(500,result.getInt(TableColumns.AtmTable.M500));
            temp.put(1000,result.getInt(TableColumns.AtmTable.M1000));
            temp.put(2000,result.getInt(TableColumns.AtmTable.M2000));
            temp.put(5000,result.getInt(TableColumns.AtmTable.M5000));
            return new ATMMachine(integer,
                    bankDAO.get(result.getInt(TableColumns.AtmTable.BANK_ID)),
                    temp);
        }, "SELECT * FROM ATM WHERE " + TableColumns.AtmTable.ID + " = ?", integer.toString());
    }

    @Override
    public void update(ATMMachine atmMachine) throws SQLException {
        executor.execUpdate("UPDATE atm SET " +
                        TableColumns.AtmTable.BANK_ID + " = ?," +
                        TableColumns.AtmTable.M100 + " = ?," +
                        TableColumns.AtmTable.M200 + " = ?," +
                        TableColumns.AtmTable.M500 + " = ?," +
                        TableColumns.AtmTable.M1000 + " = ?," +
                        TableColumns.AtmTable.M2000 + " = ?," +
                        TableColumns.AtmTable.M5000 + " = ? Where " +
                        TableColumns.AtmTable.ID + " = ?",
                Integer.toString(atmMachine.getBank().getId()),
                atmMachine.getMoneys().get(100).toString(),
                atmMachine.getMoneys().get(200).toString(),
                atmMachine.getMoneys().get(500).toString(),
                atmMachine.getMoneys().get(1000).toString(),
                atmMachine.getMoneys().get(2000).toString(),
                atmMachine.getMoneys().get(5000).toString(),
                Integer.toString(atmMachine.getId()));
    }

    @Override
    public void delete(ATMMachine atmMachine) throws SQLException {
        executor.execUpdate("DELETE FROM ATM WHERE " + TableColumns.AtmTable.ID + " = ?",
                Integer.toString(atmMachine.getId()));
    }

    @Override
    public List<ATMMachine> getAll() throws SQLException {
        List<ATMMachine> atmMachines = new ArrayList<>();
        return executor.execQuery(result -> {
            HashMap<Integer,Integer> temp = new HashMap<>();
            while (result.next()) {
                temp.put(100,result.getInt(TableColumns.AtmTable.M100));
                temp.put(200,result.getInt(TableColumns.AtmTable.M200));
                temp.put(500,result.getInt(TableColumns.AtmTable.M500));
                temp.put(1000,result.getInt(TableColumns.AtmTable.M1000));
                temp.put(2000,result.getInt(TableColumns.AtmTable.M2000));
                temp.put(5000,result.getInt(TableColumns.AtmTable.M5000));
                atmMachines.add(new ATMMachine(
                        result.getInt(TableColumns.AtmTable.ID),
                        bankDAO.get(result.getInt(TableColumns.BillTable.BANK_ID)),
                        temp));
            }
            return atmMachines;
        }, "SELECT * FROM ATM");
    }

    public List<ATMMachine> getAtmOfBank(Integer id) throws SQLException {
        List<ATMMachine> atmMachines = new ArrayList<>();
        return executor.execQuery(result -> {
            HashMap<Integer,Integer> temp = new HashMap<>();
            while (result.next()){
            temp.put(100,result.getInt(TableColumns.AtmTable.M100));
            temp.put(200,result.getInt(TableColumns.AtmTable.M200));
            temp.put(500,result.getInt(TableColumns.AtmTable.M500));
            temp.put(1000,result.getInt(TableColumns.AtmTable.M1000));
            temp.put(2000,result.getInt(TableColumns.AtmTable.M2000));
            temp.put(5000,result.getInt(TableColumns.AtmTable.M5000));
            atmMachines.add(new ATMMachine(
                    result.getInt(TableColumns.AtmTable.ID),
                    bankDAO.get(result.getInt(TableColumns.BillTable.BANK_ID)),
                    temp));
        }
        return atmMachines;
    }, "SELECT * FROM ATM WHERE "+TableColumns.AtmTable.BANK_ID +" = ?",id.toString());
    }


}
