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
        BasicConfigurator.configure();
        Client client = new ClientDAO().get(2);
        ATMMachine atmMachine = new AtmDAO().get(1);
        //client.enterCard(client.getCards(),atmMachine);
        System.out.println(atmMachine.isCheckedPin());
        HashMap<Integer,Integer> moneys = new HashMap<>();
        moneys.put(100,2);
        moneys.put(500,3);
        moneys.put(200,1);
        Card card = new CardDAO().get(2);
        client.depositMoney(atmMachine,card,moneys);
    }
}
