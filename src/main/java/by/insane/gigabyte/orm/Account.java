/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte.orm;

import by.insane.gigabyte.MyEntry;
import by.insane.gigabyte.orm.Comments;
import by.insane.gigabyte.orm.Orders;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author insane
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "account_id")
    private int account_id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "role")
    private String role;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_registration")
    private Date date;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Comments> comments = new HashSet<>(0);
    
    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    private Set<Orders> orders = new HashSet<>(0);

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    
    public String getRole() {
        return role;
    }

    public Cart getCart() {
        return cart;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public TreeMap<String, String> toMap() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("fname", fname);
        map.put("lname", lname);
        map.put("address", address);
        map.put("phone", phone);
        map.put("email", email);
        map.put("role", role);
        map.put("account_id", String.valueOf(account_id));
        map.put("password", password);

        return map;
    }

    public ArrayList<MyEntry<String, String>> toArrayList() {
        ArrayList<MyEntry<String, String>> arr = new ArrayList<>();
        arr.add(new MyEntry<>("First name",fname));
        arr.add(new MyEntry<>("Last name",lname));
        arr.add(new MyEntry<>("Address",address));
        arr.add(new MyEntry<>("Phone",phone));
        arr.add(new MyEntry<>("Email",email));
        return arr;
    }
}
