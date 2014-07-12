/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO.impl;

import by.insane.DAO.CartDAO;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.Cart;
import by.insane.gigabyte.orm.Product;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class CartDAOImpl implements CartDAO {

    @Override
    public void addCart(Cart cart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(cart);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateCart(Cart cart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(cart);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Cart getCartById(int cart_id) {
        Cart cart = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        cart = (Cart) session.load(Cart.class, cart_id);
        session.getTransaction().commit();
        session.close();
        return cart;
    }

    @Override
    public Collection getAllCarts() {
        List<Cart> carts = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        carts = session.createCriteria(Cart.class).list();
        session.getTransaction().commit();
        session.close();
        return carts;
    }

    @Override
    public void deleteCart(Cart cart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(cart);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Cart getCartByAccount(Account account) {
        Cart cart = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        cart = (Cart)session.createSQLQuery("SELECT * FROM cart WHERE account_id = " + account.getAccount_id())
                .addEntity(Cart.class).uniqueResult();
       
        session.getTransaction().commit();
        session.close();
        return cart;
    }

    @Override
    public void deleteCartByAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.createSQLQuery("DELETE FROM cart WHERE account_id = " + account.getAccount_id())
                .addEntity(Cart.class);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteProductFromCartsById(int product_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete Cart where product_id = :pid");
        query.setParameter("pid", product_id);
        query.executeUpdate();
//        session.createSQLQuery("DELETE FROM Cart WHERE product_id = " + product_id);
        session.getTransaction().commit();
        session.close();
    }

}
