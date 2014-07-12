/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte.orm;

import java.io.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Андрій
 */
@Entity
@Table(name = "features")
public class Features implements Serializable, Comparable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "features_id")
    private int featuresId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public int getFeaturesId() {
        return featuresId;
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setFeaturesId(int featuresId) {
        this.featuresId = featuresId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object t) {
        Features f = (Features) t;
        if (featuresId == f.featuresId) {
            return 0;
        }
        return (featuresId > f.featuresId) ? 1 : -1;
    }

}
