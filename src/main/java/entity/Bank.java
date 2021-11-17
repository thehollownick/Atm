package entity;

import dao.AtmDAO;
import dao.BankDAO;
import dao.BillDAO;
import dao.CardDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rmi.BanksMethod;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Bank implements BanksMethod, Serializable {
    private int id;
    private String name;
    private transient List<Bill> clientsBills;
    private transient List<ATMMachine> atmMachines;
    private transient HashMap<Card, Integer> pinCodes;
    private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank(int id, String name) throws SQLException {
        this.id = id;
        this.name = name;
    }

    public Bank() {
    }

    public List<Bill> getClientsBills() throws SQLException {
        this.clientsBills = new BillDAO().getBillsOfBank(id);
        return clientsBills;
    }

    public List<ATMMachine> getAtmMachines() throws SQLException {
        this.atmMachines = new AtmDAO().getAtmOfBank(id);
        return atmMachines;
    }

    public HashMap<Card, Integer> getPinCodes() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        HashMap<Card, Integer> pins = new HashMap<>();
        getClientsBills();
        for (Bill bill : clientsBills) {
            pins.putAll(cardDAO.getCardOfBill(bill));
        }
        this.pinCodes = pins;
        return pins;
    }

    public void getClientsBillList() throws SQLException {
        List<Bill> temp = new BillDAO().getBillsOfBank(id);
        this.clientsBills = temp;
    }

    @Override
    public boolean pinCheck(Card card, Integer pin) throws SQLException {
        Integer temp = getPinCodes().get(card);
        return temp.equals(pin);
    }

    @Override
    public void depositMoney(int rub, Bill bill) throws SQLException {
        BillDAO billDAO = new BillDAO();
        int balance = rub + billDAO.getBalance(bill.getId());
        bill.setRub(balance);
        billDAO.update(bill);
    }

    @Override
    public void dispenceMoney(int rub, Bill bill) throws SQLException {
        BillDAO billDAO = new BillDAO();
        int balance = billDAO.getBalance(bill.getId()) - rub;
        LOGGER.debug("balance {}", balance);
        bill.setRub(balance);
        billDAO.update(bill);
    }
}
