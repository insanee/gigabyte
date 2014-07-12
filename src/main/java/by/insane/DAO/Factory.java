/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO;

import by.insane.DAO.impl.*;


/**
 *
 * @author Andriy
 */
public class Factory {

    private static Factory instance = null;
    private static ProductDAO productDAO = null;
    private static CategoryDAO categoryDAO = null;
    private static AccountDAO accountDAO = null;
    private static CommentsDAO commentsDAO = null;
    private static CartDAO cartDAO = null;
    private static ItemOrdersDAO itemOrdersDAO = null;
    private static OrdersDAO ordersDAO = null;
    private static ImagesDAO ImagesDAO = null;
    private static ItemCartDAO itemCartDAO = null;
    private static FeaturesDAO featuresDAO = null;
    
    private Factory(){
    }

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public ImagesDAO getImagesDAO() {
        if (ImagesDAO == null) {
            ImagesDAO = new ImagesDAOImpl();
        }
        return ImagesDAO;
    }

    public ItemCartDAO getItemCartDAO() {
        if (itemCartDAO == null) {
            itemCartDAO = new ItemCartDAOImpl();
        }
        return itemCartDAO;
    }

    public FeaturesDAO getFeaturesDAO() {
        if (featuresDAO == null) {
            featuresDAO = new FeaturesDAOImpl();
        }
        return featuresDAO;
    }

    public OrdersDAO getOrdersDAO() {
        if (ordersDAO == null) {
            ordersDAO = new OrdersDAOImpl();
        }
        return ordersDAO;
    }

    public ItemOrdersDAO getItemOrdersDAO() {
        if (itemOrdersDAO == null) {
            itemOrdersDAO = new ItemOrdersDAOImpl();
        }
        return itemOrdersDAO;
    }

    public ProductDAO getProductDAO() {
        if (productDAO == null) {
            productDAO = new ProductDAOImpl();
        }
        return productDAO;
    }

    public CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAOImpl();
        }
        return categoryDAO;
    }

    public AccountDAO getAccountDAO() {
        if (accountDAO == null) {
            accountDAO = new AccountDAOImpl();
        }
        return accountDAO;
    }

    public CommentsDAO getCommentsDAO() {
        if (commentsDAO == null) {
            commentsDAO = new CommentsDAOImpl();
        }
        return commentsDAO;
    }

    public CartDAO getCartDAO() {
        if (cartDAO == null) {
            cartDAO = new CartDAOImpl();
        }
        return cartDAO;
    }
}
