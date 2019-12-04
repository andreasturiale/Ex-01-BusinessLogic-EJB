package it.distributedsystems.model.ejb;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.CustomerDAO;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.ProductDAO;
import it.distributedsystems.model.dao.PurchaseDAO;
import it.distributedsystems.model.dao.Purchase;

/**
 * EJB3CartBean
 */
@SessionScoped
@Stateful
@Local(CartBean.class)
public class EJB3CartBean implements Serializable, CartBean {

    private Purchase purchase=new Purchase();
   
    @EJB
    private ProductDAO productDAO;
    @EJB
    private PurchaseDAO purchaseDAO;
    @EJB
    private CustomerDAO customerDAO;
    
    @PostConstruct
    public void initialize() {
        purchase = new Purchase();
        purchase.setProducts(new HashSet<Product>());
    }

    @Override
    public Set<Product> getCart() {
        return purchase.getProducts();
    }

    @Override
    public void addProduct(int productId) {
        Set<Product> products=purchase.getProducts();
        products.add(productDAO.findProductById(productId));
        purchase.setProducts(products);
    }

    @Override
    public void persistPurchase(String customerName) {
        Customer customer=customerDAO.findCustomerByName(customerName);
        this.purchase.setCustomer(customer);
        purchaseDAO.insertPurchase(this.purchase);
        //reset the states because you have finalized the purchase
        this.purchase = new Purchase();
        this.purchase.setProducts(new HashSet<Product>());
    }
    
}