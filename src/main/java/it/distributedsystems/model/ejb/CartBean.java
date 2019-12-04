package it.distributedsystems.model.ejb;

import java.util.Set;

import it.distributedsystems.model.dao.Product;

/**
 * CartBean
 */
public interface CartBean {
    public Set<Product> getCart();
    public void addProduct(int productId);
    public void persistPurchase(String customerName);
}