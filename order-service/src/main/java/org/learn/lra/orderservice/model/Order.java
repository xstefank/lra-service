package org.learn.lra.orderservice.model;

import lombok.NoArgsConstructor;
import org.learn.lra.coreapi.ProductInfo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Order {

    @Id
    private String id;
    
    private ProductInfo productInfo;

    public Order(String id, ProductInfo productInfo) {
        this.id = id;
        this.productInfo = productInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
