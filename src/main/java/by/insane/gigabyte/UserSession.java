/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.*;
import by.insane.gigabyte.orm.Account;
import by.insane.gigabyte.orm.Cart;
import by.insane.gigabyte.orm.Category;
import by.insane.gigabyte.orm.Comments;
import by.insane.gigabyte.orm.Features;
import by.insane.gigabyte.orm.Images;
import by.insane.gigabyte.orm.ItemCart;
import by.insane.gigabyte.orm.ItemOrders;
import by.insane.gigabyte.orm.Orders;
import by.insane.gigabyte.orm.Product;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.*;

/**
 *
 * @author insane
 */
@Named("user")
@SessionScoped
public class UserSession implements Serializable {
    
    private String password;
    private String login;
    private Account account = new Account();
    private Account prevAccount;
    private int id;
    private DataBaseConnection pool;
    private String role = "guest";
    private List<Category> categories;
    private Product product;
    private String selectedCategory;
    private DataModel productsModel;
    private String selectedCategoryId;
    private int button_id = 0;
    private Comments comment = new Comments();
    private Cart cart = new Cart();
    private int countProductInPage = 3;
    private int currentCountProduct = countProductInPage;
    private String searchText;
    private List<ItemCart> itemCartGuestUser = new LinkedList<>();
    private boolean registerBeforeOrder = false;
    
