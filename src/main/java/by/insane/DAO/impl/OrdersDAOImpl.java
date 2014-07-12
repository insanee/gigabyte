/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO.impl;

import by.insane.DAO.OrdersDAO;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.Orders;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public void addOrder(Orders orders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(orders);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateOrder(Orders orders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(orders);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Orders getOrderById(int orderId) {
        Orders comment = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        comment = (Orders) session.get(Orders.class, orderId);
        session.getTransaction().commit();
        session.close();
        return comment;
    }

    @Override
    public Collection<Orders> getAllOrders() {
        List<Orders> comments = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Criteria criteria = session.createCriteria(Orders.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        comments = criteria.list();
        session.getTransaction().commit();
        session.close();
        return comments;
    }

    @Override
    public void deleteOrder(Orders orders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(orders);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection<Orders> getOrdersByAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        List<Orders> list = session.createSQLQuery("select * from Orders where account_id = " + account.getAccount_id()).addEntity(Orders.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public void addOrders(Collection<Orders> orders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        for (Orders order : orders) {
            session.save(order);
        }

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void updateOrders(Collection<Orders> orders) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        for (Orders order : orders) {
            session.update(order);
        }

        session.getTransaction().commit();
        session.close();
    }

}
