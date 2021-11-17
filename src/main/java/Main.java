import dao.AtmDAO;
import dao.CardDAO;
import dao.ClientDAO;
import entity.ATMMachine;
import entity.Card;
import entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException, RemoteException, NotBoundException {
        Client client = new ClientDAO().get(2);
        ATMMachine atmMachine = new AtmDAO().get(1);
        Card card = new CardDAO().get(2);
        client.enterCard(client.getCards(), atmMachine);
        System.out.println(atmMachine.isCheckedPin());
        HashMap<Integer, Integer> h = client.takeAllMoney(atmMachine, card);
        System.out.println(h);
    }
}
