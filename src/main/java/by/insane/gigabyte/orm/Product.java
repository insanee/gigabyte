/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "product")
public class Product implements Serializable, Comparable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "product_id")
    private int product_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "adding_date")
    private Date date;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ItemCart> itemCarts = new HashSet<>(0);

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Images> images = new HashSet<>(0);

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Features> features = new HashSet<>(0);
    
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Comments> comments = new HashSet<>(0);

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setFeatures(Set<Features> features) {
        this.features = features;
    }

    public Set<Features> getFeatures() {
        return features;
    }

    public Product() {
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public Set<Images> getImages() {
        return images;
    }

    public void setCategory(Category subcategories) {
        this.category = subcategories;
    }

    public int getProduct_id() {
        return product_id;
    }

    public Category getSubcategories() {
        return category;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemCarts(Set<ItemCart> itemCarts) {
        this.itemCarts = itemCarts;
    }

    public Category getCategory() {
        return category;
    }

    public Set<ItemCart> getItemCarts() {
        return itemCarts;
    }

    @Override
    public int compareTo(Object t) {
        Product product = (Product) t;
        if (product.getProduct_id() > product_id) {
            return -1;
        }
        if (product.getProduct_id() < product_id) {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 45;
        hash = 17 * hash + this.product_id;
        hash = 17 * hash + Objects.hashCode(this.category);
        hash = 17 * hash + this.count;
        hash = 17 * hash + this.price;
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.product_id != other.product_id) {
            return false;
        }
        return true;
    }

}
