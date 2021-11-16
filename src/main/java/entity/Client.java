package entity;

import dao.CardDAO;

import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;

public class Client {
    private int id;
    private String name;
    private String surname;
    private HashMap<Card, Integer> cards;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public HashMap<Card, Integer> getCards() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        HashMap<Card, Integer> cards = new HashMap<>();
        cards = cardDAO.getCardsClient(id);
        this.cards = cards;
        return cards;
    }

    public Client(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public void enterCard(HashMap<Card, Integer> card, ATMMachine atmMachine) throws SQLException {
        atmMachine.checkPin(card);
    }

    public void depositMoney(ATMMachine atm, Card card, HashMap<Integer, Integer> moneys) throws SQLException {
        atm.depositMoney(card, moneys);
    }

    public HashMap<Integer, Integer> takeMoney(ATMMachine atm, Card card, int rub) throws SQLException {
        return atm.giveMoney(card, rub);
    }
    /*public  List<Integer> takeAllMoney(ATMMachine atm){

    }*/
}
