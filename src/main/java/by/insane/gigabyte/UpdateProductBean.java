/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import by.insane.DAO.Factory;
import by.insane.gigabyte.orm.Category;
import by.insane.gigabyte.orm.Features;
import by.insane.gigabyte.orm.Images;
import by.insane.gigabyte.orm.Product;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

/**
 *
 * @author Андрій
 */
@ManagedBean
@ViewScoped
public class UpdateProductBean implements Serializable {

    private Product product = new Product();
    private String features;
    private int mainCategoryId = 1;
    private int categoryId = 1;
    private List<Integer> categoriesID = new ArrayList<>();
    int i = -1;
    private static final int COUNT_FILES = 6;
    private List<Part> files = new ArrayList<>(COUNT_FILES);
    private String pathForImage = "E:/GigaByte/web/resources/images/";

    public UpdateProductBean() {
        for (Category c : by.insane.DAO.Factory.getInstance().getCategoryDAO().getMainCategories()) {
            categoriesID.add(0);
        }
        for (Integer id : categoriesID) {
            System.out.println("Categoriess1: " + id);
        }
        for (int i = 0; i < COUNT_FILES; i++) {
            Part part = null;
            files.add(part);
        }
    }

    public void setCategoryID(int j) {
        System.out.println("Set cat id: " + j);
        for (Integer id : categoriesID) {
            System.out.println("Categoriess4: " + id);
        }
        int temp = categoriesID.iterator().next();
        boolean flag = false;
        for (int i = 1; i < categoriesID.size(); i++) {
            if(temp != categoriesID.get(i)){
                flag = true;
                break;
            }

        }
        if(!flag){
            for(int f = 0; f < categoriesID.size(); f++){
                        categoriesID.set(f, 0);
                    }
        }
        for (Integer id : categoriesID) {
            System.out.println("Categoriess3: " + id);
        }
        categoriesID.add(j);
        for (Integer id : categoriesID) {
            System.out.println("Categoriess5: " + id);
        }
    }

    public int getCategoryID() {
        i++;
        if (i >= categoriesID.size()) {
            i--;
        }
        return categoriesID.get(i);

    }

    public String updateProduct() {
        System.out.println("Main category id: " + mainCategoryId + ", category id: " + categoryId);
//        product.setCategory(DAO.Factory.getInstance().getCategoryDAO().getCategoryById(categoryId));
//        DAO.Factory.getInstance().getProductDAO().addProduct(product);
        Category categoryById = by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(mainCategoryId);
        Set<Category> categories = categoryById.getCategories();
        for (Integer id : categoriesID) {
            System.out.println("Categoriess: " + id);
        }

        label:
        for (Integer id : categoriesID) {

            for (Category category : categories) {
                if (category.getCategory_id() == id && category.getParentCategory().getCategory_id() == mainCategoryId) {
                    System.out.println("Search category is: "
                            + by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(id).getName());
                    product.setCategory(by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(id));
                    break label;
                }
            }
        }
        i = -1;
        System.out.println("Nee categry update: " + product.getCategory().getName());
        System.out.println("New Features: " + features);
        System.out.println("New count " + product.getCount());
        System.out.println("New name " + product.getName());
        by.insane.DAO.Factory.getInstance().getProductDAO().updateProduct(product);
        by.insane.DAO.Factory.getInstance().getFeaturesDAO().deleteFeaturesByProduct(product);
        addFeatures();
        uploadListener();
        return "/index?faces-redirect=true";
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        System.out.println("CatEgory id updaTE " + categoryId);
        this.categoryId = categoryId;
    }

    public Product getProduct() {
        return product;
    }

    public int getMainCategoryId() {

        return mainCategoryId;
    }

    public void setProduct(Product product) {
        if (product == null) {
            System.out.println("Set product is null!");
            return;
        }
        System.out.println("Set product");
        this.product = product;
        features = "";
        mainCategoryId = product.getCategory().getParentCategory().getCategory_id();
        for (Features feature : product.getFeatures()) {
            features += feature.getName() + " = " + feature.getValue() + "\n";
        }
        i = -1;
        for (int j = 0; j < categoriesID.size(); j++) {
            categoriesID.set(j, product.getCategory().getCategory_id());
        }
//        categoriesID.clear();
//        categoryId = product.getCategory().getCategory_id();
//        for (Category c : by.insane.DAO.Factory.getInstance().getCategoryDAO().getMainCategories()) {
//            categoriesID.add(0);
//        }
        for (Integer id : categoriesID) {
            System.out.println("Categoriess2: " + id);
        }
        System.out.println("New category id is: " + categoryId);
    }

