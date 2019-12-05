package it.distributedsystems.model.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

@Entity
public class Purchase implements Serializable {

    private static final long serialVersionUID = 4612874195612951296L;

    protected int id;
    protected int purchaseNumber;
    protected Customer customer;
    protected Set<Product> products;
    protected HashMap<Integer,Integer> quantities;
    public Purchase() {}

    public Purchase(int purchaseNumber) { this.purchaseNumber = purchaseNumber; }

    public Purchase(int purchaseNumber, Customer customer) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
    }

    public Purchase(int purchaseNumber, Customer customer, Set<Product> products) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
        this.products = products;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public int getPurchaseNumber() { return id; }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @ManyToOne(
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    @ManyToMany( 
        cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    public Set<Product> getProducts() { return products; }

    public void setProducts(Set<Product> products) { this.products = products; }

    public HashMap<Integer, Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(HashMap<Integer, Integer> quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "Purchase [customer=" + customer + ", purchaseNumber=" + purchaseNumber + "]";
    }
}