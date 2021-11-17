package dao;

import dao.executor.Executor;
import entity.Bill;
import entity.Card;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardDAO implements DAO<Card, Integer> {
    private final Executor executor;
    private final BillDAO billDAO;
    private final ClientDAO clientDAO;

    public CardDAO() throws SQLException {
        this.executor = new Executor();
        this.billDAO = new BillDAO();
        this.clientDAO = new ClientDAO();
    }

    @Override
    public void add(Card card) throws SQLException {
        executor.execUpdate("INSERT INTO CARD (" +
                        TableColumns.CardTable.NUMBER + "," +
                        TableColumns.CardTable.CLIENT_ID + " ," +
                        TableColumns.CardTable.BILL_ID +
                        ") VALUES (?,?,?)",
                card.getCardNumber(),
                Integer.toString(card.getClient().getId()),
                Integer.toString(card.getBill().getId()));

    }

    @Override
    public Card get(Integer integer) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            return new Card(integer, result.getString(TableColumns.CardTable.NUMBER),
                    billDAO.get(result.getInt(TableColumns.CardTable.BILL_ID)),
                    clientDAO.get(result.getInt(TableColumns.CardTable.CLIENT_ID)));
        }, "SELECT * FROM CARD WHERE " + TableColumns.CardTable.ID + " = ?", integer.toString());
    }

    @Override
    public void update(Card card) throws SQLException {
        executor.execUpdate("UPDATE CARD SET " +
                        TableColumns.CardTable.NUMBER + " = ?," +
                        TableColumns.CardTable.BILL_ID + " = ?," +
                        TableColumns.CardTable.CLIENT_ID + " = ? WHERE " +
                        TableColumns.CardTable.ID + " = ?",
                card.getCardNumber(),
                Integer.toString(card.getBill().getId()),
                Integer.toString(card.getClient().getId()),
                Integer.toString(card.getId()));
    }

    @Override
    public void delete(Card card) throws SQLException {
        executor.execUpdate("DELETE FROM CARD WHERE " + TableColumns.CardTable.ID + " = ?",
                Integer.toString(card.getId()));
    }

    @Override
    public List<Card> getAll() throws SQLException {
        List<Card> cards = new ArrayList<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                cards.add(new Card(
                        result.getInt(TableColumns.CardTable.ID),
                        result.getString(TableColumns.CardTable.NUMBER),
                        billDAO.get(result.getInt(TableColumns.CardTable.BILL_ID)),
                        clientDAO.get(result.getInt(TableColumns.CardTable.CLIENT_ID))));
            }
            return cards;
        }, "SELECT * FROM CARD");
    }

    public void updatePin(Integer id, Integer pin) throws SQLException {
        executor.execUpdate("UPDATE CARD SET " +
                        TableColumns.CardTable.PIN + " =? WHERE " +
                        TableColumns.CardTable.ID + " =?",
                pin.toString(), id.toString());
    }

    public HashMap<Card, Integer> getCardsClient(Integer id) throws SQLException {
        HashMap<Card, Integer> cards = new HashMap<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                cards.put(new Card(
                        result.getInt(TableColumns.CardTable.ID),
                        result.getString(TableColumns.CardTable.NUMBER),
                        billDAO.get(result.getInt(TableColumns.CardTable.BILL_ID)),
                        clientDAO.get(result.getInt(TableColumns.CardTable.CLIENT_ID))), new Integer(result.getInt(TableColumns.CardTable.PIN)));
            }
            return cards;
        }, "SELECT * FROM CARD WHERE " + TableColumns.CardTable.CLIENT_ID + " = ?", id.toString());
    }

    public HashMap<Card, Integer> getCardOfBill(Bill bill) throws SQLException {
        return executor.execQuery(result -> {
            HashMap<Card, Integer> cards = new HashMap<>();
            if (!result.next())
                return null;
            cards.put(new Card(result.getInt(TableColumns.CardTable.ID), result.getString(TableColumns.CardTable.NUMBER),
                    bill, clientDAO.get(result.getInt(TableColumns.CardTable.CLIENT_ID))), result.getInt(TableColumns.CardTable.PIN));
            return cards;
        }, "SELECT * FROM CARD WHERE " + TableColumns.CardTable.BILL_ID + " = ?", new Integer(bill.getId()).toString());
    }
}
