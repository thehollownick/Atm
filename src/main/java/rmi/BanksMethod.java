package rmi;

import entity.Bill;
import entity.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface BanksMethod extends Remote {
    void dispenceMoney(int rub, Bill bill) throws SQLException, RemoteException;

    void depositMoney(int rub, Bill bill) throws SQLException, RemoteException;

    boolean pinCheck(Card card, Integer pin) throws SQLException, RemoteException;
}
