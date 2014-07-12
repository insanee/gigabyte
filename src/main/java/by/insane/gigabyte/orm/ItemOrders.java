/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.gigabyte.orm;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Андрій
 */

@Entity
@Table(name = "item_orders")
public class ItemOrders implements Serializable{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    @Column(name = "item_orders_id")
    private int itemOrderId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
    
    @Column(name = "count")
    private int count;

    public int getItemOrderId() {
        return itemOrderId;
    }

    public Product getProduct() {
        return product;
    }

    public Orders getOrder() {
        return order;
    }

    public int getCount() {
        return count;
    }

    public void setItemOrderId(int itemOrderId) {
        this.itemOrderId = itemOrderId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
