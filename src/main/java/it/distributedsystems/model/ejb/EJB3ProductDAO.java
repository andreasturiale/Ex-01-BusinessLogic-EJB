package it.distributedsystems.model.ejb;

//import it.distributedsystems.model.logging.OperationLogger;
import it.distributedsystems.model.dao.*;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;



@Stateless
@Local(ProductDAO.class)
//@Remote(ProductDAO.class)  //-> TODO: serve nella versione clustering???
public class EJB3ProductDAO implements ProductDAO {

    @PersistenceContext(unitName = "distributed-systems-demo")
    EntityManager em;


    @Override
    @Interceptors(JmsLogProducer.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int insertProduct(Product product) {
        product.setProducer(em.merge(product.getProducer()));
        em.persist(product);
        return product.getId();
    }

    @Override
    @Interceptors(JmsLogProducer.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int removeProductByNumber(int productNumber) {

        Product product = (Product) em.createQuery("from Product p where p.name = :num").
                setParameter("num", productNumber).getSingleResult();
        if (product!=null){
            int id = product.getId();
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id="+id+" ;").executeUpdate();

            em.remove(product);

            return id;
        }
        else
            return 0;
    }

    @Override
    @Interceptors(JmsLogProducer.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int removeProductById(int id) {
        Product product = em.find(Product.class, id);
        if (product!=null){
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id="+product.getId()+" ;").executeUpdate();

            em.remove(product);

            return id;
        }
        else
            return 0;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Product findProductByNumber(int productNumber) {
        return (Product) em.createQuery("from Product p where p.productNumber = :num").
                setParameter("num", productNumber).getSingleResult();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Product findProductById(int id) {
        Product product= em.find(Product.class, id);
        Hibernate.initialize(product.getProducer());
        return product;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Product> getAllProducts() {
        List<Product> result= em.createQuery("from Product").getResultList();
        for (Product product : result)
           Hibernate.initialize(product.getProducer());
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Product> getAllProductsByProducer(Producer producer) {
        //Non è stato necessario usare una fetch join (nonostante Product.producer fosse mappato LAZY)
        //perché gli id delle entità LAZY collegate vengono comunque mantenuti e sono accessibili
        return em.createQuery("FROM Product p WHERE :producerId = p.producer.id").
                setParameter("producerId", producer.getId()).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        // riattacco il product al contesto di persistenza con una merge
        return em.createQuery("FROM Product p WHERE :purchaseId = p.purchase.id").
                setParameter("purchaseId", purchase.getId()).getResultList();
    }
}

