/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.DAO.impl;

import by.insane.DAO.*;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Andriy
 */
public class AccountDAOImpl implements AccountDAO{

    @Override
    public void addAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(account);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(account);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Account getAccountById(int account_id) {
        Account account = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        account = (Account) session.get(Account.class, account_id);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        List<Account> accounts;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        criteria.setProjection(Projections.distinct(Projections.property("account_id")));
        accounts = criteria.list();
        System.out.println("Size: " + accounts.size());
        session.getTransaction().commit();
        session.close();
        return accounts;
    }

    @Override
    public void deleteAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(account);
        session.getTransaction().commit();
        session.close();
    }

    
}
