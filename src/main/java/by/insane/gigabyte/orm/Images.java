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
@Table(name = "images")
public class Images implements Serializable{
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "images_id")
    private int imagesId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(name = "path")
    private String path;
    
    @Column(name = "name")
    private String name;

    public int getImagesId() {
        return imagesId;
    }

    public Product getProduct() {
        return product;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public void setImagesId(int imagesId) {
        this.imagesId = imagesId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
