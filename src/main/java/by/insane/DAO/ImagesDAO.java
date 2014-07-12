/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.DAO;

import by.insane.gigabyte.orm.Product;
import by.insane.gigabyte.orm.Images;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Андрій
 */
public interface ImagesDAO {

    public void addImage(Images image);

    public void updateImage(Images image);

    public Images getImageById(int imageId);

    public Collection<Images> getAllImages();

    public void deleteImage(Images image);
    
    public Set<Images> getImagesByProduct(Product product);
}
