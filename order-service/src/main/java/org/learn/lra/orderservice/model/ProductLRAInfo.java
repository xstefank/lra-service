package org.learn.lra.orderservice.model;

import org.learn.lra.coreapi.LRAInfoImpl;
import org.learn.lra.coreapi.ProductInfo;

public class ProductLRAInfo extends LRAInfoImpl<ProductInfo> {

    private ProductInfo productInfo;

    public ProductLRAInfo(ProductInfo productInfo) {
        super(productInfo);
    }
}
