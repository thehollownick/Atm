import dao.*;
import entity.*;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    private static final Logger LOGGER= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        Client client = new ClientDAO().get(2);
        ATMMachine atmMachine = new AtmDAO().get(1);
        Card card = new CardDAO().get(2);
        client.enterCard(client.getCards(),atmMachine);
        System.out.println(atmMachine.isCheckedPin());
        HashMap<Integer,Integer> h = client.takeMoney(atmMachine,card,12000);
        System.out.println(h);
    }
}
