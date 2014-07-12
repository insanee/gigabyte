/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO.impl;

import by.insane.DAO.*;
import by.insane.gigabyte.orm.Category;
import by.insane.gigabyte.orm.Product;
import by.insane.gigabyte.util.HibernateUtil;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public void addCategory(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateCategory(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Category getCategoryById(int category_id) {
        Category category = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        category = (Category) session.get(Category.class, category_id);
        session.getTransaction().commit();
        
        session.close();
        return category;
    }

    @Override
    public Collection<Category> getAllCategories() {
        List<Category> categories = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        categories = session.createCriteria(Category.class).list();
        session.getTransaction().commit();
        for (Category category : categories) {
            System.out.println(category.getName() + category.getCategories().size());
        }
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        session.close();
        return categories;
    }

    @Override
    public void deleteCategory(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection<Category> getMainCategories() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        List<Category> categories = null;
        categories = session.createSQLQuery("SELECT * FROM category WHERE parent_id IS NULL").addEntity(Category.class).list();
        session.getTransaction().commit();
        for (Category category : categories) {
            System.out.println(category.getName());
            Set<Category> categories1 = category.getCategories();
            for (Category category1 : categories1) {
                System.out.println(category1.getName());
            }
        }
        session.close();
        return categories;
    }

    @Override
    public Category getCategoryByName(String name) {
        Collection<Category> allCategories = getAllCategories();
        for (Category category : allCategories) {
            if(name.equals(category.getName()))
                return category;
        }
        return null;
    }

}
