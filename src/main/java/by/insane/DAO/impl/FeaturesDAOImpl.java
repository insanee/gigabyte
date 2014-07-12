/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO.impl;

import by.insane.DAO.FeaturesDAO;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.Features;
import by.insane.gigabyte.orm.Product;
import by.insane.gigabyte.util.*;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class FeaturesDAOImpl implements FeaturesDAO {

    @Override
    public void addFeatures(Features features) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(features);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateFeatures(Features features) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(features);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Features getFeaturesById(int featuresId) {
        Features features = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        features = (Features) session.get(Features.class, featuresId);
        session.getTransaction().commit();
        session.close();
        return features;
    }

    @Override
    public Collection<Features> getAllFeatures() {
        List<Features> features;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        features = session.createCriteria(Features.class).list();
        System.out.println("Size: " + features.size());
        session.getTransaction().commit();
        session.close();
        return features;
    }

    @Override
    public void deleteFeatures(Features features) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(features);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteFeaturesByProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
//        session.createSQLQuery("DELETE FROM Features WHERE product_id = " + product.getProduct_id()).addEntity(Features.class);
//        Query query = session.createQuery("delete Features where product_id = :productId");
//        query.setParameter("productId", product.getProduct_id());
//        query.executeUpdate();
        session.createSQLQuery("DELETE FROM Features WHERE product_id = " + product.getProduct_id()).addEntity(Features.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
