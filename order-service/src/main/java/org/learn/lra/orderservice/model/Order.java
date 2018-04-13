package org.learn.lra.orderservice.model;

import lombok.NoArgsConstructor;
import org.learn.lra.coreapi.ProductInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "OrderEntity")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    private String productId;

    @NotNull
    private String productComment;

    @NotNull
    private int productPrice;

    @NotNull
    private String lraId;

    private boolean completed;

    public Order(ProductInfo productInfo) {
        this(productInfo, null);
    }

    public Order(ProductInfo productInfo, String lraId) {
        this.productId = productInfo.getProductId();
        this.productComment = productInfo.getComment();
        this.productPrice = productInfo.getPrice();
        this.completed = false;
        this.lraId = lraId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getLraId() {
        return lraId;
    }

    public void setLraId(String lraId) {
        this.lraId = lraId;
    }

    @Override
    public String toString() {
        return String.format("Order[id: %s, product: %s, saga: %s]", id, productId, lraId);
    }
}
