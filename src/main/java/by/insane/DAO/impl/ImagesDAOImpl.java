/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.DAO.impl;

import by.insane.DAO.ImagesDAO;
import by.insane.gigabyte.orm.Images;
import by.insane.gigabyte.orm.ItemOrders;
import by.insane.gigabyte.orm.Product;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class ImagesDAOImpl implements ImagesDAO{

    @Override
    public void addImage(Images image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(image);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateImage(Images image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(image);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Images getImageById(int imageId) {
        Images image = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        image = (Images) session.get(Images.class, imageId);
        session.getTransaction().commit();
        session.close();
        return image;
    }

    @Override
    public Collection<Images> getAllImages() {
        List<Images> images = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        images = session.createCriteria(Images.class).list();
        session.getTransaction().commit();
        
        session.close();
        return images;
    }

    @Override
    public void deleteImage(Images image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(image);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Images> getImagesByProduct(Product product) {
        Set<Images> images = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        images = new HashSet<>(session.createSQLQuery("SELECT * FROM Images WHERE product_id = " + product.getProduct_id()).addEntity(Images.class).list());
        session.getTransaction().commit();
        session.close();
        return images;
    }
    
}