    public String getSearchText() {
        return searchText;
    }
    
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    public String goSearch() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/catalog/search?q=" + searchText);
        } catch (IOException ex) {
            Logger.getLogger(UserSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/GigaByte/catalog/search?=" + searchText;
    }
    
    public void setCurrentCountProduct(int currentCountProduct) {
        this.currentCountProduct = currentCountProduct;
    }
    
    public int getCurrentCountProduct() {
        return currentCountProduct;
    }
    
    public int getCountProductInPage() {
        return countProductInPage;
    }
    
    public void setComment(Comments comment) {
        if (comment != null) {
            this.comment = comment;
        }
    }
    
    public Comments getComment() {
        return comment;
    }
    
    public String getButton_id() {
        return "id" + (button_id++);
    }
    
    public String getSelectedCategory() {
        return selectedCategory;
    }
    
    public void setSelectedCategory(String selectedCategory) {
        if (selectedCategory != null && !selectedCategory.trim().equals("")) {
            try {
                Integer.parseInt(selectedCategory);
                this.selectedCategory = selectedCategory;
            } catch (Exception e) {
            }
            
        }
    }
    
    public Collection<Orders> getOrders() {
        return by.insane.DAO.Factory.getInstance().getOrdersDAO().getOrdersByAccount(account);
    }
    
    public String check() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession(false);
            Statement statement = pool.getStatement();
            
            ResultSet result = statement.executeQuery("SELECT account_id, login, role, password FROM account");
            System.out.println("login: " + login + "  passowrd: " + password);
            while (result.next()) {
                
                if (result.getString("login").equals(login) && result.getString("password").equals(password)) {
                    if (session != null) {
                        session.setAttribute("authentication", "true");
                        id = result.getInt("account_id");
                        account = by.insane.DAO.Factory.getInstance().getAccountDAO().getAccountById(id);
                        l = account.toArrayList();
                        prevAccount = account;
                        role = result.getString("role");
                        cart = by.insane.DAO.Factory.getInstance().getCartDAO().getCartByAccount(account);
                        
                    }
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/index?faces-redirect=true";
    }
    
    public UserSession() {
        pool = DataBaseConnection.getInstance();
        account.setRole("guest");
        account.setAccount_id(-1);
        categories = (List<Category>) by.insane.DAO.Factory.getInstance().getCategoryDAO().getMainCategories();
        System.out.println("Size: " + categories.size());
        
    }
    
    public UserSession(String password, String login) {
        this.password = password;
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public boolean getAuthentication() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("authentication") != null) {
                return Boolean.valueOf((String) session.getAttribute("authentication"));
            }
        }
        return false;
    }
    
    public void logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("authentication", "false");
        }
        role = "guest";
        account = new Account();
        prevAccount = null;
        account.setAccount_id(-1);
        cart.setAccount(null);
        cart.setAccountId(0);
        cart.getItemCart().clear();
        indexRedirect();
    }
    
    public String register() {
        System.out.println("Register_________________________________________________________________");
        account.setRole("user");
        account.setDate(new Date());
        by.insane.DAO.Factory.getInstance().getAccountDAO().addAccount(account);
        System.out.println("Account id: " + account.getAccount_id());
        cart.setAccount(account);
        cart.setAccountId(account.getAccount_id());
        by.insane.DAO.Factory.getInstance().getCartDAO().addCart(cart);
        if (registerBeforeOrder) {
            registerBeforeOrder = false;
            login = account.getLogin();
            password = account.getPassword();
            order();
            check();
            
        }
        return "/index?faces-redirect=true";
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void accountRedirect() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/faces/user/account.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void categoryRedirect() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/faces/category");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request
                    = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response
                    = (HttpServletResponse) context.getExternalContext().getResponse();
            
            System.out.println("kadn anfkan ka nk           " + request.getParameter("username"));
            //response.
            //dispatcher.forward(request, response);
            response.sendRedirect("category");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }
    
    public void indexRedirect() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/faces/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Map<String, String> getMapUser() {
        Account account = by.insane.DAO.Factory.getInstance().getAccountDAO().getAccountById(id);
        return account.toMap();
    }
    
    public String getClassForAdminPanel() {
        return role.equals("admin") ? "show" : "hide";
    }
    
    public String getRole() {
        return role;
    }
    
    public boolean isShowAdminPanel() {
        return "admin".equals(role);
    }
    
    public String getLastRole(String role) {
        return "admin".equals(role) ? "user" : "admin";
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public Product getProductById(String id) {
        if (id == null || id.trim().equals("")) {
            return null;
        }
        product = by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(Integer.parseInt(id));
        return product;
    }
    
    public String[] getNamesCategories() {
        String[] tmp = new String[categories.size()];
        
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = ((Category) categories.toArray()[i]).getName();
        }
        return tmp;
    }
    
    public String[] getNamesSubcategories() {
        if (selected == null || "".equals(selected.trim())) {
            selected = "OS";
        }
        
        String[] tmpArr = null;
        
        List<Category> categories = getCategories();
        for (int i = 0; i < getNamesCategories().length; i++) {
            if (categories.get(i).getName().equals(selected)) {
                Set<Category> subcategories = categories.get(i).getCategories();
                Iterator<Category> it = subcategories.iterator();
                tmpArr = new String[subcategories.size()];
                int j = 0;
                while (it.hasNext()) {
                    tmpArr[j++] = it.next().getName();
                }
                
            }
        }
        
        return tmpArr;
        
    }
    
    public String getSelected() {
        return selected;
    }
    
    public void setSelected(String selected) {
        System.out.println("Selected__________________ " + selected);
        if (selected == null || selected.equals("")) {
            return;
        }
        this.selected = selected;
    }
    
    public String getResult() {
        return result;
    }
    private String selected;
    private String result;
    
    synchronized public List<Product> getAllProduct(String id) {
        
        if (id == null || id.trim().equals("")) {
            return null;
        }
        int id2 = 0;
        try {
            id2 = Integer.parseInt(id);
        } catch (Exception e) {
            return null;
        }
        Category subcategories = by.insane.DAO.Factory.getInstance().getCategoryDAO().getCategoryById(id2);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String parameter = request.getParameter("product");
        Set<Product> products = new TreeSet<>();
        if (parameter != null && parameter.equalsIgnoreCase("all")) {
            Set<Category> categories1 = subcategories.getCategories();
            for (Category category : categories1) {
                products.addAll(category.getProducts());
            }
        } else {
            products = subcategories.getProducts();
        }
//        productsModel = new ListDataModel(new LinkedList<>(subcategories.getProducts()));

        System.out.println(products.size() + "  wawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        List<Product> list = new ArrayList<>(products);
        Collections.sort(list);
        return list;
    }
    
    synchronized public List<Product> getNextPage(String id) {
        List<Product> allProduct = getAllProduct(id);
        if (allProduct == null) {
            return null;
        }
        
        if (currentCountProduct >= allProduct.size()) {
            return allProduct;
        }
        
        return allProduct.subList(0, currentCountProduct);
    }
    
    public void nextPageListener() {
        currentCountProduct += countProductInPage;
    }
    
    public Product getProductByIdInRequestParameter() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String param = request.getParameter("productId");
        String att = (String)request.getAttribute("productId");
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String string = entry.getKey();
            String[] strings = entry.getValue();
            System.out.println("Param: " + string);
            
        }
        System.out.println("Attribute: " + att);
        if (param == null || param.trim().equals("")) {
            return null;
        }
        return by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(Integer.parseInt(param));
    }
    
    synchronized public Collection<Account> getAllAccounts() {
        Collection<Account> allAccounts = by.insane.DAO.Factory.getInstance().getAccountDAO().getAllAccounts();
        for (Account account1 : allAccounts) {
            System.out.println("Name: " + account1.getFname() + " size: " + allAccounts.size());
        }
        return allAccounts;
    }
    
    synchronized public String dropProduct(Product product) throws IOException {
//        DAO.Factory.getInstance().getCommentsDAO().getCommentsByProductId(Integer.parseInt(product_id));
//        DAO.Factory.getInstance().getCartDAO().deleteProductFromCartsById(product.getProduct_id());
//        DAO.Factory.getInstance().getCommentsDAO().deleteCommentsByProductId(product.getProduct_id());
        if (product == null) {
        } else {
            FTPLoader ftp = new FTPLoader();
            ftp.dropImages(product);
            by.insane.DAO.Factory.getInstance().getProductDAO().deleteProduct(product);
        }
        
        return "/index?faces-redirect=true";
    }
    
    synchronized public String getSelectedCategoryId() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("Params: " + request.getParameter("categoryId"));
        if (request.getParameter("categoryId") != null) {
            selectedCategoryId = (String) request.getParameter("categoryId");
        }
        return selectedCategoryId;
    }
    
    public String redirectCart() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/faces/cart.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/cart";
    }
    
    public void addComment() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String product_id = request.getParameter("productId");
        
        if (product_id == null || product_id.trim().equals("")) {
            return;
        }
        
        comment.setAccount(account);
        comment.setProduct(by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(Integer.parseInt(product_id)));
        Date date = new Date();
        comment.setDate(date);
        by.insane.DAO.Factory.getInstance().getCommentsDAO().addComment(comment);
        comment.setDescription("");
        
    }
    
    public static String nl2br(String string) {
        return (string != null) ? string.replace("\n", "<br />") : null;
    }
    
    public Collection<Comments> getCommentsByProductId() {
        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getParameter("productId") == null) {
            return null;
        }
        return by.insane.DAO.Factory.getInstance().getCommentsDAO().getCommentsByProductId(Integer.parseInt(request.getParameter("productId")));
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void addToCart() {
        String product_id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("product_id");
        if (product_id == null || product_id.trim().equals("")) {
            return;
        }
        if (account != null && account.getAccount_id() != -1) {
            ItemCart itemCart = new ItemCart();
//            cart.setAccount(account);
            itemCart.setCount(1);
            itemCart.setCart(cart);
            itemCart.setProduct(by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(Integer.parseInt(product_id)));
            by.insane.DAO.Factory.getInstance().getItemCartDAO().addItemCart(itemCart);
//            Set<ItemCart> set = new HashSet<>(cart.getItemCart());
//            set.add(itemCart);
            cart.getItemCart().add(itemCart);
//            DAO.Factory.getInstance().getCartDAO().addCart(cart);
//            itemCartGuestUser.add(itemCart);
        } else {
//            if (itemCartGuestUser == null) {
//                itemCartGuestUser = new LinkedList<>();
//            }
            ItemCart itemCart = new ItemCart();
            itemCart.setCount(1);
            itemCart.setProduct(by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(Integer.parseInt(product_id)));
            cart.getItemCart().add(itemCart);
        }
        System.out.println("Aded to cart!");
    }
    
    public void countProductInCartListener(Cart cart) {
        for (int i = 0; i < 10; i++) {
//            System.out.println("awdawdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa       " + cart.getProduct().getName() + " " + cart.getCount());
        }
//        DAO.Factory.getInstance().getCartDAO().updateCart(cart);
    }
    
    public String order() {
        
        if (!getAuthentication()) {
            System.out.println("Authentication awd");
            registerBeforeOrder = true;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/GigaByte/faces/login.xhtml?path=create");
            } catch (IOException ex) {
                Logger.getLogger(UserSession.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        Orders order = new Orders();
        Set<ItemOrders> items = new HashSet<>(0);
        order.setAccount(account);
        order.setStatus("new");
        order.setDate(new Date());
        by.insane.DAO.Factory.getInstance().getOrdersDAO().addOrder(order);
        for (ItemCart cart : cart.getItemCart()) {
            ItemOrders item = new ItemOrders();
            item.setCount(cart.getCount());
            item.setProduct(cart.getProduct());
            item.setOrder(order);
            by.insane.DAO.Factory.getInstance().getItemOrdersDAO().addItemOrders(item);
        }
        
        for (ItemCart itemOrders : cart.getItemCart()) {
            by.insane.DAO.Factory.getInstance().getItemCartDAO().deleteItemCart(itemOrders);
        }
        cart.getItemCart().clear();
//        order.setItemOrders(items);
//        DAO.Factory.getInstance().getOrdersDAO().addOrder(order);
//        for (ItemOrders itemOrders : items) {
//            System.out.println("name " + itemOrders.getProduct().getName() + "   order_id" + itemOrders.getOrder().getOrderId());
//        }
        return "/index?faces-redirect=true";
    }
    
    public void deleteRowFromCart(ItemCart itemCart) {
        if (account != null && account.getAccount_id() != -1) {
            by.insane.DAO.Factory.getInstance().getItemCartDAO().deleteItemCart(itemCart);
        }
        if (cart != null) {
            cart.getItemCart().remove(itemCart);
        }
        
    }
    
    public void dropUser(Account account) {
//        by.insane.DAO.Factory.getInstance().getCartDAO().deleteCartByAccount(account);
        by.insane.DAO.Factory.getInstance().getAccountDAO().deleteAccount(account);
    }
    
    public void updateUsersRole(Collection<Account> account) {
        for (Account account1 : account) {
            System.out.println("awdawdawd       " + account1.getFname() + " role " + account1.getRole());
        }
        Map<String, String> re = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        for (Map.Entry<String, String> entry : re.entrySet()) {
            String s1 = entry.getKey();
            String s2 = entry.getValue();
            System.out.println("key " + s1 + "  value " + s2);
            
        }
        
    }
    
    public void dropComment(Comments comment) {
        System.out.println("Comment dropped! ");
        by.insane.DAO.Factory.getInstance().getCommentsDAO().deleteComment(comment);
    }

    //start update account
    private List<MyEntry<String, String>> l;
    
    public void updateAccountInfo(MyEntry<String, String> item) {
        System.out.println("saved " + item.getKey() + " " + item.getNewValue());
        switch (item.getKey()) {
            case "First name":
                prevAccount.setFname(item.getNewValue());
                break;
            case "Last name":
                prevAccount.setLname(item.getNewValue());
                break;
            case "Phone":
                prevAccount.setPhone(item.getNewValue());
                break;
            case "Address":
                prevAccount.setAddress(item.getNewValue());
                break;
            case "Email":
                prevAccount.setEmail(item.getNewValue());
                break;
        }
        item.setEditable(false);
        save();
    }
    
    public void save() {
        System.out.println(" dddd " + prevAccount.getAddress());
        account = prevAccount;
        by.insane.DAO.Factory.getInstance().getAccountDAO().updateAccount(account);
        l = account.toArrayList();
        
    }
    
    public void cancel(MyEntry<String, String> item) {
        item.setEditable(false);
        System.out.println(item.getKey() + " editable " + item.isEditable() + " awd");
    }
    
    public void listener(MyEntry<String, String> m) {
        
        for (MyEntry<String, String> next : l) {
            next.setEditable(false);
        }
        m.setEditable(true);
        System.out.println(m.getKey() + " editable " + m.isEditable());
    }
    
    public List<MyEntry<String, String>> getArrayListUser() {
        
        return l;
    }

    //end update account
    public String getCurrentPage(String path) {
        return (path == null) ? "unknown" : path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    }
    
    public boolean isShowLeftBanners(String path) {
        String currentPage = getCurrentPage(path);
        switch (currentPage) {
            case "account":
                return false;
            case "cart":
                return false;
            case "orders":
                return false;
        }
        return true;
    }
    
    public Orders getOrderById() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer orderId = Integer.valueOf(request.getParameter("view"));
        System.out.println("Integer: " + orderId);
        
        if (orderId != null) {
            return by.insane.DAO.Factory.getInstance().getOrdersDAO().getOrderById(orderId);
        }
        
        return null;
    }
    
    public Collection<Product> getAllProduct() {
        return by.insane.DAO.Factory.getInstance().getProductDAO().getAllProducts();
    }
    
    public Collection<Product> getFindsProduct() {
        return by.insane.DAO.Factory.getInstance().getProductDAO().getProductsLikeName(searchText);
    }
    
    public String getAbsolutePathForImage(Product product) {
        if (product == null) {
            return null;
        }
        Set<Images> images = product.getImages();
        
        if (images == null) {
            return "resources/images/no-image-available.jpg";
        }
        if (images.iterator().hasNext()) {
            Images image = images.iterator().next();
            return image.getPath() + "/" + image.getName();
        } else {
            return "resources/images/no-image-available.jpg";
        }
        
    }
    
    public Collection<Orders> getAllOrders() {
        return by.insane.DAO.Factory.getInstance().getOrdersDAO().getAllOrders();
    }
    
    public void deleteOrder(Orders order) {
        for (ItemOrders item : order.getItemOrders()) {
            by.insane.DAO.Factory.getInstance().getItemOrdersDAO().deleteItemOrders(item);
        }
        by.insane.DAO.Factory.getInstance().getOrdersDAO().deleteOrder(order);
    }
    
    public Set<Features> getSubFeatures(Product product) {
        
        if (product == null) {
            return null;
        }
        ArrayList<Features> set = new ArrayList<>(product.getFeatures());
        int n = 5;
        if (set.size() <= 5) {
            n = set.size();
        }
        return new TreeSet<>(set.subList(0, n));
    }
    
    public boolean isContainedInBasket(Product product) {
        for (ItemCart item : cart.getItemCart()) {
            if (item.getProduct().equals(product)) {
                return true;
            }
        }
        return false;
    }
    
    public void dropImage(Images image) {
        if (image == null) {
            System.out.println("Drop image is call1!");
            return;
        }
        File file = new File("E:/GigaByte/web/resources/images/" + image.getName());
        if (file.exists()) {
            file.delete();
        }
        by.insane.DAO.Factory.getInstance().getImagesDAO().deleteImage(image);
        System.out.println("Drop image is call!");
    }
    
    public Collection<Images> getImagesByProduct() {
//        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        String productId = request.getParameter("productId");
//        if(productId == null || productId.trim().equals("")){
//            return null;
//        }
//        int a = getProductId();
//        System.out.println("ProductIDISawd: " + productId);
//        if(productId <=0 )
//            return null;
        return (product == null) ? null : by.insane.DAO.Factory.getInstance().getProductDAO().getProductById(product.getProduct_id()).getImages();
    }
    private int productId;

    public void setProductId(int awd) {
        
        productId = awd;
        System.out.println("ProductIDIS: " + productId);
    }

    private int getProductId() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String productId = request.getParameter("productId");
        if (productId == null || productId.trim().equals("")) {
            return 0;
        }
        return Integer.parseInt(productId);
    }
}
