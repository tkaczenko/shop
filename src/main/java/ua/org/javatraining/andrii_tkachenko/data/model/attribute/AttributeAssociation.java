package ua.org.javatraining.andrii_tkachenko.data.model.attribute;

import ua.org.javatraining.andrii_tkachenko.data.model.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Entity
@Table(name = "Value")
public class AttributeAssociation implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "product_value_fkey"))
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "attribute_value_fkey"))
    private Attribute attribute;

    @Column(nullable = false, columnDefinition = "text")
    private String value;

    public AttributeAssociation() {

    }

    public AttributeAssociation(Product product, Attribute attribute, String value) {
        this.product = product;
        this.attribute = attribute;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}