    public void setMainCategoryId(int mainCategoryId) {
        System.out.println("mAIN CatEgory id updaTE " + mainCategoryId);
        this.mainCategoryId = mainCategoryId;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        System.out.println("Features: " + features);
        if (features == null || features.trim().equals("")) {
            return;
        }
        this.features = features;
    }

    public void addFeatures() {
        String[] split = features.split("\n");

        for (String string : split) {
            int indexOf = string.indexOf("=");
            if (indexOf == -1) {
                continue;
            }
            Features feature = new Features();
            feature.setProduct(product);
            feature.setName(string.substring(0, indexOf).replaceAll("\n", "").trim());
            feature.setValue(string.substring(indexOf + 1, string.length()).replaceAll("\n", "").trim());
            Factory.getInstance().getFeaturesDAO().addFeatures(feature);
        }
        features = "";
    }

    public List<Part> getFiles() {
        return files;
    }

    public void setFiles(List<Part> files) {
        this.files = files;
    }

    public void uploadListener() {
        for (Part part : files) {
            if (part != null) {
                FileOutputStream fos = null;
                try {
                    String submittedFileName = part.getSubmittedFileName();
                    byte[] results = new byte[(int) part.getSize()];
                    InputStream in = part.getInputStream();
                    in.read(results);
                    String newFIleName;
                    if (part.getSubmittedFileName().trim().equals("")) {
                        continue;
                    }

                    Images image = new Images();
                    image.setProduct(product);
//                    image.setName(part.getSubmittedFileName());
                    image.setPath("resources/images/");
                    by.insane.DAO.Factory.getInstance().getImagesDAO().addImage(image);
                    newFIleName = image.getImagesId() + submittedFileName.substring(submittedFileName.lastIndexOf("."), submittedFileName.length());

//                    File f = new File(pathForImage + newFIleName);
//                    if (!f.exists()) {
//                        f.createNewFile();
//                    }
                    File f = new File(newFIleName);

//                    if (!f.exists()) {
//                        f.createNewFile();
//                    }
//                    File file = new File("awd");
//                    System.out.println("Absolutre path: " + file.getAbsoluteFile());
                    System.out.println("Path with file: " + f.getPath());
                    System.out.println("New file name: " + newFIleName);
                    FTPLoader ftpUpload = new FTPLoader();
                    String serverURL = ftpUpload.getServer();
                    image.setName(newFIleName);
                    image.setPath("http://" + serverURL.substring(serverURL.indexOf(".") + 1, serverURL.length()) + "/");
                    by.insane.DAO.Factory.getInstance().getImagesDAO().updateImage(image);

                    fos = new FileOutputStream(f);
                    fos.write(results);

                    ftpUpload.uploadToFtp(f, newFIleName);
                    fos.close();
                } catch (IOException ex) {
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException ex) {
                    }
                }
            } else {
                System.out.println("Part is null!");
            }
        }
    }

    public List<SelectItem> getSelectedItemMainCategory() {
        Collection<Category> mainCategories = by.insane.DAO.Factory.getInstance().getCategoryDAO().getMainCategories();
        List<SelectItem> list = new LinkedList<>();
        for (Category category : mainCategories) {
            SelectItem selectItem = new SelectItem(category.getCategory_id(), category.getName());
            list.add(selectItem);
        }

        return list;
    }

    public List<SelectItem> getSelectedItemSubcategories() {
        Category category = by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(mainCategoryId);
        List<SelectItem> list = new LinkedList<>();
        for (Category subcategory : category.getCategories()) {
            SelectItem selectItem = new SelectItem(subcategory.getCategory_id(), subcategory.getName());
            list.add(selectItem);
        }
        return list;
    }
}
