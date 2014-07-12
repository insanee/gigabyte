/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte.orm;

import by.insane.gigabyte.orm.Account;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Андрій
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    private int accountId;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Account account;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<ItemCart> itemCart = new HashSet<>(0);

    public void setItemCart(Set<ItemCart> itemCart) {
        this.itemCart = itemCart;
    }

    public Set<ItemCart> getItemCart() {
        return itemCart;
    }

    public int getAccountId() {
        return accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
