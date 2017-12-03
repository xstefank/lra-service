package org.learn.lra.orderservice.model;

import org.learn.lra.coreapi.AbstractLRAInfo;
import org.learn.lra.coreapi.ProductInfo;

public class ProductLRAInfo extends AbstractLRAInfo<ProductInfo> {

    private ProductInfo productInfo;

    public ProductLRAInfo(ProductInfo productInfo) {
        super(productInfo);
    }
}
