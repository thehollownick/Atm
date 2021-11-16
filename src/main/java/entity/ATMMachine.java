package entity;


import dao.AtmDAO;
import dao.BillDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.rmi.CORBA.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ATMMachine {
    private int id;
    private Bank bank;
    private HashMap<Integer, Integer> moneys;
    private boolean checkedPin = false;
    private static final Logger LOGGER= LoggerFactory.getLogger(ATMMachine.class);


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

    public void checkPin(Map<Card, Integer> card) throws SQLException {
        ArrayList<Card> keySet = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        value.addAll(card.values());
        keySet.addAll(card.keySet());
        if (bank.pinCheck(keySet.get(0), value.get(0)))
            checkedPin = true;
    }

    public void depositMoney(Card card, HashMap<Integer, Integer> moneys) throws SQLException {
        try {
            if (!checkedPin) {
                //Logger.getRootLogger().setLevel(Level.OFF);
                LOGGER.error("Pin not enter");
                throw new RuntimeException();}
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
                if (count > 40) throw new RuntimeException("More 40");
                for (int i = 0; i < keySet.size(); i++) {
                    rub += keySet.get(i) * values.get(i);
                }
                bank.depositMoney(rub, card.getBill());
                for (Integer i : keySet) {
                    this.moneys.put(i, this.moneys.get(i) + moneys.get(i));
                }
                new AtmDAO().update(this);
            }

        } catch (RuntimeException err) {
            //System.out.println(err.getMessage());
        }
    }

    public HashMap<Integer,Integer> giveMoney(Card card, int rub) throws SQLException {
        if(new BillDAO().getBalance(card.getBill().getId())<rub){
            LOGGER.error("Баланс меньше");
            throw new RuntimeException();
        }
        HashMap<Integer,Integer> result=AtmUtils.fromIntToMoneys(rub,moneys);
        for (Integer i: moneys.keySet()) {
            moneys.put(i,moneys.get(i)-result.get(i));
        }
        new AtmDAO().update(this);
        return result;
    }
}
