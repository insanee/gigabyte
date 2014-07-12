/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO.impl;

import by.insane.DAO.ItemOrdersDAO;
import by.insane.DAO.*;
import by.insane.gigabyte.orm.ItemOrders;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class ItemOrdersDAOImpl implements ItemOrdersDAO {

    @Override
    public void addItemOrders(ItemOrders itemOrders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(itemOrders);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateItemOrders(ItemOrders itemOrders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(itemOrders);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public ItemOrders getItemOrdersId(int itemOrdersId) {
        ItemOrders comment = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        comment = (ItemOrders) session.get(ItemOrders.class, itemOrdersId);
        session.getTransaction().commit();
        session.close();
        return comment;
    }

    @Override
    public Collection<ItemOrders> getAllItemOrders() {
        List<ItemOrders> itemOrders = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        itemOrders = session.createCriteria(ItemOrders.class).list();
        session.getTransaction().commit();
        for (ItemOrders comments1 : itemOrders) {
            System.out.println(comments1);
        }
        session.close();
        return itemOrders;
    }

    @Override
    public void deleteItemOrders(ItemOrders itemOrders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(itemOrders);
        session.getTransaction().commit();
        session.close();
    }

}
