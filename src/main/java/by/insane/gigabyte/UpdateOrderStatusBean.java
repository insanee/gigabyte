/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import by.insane.gigabyte.orm.Orders;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Андрій
 */
@ManagedBean
@ViewScoped
public class UpdateOrderStatusBean implements Serializable {

    private LinkedList<Orders> orders;

    public UpdateOrderStatusBean() {
        orders = new LinkedList<>(by.insane.DAO.Factory.getInstance().getOrdersDAO().getAllOrders());
    }
    
    public void updateOrders(){
        System.out.println("Update order!");
        by.insane.DAO.Factory.getInstance().getOrdersDAO().updateOrders(orders);
    }

    public LinkedList<Orders> getOrders() {
        return orders;
    }
    public void changeListener(Orders order){
        System.out.println("Change listener order!");
        int i = 0;
        for (Orders acc : orders) {
            if(acc.getOrderId()== acc.getOrderId()){
                orders.set(i, order);
                break;
            }
            i++;
        }
    }

    public void setOrders(LinkedList<Orders> orders) {
        this.orders = orders;
    }
public List<SelectItem> getAllStatuses() {
        List<SelectItem> list = new LinkedList<>();
        list.add(new SelectItem("new","new"));
        list.add(new SelectItem("no","no"));
        return list;
    }
}
