/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.DAO.impl;

import by.insane.DAO.CommentsDAO;
import by.insane.gigabyte.orm.Comments;
import by.insane.gigabyte.util.*;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Андрій
 */
public class CommentsDAOImpl implements CommentsDAO{

    @Override
    public void addComment(Comments comment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(comment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateComment(Comments comment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(comment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Comments getCommentById(int comment_id) {
        Comments comment = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        comment = (Comments) session.get(Comments.class, comment_id);
        session.getTransaction().commit();
        session.close();
        return comment;
    }

    @Override
    public Collection getAllComments() {
        List<Comments> comments = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        comments = session.createCriteria(Comments.class).list();
        session.getTransaction().commit();
        for (Comments comments1 : comments) {
            System.out.println(comments1);
        }
        session.close();
        return comments;
    }

    @Override
    public void deleteComment(Comments comment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(comment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection getCommentsByProductId(int product_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        List<Comments> list = session.createSQLQuery("SELECT * FROM Comments WHERE product_id = " + product_id).addEntity(Comments.class).list();
        for (Comments object : list) {
            System.out.println(object.getAccount().getAccount_id());
        }
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public void deleteCommentsByProductId(int product_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete Comments where product_id = :pid");
        query.setParameter("pid", product_id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    
}
