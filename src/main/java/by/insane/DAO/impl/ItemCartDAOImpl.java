/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.DAO.impl;

import by.insane.DAO.ItemCartDAO;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.ItemCart;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class ItemCartDAOImpl implements ItemCartDAO{

    @Override
    public void addItemCart(ItemCart itemCart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(itemCart);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateItemCart(ItemCart itemCart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(itemCart);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public ItemCart getItemCartById(int itemCartId) {
        ItemCart itemCart = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        itemCart = (ItemCart) session.get(ItemCart.class, itemCartId);
        session.getTransaction().commit();
        session.close();
        return itemCart;
    }

    @Override
    public Collection<ItemCart> getAllItemCart() {
        List<ItemCart> itemCart;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        itemCart = session.createCriteria(ItemCart.class).list();
        session.getTransaction().commit();
        session.close();
        return itemCart;
    }

    @Override
    public void deleteItemCart(ItemCart itemCart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(itemCart);
        session.getTransaction().commit();
        session.close();
    }
    
}
