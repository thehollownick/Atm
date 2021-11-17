package rmi;

import dao.BankDAO;
import entity.Bank;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerMain {
    public static final String UNIQUE_BINDING_NAME = "server.bank";

    public static void main(String[] args) throws SQLException, RemoteException, AlreadyBoundException, InterruptedException {
        final Bank server = new BankDAO().get(1);

        final Registry registry = LocateRegistry.createRegistry(2732);

        Remote stub = UnicastRemoteObject.exportObject(server, 0);

        registry.bind(UNIQUE_BINDING_NAME, stub);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
