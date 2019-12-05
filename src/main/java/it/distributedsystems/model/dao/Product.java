package it.distributedsystems.model.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import it.distributedsystems.model.dao.Purchase;

@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 7879128649212648629L;

    protected int id;
    protected int productNumber;
    protected String name;
    protected int price;
    protected int quantity;
    protected Set<Purchase> purchases;
    protected Producer producer;

    public Product() {}

    public Product(String name) { this.name = name; }

    public Product(String name, int price) { this.name = name; this.price = price; }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public int getProductNumber() { return productNumber; }

    public void setProductNumber(int productNumber) { this.productNumber = productNumber; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToMany()
    public Set<Purchase> getPurchases() {
        return this.purchases;
    }

    public void setPurchases(Set<Purchase> purchase) {
        this.purchases = purchase;
    }

    @ManyToOne(
            cascade = {CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", price=" + price + ", producer=" + producer + ", productNumber="
                + productNumber + ", quantity=" + quantity + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + price;
        result = prime * result + productNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (price != other.price)
            return false;
        if (productNumber != other.productNumber)
            return false;
        return true;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
}
