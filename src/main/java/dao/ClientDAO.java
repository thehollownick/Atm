package dao;

import dao.executor.Executor;
import entity.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements DAO<Client, Integer> {
    private final Executor executor;

    public ClientDAO() throws SQLException {
        this.executor = new Executor();
    }

    @Override
    public Client get(Integer id) throws SQLException {
        return executor.execQuery(result -> {
            if (!result.next())
                return null;
            return new Client(id, result.getString(TableColumns.ClientTable.NAME),
                    result.getString(TableColumns.ClientTable.SURNAME));
        }, "SELECT * FROM CLIENT WHERE " + TableColumns.ClientTable.ID + " = ?", id.toString());
    }

    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        return executor.execQuery(result -> {
            while (result.next()) {
                clients.add(new Client(
                        result.getInt(TableColumns.ClientTable.ID),
                        result.getString(TableColumns.ClientTable.NAME),
                        result.getString(TableColumns.ClientTable.SURNAME)));
            }
            return clients;
        }, "SELECT * FROM CLIENT");
    }

    @Override
    public void add(Client client) throws SQLException {
        executor.execUpdate("INSERT INTO CLIENT (" +
                        TableColumns.ClientTable.NAME + ',' +
                        TableColumns.ClientTable.SURNAME +
                        ") VALUES (?, ?)",
                client.getName(),
                client.getSurname());
    }

    @Override
    public void delete(Client client) throws SQLException {
        executor.execUpdate("DELETE FROM CLIENT WHERE " + TableColumns.ClientTable.ID + " = ?",
                Integer.toString(client.getId()));
    }

    @Override
    public void update(Client client) throws SQLException {
        executor.execUpdate("UPDATE CLIENT SET " +
                        TableColumns.ClientTable.NAME + " = ?," +
                        TableColumns.ClientTable.SURNAME + " = ? WHERE " +
                        TableColumns.ClientTable.ID + " = ?",
                client.getName(),
                client.getSurname(),
                Integer.toString(client.getId()));
    }
}
