package entity;

import lombok.Data;

import java.util.Objects;


@Data
public class Card {
    private int id;
    private String cardNumber;
    private Bill bill;
    private Client client;

    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Bill getBill() {
        return bill;
    }

    public Client getClient() {
        return client;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Card(int id, String cardNumber, Bill bill, Client client) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.bill = bill;
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id&&
                Objects.equals(cardNumber, card.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber);
    }
}
