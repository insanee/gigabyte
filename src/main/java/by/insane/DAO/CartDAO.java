/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO;


import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.Cart;
import java.util.Collection;

/**
 *
 * @author Андрій
 */
public interface CartDAO {

    public void addCart(Cart cart);

    public void updateCart(Cart cart);

    public Cart getCartById(int cart_id);

    public Collection<Cart> getAllCarts();

    public void deleteCart(Cart cart);

    public Cart getCartByAccount(Account account);

    public void deleteCartByAccount(Account account);

    public void deleteProductFromCartsById(int product_id);
}
