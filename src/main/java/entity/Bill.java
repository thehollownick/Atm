package entity;

import dao.BillDAO;


import java.sql.SQLException;
import java.util.Objects;


public class Bill {
    private int id;
    private int rub;
    private int penny;
    private Bank bank;

    public int getId() {
        return id;
    }

    public int getRub() {
        return rub;
    }

    public int getPenny() {
        return penny;
    }

    public Bank getBank() {
        return bank;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRub(int rub) {
        this.rub = rub;
    }

    public void setPenny(int penny) {
        this.penny = penny;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bill(int id, int rub, int penny, Bank bank) {
        this.id = id;
        this.rub = rub;
        this.penny = penny;
        this.bank = bank;
    }

}
