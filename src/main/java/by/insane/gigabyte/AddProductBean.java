/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import by.insane.DAO.Factory;
import by.insane.gigabyte.orm.*;
import java.io.*;
import java.util.*;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

/**
 *
 * @author Андрій
 */
@ManagedBean
@ViewScoped
@Model
 class AddProductBean implements Serializable {

    private Product product = new Product();
    private int mainCategoryId = 1;
    private int categoryId = 1;
    private String pathForImage = "C:/Users/Андрій/Documents/NetBeansProjects/GigaByte_VS_Maven/src/main/resources/";
    private static final int COUNT_FILES = 6;
    private List<Part> files = new ArrayList<>(COUNT_FILES);
    private Part file;
    private String features;
    private List<Integer> categoriesID = new ArrayList<>();
    private int i = -1;

    public AddProductBean() {
        for (int i = 0; i < COUNT_FILES; i++) {
            Part part = null;
            files.add(part);
        }
        for (Category c : by.insane.DAO.Factory.getInstance().getCategoryDAO().getMainCategories()) {
            categoriesID.add(0);
        }
    }

    public void setCategoryID(int i) {
        categoriesID.add(i);
    }

    public int getCategoryID() {
        i++;
        return categoriesID.get(i);

    }

    public List<Part> getFiles() {
        System.out.println("Size array list files: " + files.size());
        return files;
    }

    public void setFiles(List<Part> files) {
        this.files = files;
    }

    public String addProduct() {
//        System.out.println("New Main category id: " + mainCategoryId + ", category id: " + categoryId);
//        product.setCategory(DAO.Factory.getInstance().getCategoryDAO().getCategoryById(categoryId));
//        DAO.Factory.getInstance().getProductDAO().addProduct(product);
//        uploadListener();
//        addFeatures();
        Category categoryById = by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(mainCategoryId);
        System.out.println("Selectde Category id: ");
        label:
        for (Integer id : categoriesID) {
            Set<Category> categories = categoryById.getCategories();
            for (Category category : categories) {
                if (category.getCategory_id() == id) {
                    System.out.println("Search category is: "
                            + by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(id).getName());
                    product.setCategory(by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(id));
                    break label;
                }
            }
        }
        product.setDate(new Date());
        by.insane.DAO.Factory.getInstance().getProductDAO().addProduct(product);
        uploadListener();
        addFeatures();
        return "/index?faces-redirect=true";
    }

    public void addFeatures() {
        String[] split = features.split("\n");
        System.out.println("Features: ");
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
//        by.insane.DAO.impl.
        Category category = by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(mainCategoryId);
        List<SelectItem> list = new LinkedList<>();
        for (Category subcategory : category.getCategories()) {
            SelectItem selectItem = new SelectItem(subcategory.getCategory_id(), subcategory.getName());
            list.add(selectItem);
        }
        return list;
    }

    public List<SelectItem> getSelectedItemSubcategories(Category category) {
//        Category category = DAO.Factory.getInstance().getCategoryDAO().getCategoryById(mainCategoryId);
        List<SelectItem> list = new LinkedList<>();
        for (Category subcategory : category.getCategories()) {
            SelectItem selectItem = new SelectItem(subcategory.getCategory_id(), subcategory.getName());
            list.add(selectItem);
        }
        return list;
    }

    public int getCategoryId() {
        return categoryId;
    }

//    public void setCategoryId(int categoryId) {
//        System.out.println("Set category id " + categoryId
//                + DAO.Factory.getInstance().getCategoryDAO().getCategoryById(categoryId).getName());
//        this.categoryId = categoryId;
//    }
    public Product getProduct() {
        return product;
    }

    public int getMainCategoryId() {

        return mainCategoryId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setMainCategoryId(int mainCategoryId) {
        System.out.println("awdawd aa " + mainCategoryId);
        this.mainCategoryId = mainCategoryId;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getFeatures() {
        return features;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile() {
        return file;
    }

    public void uploadListener() {
        System.out.println("awdawdawdawdawdawdawdawdawdawdadaaaaaaa22");
        for (Part part : files) {
            if (part != null) {
                FileOutputStream fos = null;
                try {
//                    part.get
                    System.out.println("Name upload file: " + part.getSubmittedFileName());
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
                    image.setPath("resources/");
                    by.insane.DAO.Factory.getInstance().getImagesDAO().addImage(image);
                    newFIleName = image.getImagesId() + submittedFileName.substring(submittedFileName.lastIndexOf("."), submittedFileName.length());
                    
                    System.out.println("New file name: " + newFIleName);
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

}
