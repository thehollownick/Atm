package entity;


import dao.AtmDAO;
import dao.BillDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rmi.BanksMethod;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ATMMachine implements Serializable {
    private int id;
    private Bank bank;
    private HashMap<Integer, Integer> moneys;
    private transient boolean checkedPin = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(ATMMachine.class);
    public static final String UNIQUE_BINDING_NAME = "server.bank";

    public int getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public HashMap<Integer, Integer> getMoneys() {
        return moneys;
    }

    public boolean isCheckedPin() {
        return checkedPin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setMoneys(HashMap<Integer, Integer> moneys) {
        this.moneys = moneys;
    }

    public void setCheckedPin(boolean checkedPin) {
        this.checkedPin = checkedPin;
    }

    public ATMMachine(int id, Bank bank, HashMap<Integer, Integer> moneys) {
        this.id = id;
        this.bank = bank;
        this.moneys = moneys;
    }

    public ATMMachine() {
    }

    public void checkPin(Map<Card, Integer> card) throws SQLException, RemoteException, NotBoundException {
        ArrayList<Card> keySet = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        value.addAll(card.values());
        keySet.addAll(card.keySet());
        final Registry registry = LocateRegistry.getRegistry(2732);
        BanksMethod banksMethod = (BanksMethod) registry.lookup(UNIQUE_BINDING_NAME);
        if (banksMethod.pinCheck(keySet.get(0), value.get(0)))
            checkedPin = true;
    }

    public void depositMoney(Card card, HashMap<Integer, Integer> moneys) throws SQLException, RemoteException, NotBoundException {
        if (!checkedPin) {
            //Logger.getRootLogger().setLevel(Level.OFF);
            LOGGER.error("Pin not enter");
            throw new RuntimeException();
        }
        int rub = 0;
        if (!moneys.isEmpty()) {

            ArrayList<Integer> keySet = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            keySet.addAll(moneys.keySet());
            values.addAll(moneys.values());

            int count = 0;
            for (Integer i : values) {
                count += i;
            }
            if (count > 40) {
                LOGGER.error("Более 40 купюр");
                throw new RuntimeException();
            }
            for (int i = 0; i < keySet.size(); i++) {
                rub += keySet.get(i) * values.get(i);
            }
            final Registry registry = LocateRegistry.getRegistry(2732);
            BanksMethod banksMethod = (BanksMethod) registry.lookup(UNIQUE_BINDING_NAME);
            banksMethod.depositMoney(rub, card.getBill());
            for (Integer i : keySet) {
                this.moneys.put(i, this.moneys.get(i) + moneys.get(i));
            }
            new AtmDAO().update(this);
        }

    }

    public HashMap<Integer, Integer> giveMoney(Card card, int rub) throws SQLException, RemoteException, NotBoundException {
        if (new BillDAO().getBalance(card.getBill().getId()) < rub) {
            LOGGER.error("Баланс меньше");
            throw new RuntimeException();
        }
        HashMap<Integer, Integer> result = AtmUtils.fromIntToMoneys(rub, moneys);
        int count = 0;
        for (Integer i : result.values()) {
            count += i;
        }
        if (count > 40) {
            LOGGER.error("Более 40 купюр");
            throw new RuntimeException();
        }
        new AtmDAO().update(this);
        final Registry registry = LocateRegistry.getRegistry(2732);
        BanksMethod banksMethod = (BanksMethod) registry.lookup(UNIQUE_BINDING_NAME);
        banksMethod.dispenceMoney(rub, card.getBill());
        return result;
    }

    public HashMap<Integer, Integer> giveAllMoney(Card card) throws SQLException, RemoteException, NotBoundException {
        return giveMoney(card, new BillDAO().getBalance(card.getBill().getId()));
    }
}
