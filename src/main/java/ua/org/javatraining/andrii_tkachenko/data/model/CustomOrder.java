package ua.org.javatraining.andrii_tkachenko.data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by tkaczenko on 15.12.16.
 */
@Entity
public class CustomOrder implements Serializable {
    //// FIXME: 13.03.17 Check String id
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "customer_customorder_fkey"))
    private Customer customer;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> products;

    public CustomOrder() {

    }

    public CustomOrder(int id, int status, String address, Customer customer) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}